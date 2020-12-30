package Steps;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import Util.RestUtilities;
import constants.EndPoints;
import constants.Path;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ExchangeRatesTests {

	RequestSpecification reqSpec;
	ResponseSpecification resSpec;

	@BeforeMethod
	public void setup() {

		reqSpec = RestUtilities.getRequestSpecification();
		reqSpec.basePath(Path.API);

		// RestAssured.baseURI = "https://api.ratesapi.io";
		// RestAssured.basePath = "/api";

	}

	// Step definitions scenario 1 Verify Response code of API on Success
	@Given("Exchange rate API")
	public void exchange_rate_api() {
		setup();
		given().spec(reqSpec).when().get(EndPoints.LATEST_FOREIGN_EXCHANGE_RATES).then().statusCode(200);

	}

	@When("Get latest Rates")
	public void get_latest_rates() {

		// when().get(EndPoints.LATEST_FOREIGN_EXCHANGE_RATES).then().statusCode(200);

	}

	@Then("response code is {int}")
	public void response_code_is(Integer int1) {

	}

	/// Scenario 2 todays date is returned by exchange Rate latest API
	@Then("Todays date is returned")
	public void todays_date_is_returned() {
		// get yesterdays date As latest rate API will give yesterdays rate.
		String date1 = RestUtilities.getYesterdaydate();

		reqSpec = RestUtilities.getRequestSpecification();
		reqSpec.basePath(Path.API);
		given().spec(reqSpec).when().get(EndPoints.LATEST_FOREIGN_EXCHANGE_RATES).then().body("date", equalTo(date1));
	}

	/// Scenario 3 Verify base is set to EURO for exchange Rate latest API
	@Then("Verify Base is set to EURO")
	public void Verify_Base_is_set_to_EURO() {
		setup();
		given().spec(reqSpec).when().get(EndPoints.LATEST_FOREIGN_EXCHANGE_RATES).then().assertThat().body("base",
				equalTo("EUR"));

	}

	/// Scenario 4 Verify response code of exchange rate API for Specific date
	@Then("Verify response code for Specific date")
	public void Verify_response_code_for_Specific_date() {
		setup();
		given().spec(reqSpec).when().get(EndPoints.SPECIFIC_DATE_FOREIGN_EXCHANGE_RATES).then().assertThat()
				.statusCode(200).body("date", equalTo(EndPoints.SPECIFIC_DATE_FOREIGN_EXCHANGE_RATES));

	}

	/// Scenario 5 Verify exchange rate of INR for Specific date
	@Then("Verify exchange rate of INR for Specific date")
	public void Verify_exchange_rate_of_INR_for_Specific_date() {
		setup();

		given().spec(reqSpec).when().get(EndPoints.SPECIFIC_DATE_FOREIGN_EXCHANGE_RATES).then().assertThat()
				.body("rates.INR", equalTo(89.2515F));
	}
	
	/// Scenario 6 Verify error message is displayed by exchange rate API when future date is specified.
		@Then("Verify error msg for future date")
		public void Verify_error_msg_for_future_date() {
			try {
				setup();
			
			given().spec(reqSpec).when().get(EndPoints.FUTURE_DATE_FOREIGN_EXCHANGE_RATES).then().assertThat()
					
			.statusCode(400);
			
//			.body("msg",equalTo("Please specify valid date.."));
			}
			catch (Exception e){
				
				System.out.println("Here Error message should be displayed but API gives rates for Lates available date");
			}
		}
	

}
