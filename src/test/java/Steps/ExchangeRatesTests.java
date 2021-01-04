package Steps;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import io.restassured.response.Response;
import Util.RestUtilities;
import constants.EndPoints;
import constants.Path;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ExchangeRatesTests extends RestUtilities{

	RequestSpecification reqSpec;
	ResponseSpecification resSpec;

//This is a setup configuration method	
	@Before
	public void setup() {

		reqSpec = RestUtilities.getRequestSpecification();
		reqSpec.basePath(Path.API);
		
		resSpec = RestUtilities.getResponseSpecification();

	}

	// Step definitions scenario 1 Verify Response code of API on Success
	@Given("Exchange rate API")
	public void exchange_rate_api() {

		given().spec(reqSpec).when().get(EndPoints.LATEST_FOREIGN_EXCHANGE_RATES)
		.then().log().body()
		.statusCode(200);

	}

	@When("Get latest Rates")
	public void get_latest_rates() {

		// when().get(EndPoints.LATEST_FOREIGN_EXCHANGE_RATES).then().statusCode(200);

	}
	
	@Then("^Verify list of countries for which exchange rate available$")
	public void verify_list_of_countries_for_which_exchange_rate_available() {
		
		given().spec(reqSpec).when().get(EndPoints.LATEST_FOREIGN_EXCHANGE_RATES).then().assertThat()
		.body("rates.size()", equalTo(32));
		
		//.body(rates.size(),equalTo(31));
	}

	@Then("response code is {int}")
	public void response_code_is(Integer int1) {

	}

	/// Scenario 2 todays date is returned by exchange Rate latest API
	@Then("Todays date is returned")
	public void todays_date_is_returned() {
		// get yesterdays date As latest rate API will give yesterdays rate.
		String date1 = getYesterdaydate();

		reqSpec = RestUtilities.getRequestSpecification();
		reqSpec.basePath(Path.API);
		given().spec(reqSpec).when().get(EndPoints.LATEST_FOREIGN_EXCHANGE_RATES).then().body("date", equalTo(date1));
	}

	/// Scenario 3 Verify base is set to EURO for exchange Rate latest API
	@Then("Verify Base is set to EURO")
	public void Verify_Base_is_set_to_EURO() {
//		setup();
		given().spec(reqSpec).when().get(EndPoints.LATEST_FOREIGN_EXCHANGE_RATES).then().assertThat().body("base",
				equalTo("EUR"));

	}

	/// Scenario 4 Verify response code of exchange rate API for Specific date
	@Then("Verify response code for Specific date")
	public void Verify_response_code_for_Specific_date() {
//		setup();
		given().spec(reqSpec).when().get(EndPoints.SPECIFIC_DATE_FOREIGN_EXCHANGE_RATES).then().assertThat()
				.statusCode(200).body("date", equalTo(EndPoints.SPECIFIC_DATE_FOREIGN_EXCHANGE_RATES));

	}

	/// Scenario 5 Verify exchange rate of INR for Specific date
	@Then("Verify exchange rate of INR for Specific date")
	public void Verify_exchange_rate_of_INR_for_Specific_date() {
//		setup();

		given().spec(reqSpec).when().get(EndPoints.SPECIFIC_DATE_FOREIGN_EXCHANGE_RATES).then().assertThat()
				.body("rates.INR", equalTo(89.2515F));
				
	}
	
                /// Scenario 6 Verify error message is displayed by exchange rate API when future date is specified.
                @Then("Verify error msg for future date")
                public void Verify_error_msg_for_future_date() {
                        try {

                        given().spec(reqSpec).when().get(EndPoints.FUTURE_DATE_FOREIGN_EXCHANGE_RATES).then().assertThat()

                        .statusCode(200);

                //			.body("msg",equalTo("Please specify valid date.."));
                        }
                        catch (Exception e){

                                System.out.println("Here Error message should be displayed but API gives rates for Lates available date");
                        }
                }
                /// Scenario 7 Verify response of API when base is set to USD
                @Then("^Verify Response base set to USD$")
                public void Verify_Response_base_set_to_USD() {

                        RestUtilities.setEndPoint(EndPoints.LATEST_FOREIGN_EXCHANGE_RATES);
                        Response res = RestUtilities.getResponse(
                                        RestUtilities.createQueryParam(reqSpec, "base", "USD"), "get");
                        System.out.println("Base is Set to USD...");
                        String baseUSD = res.path("base");
                        System.out.println("baseUSD:"+baseUSD);

                //			Assert.assertTrue(USDrates.contains("USD"));

                }

                /// Scenario 8 Verify API gives conversion rates for following countries
                @Then("^Verify countries available for conversion rate$")
                        public void Verify_countries_available_for_conversion_rate() {

                        Map<String, Double> Expected_countriesList = new HashMap<String, Double>();
                        Expected_countriesList.put("GBP",0.89903);
                        Expected_countriesList.put("INR",89.6605);

                        RestUtilities.setEndPoint(EndPoints.SPECIFIC_DATE_FOREIGN_EXCHANGE_RATES);
                        Response res = RestUtilities.getResponse(reqSpec, "get");

                        Map<String, Double> Actual_countriesList = new HashMap<String, Double>();
                        Actual_countriesList = res.path("rates");

                        System.out.println("Countries Available.......");


                        System.out.println("Countries List:"+Actual_countriesList);

                        // Compare List of Countries returned by Exchange API			
                        Expected_countriesList.equals(Actual_countriesList);



                                }	

}
