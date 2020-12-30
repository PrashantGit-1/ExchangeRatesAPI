package RatesApiTests;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ExchangeRatesTests {
	
	@BeforeClass
	public void setup(){
		RestAssured.baseURI = "https://api.ratesapi.io";
		RestAssured.basePath = "/api";
		
	}
	
	@Test
	public void getLatestRates() {
		
		given()
		.when()
			.get("/latest")
		.then()
			.statusCode(200)
			.contentType("application/json");
		Reporter.log("Invalid contentType");
		
	}

}
