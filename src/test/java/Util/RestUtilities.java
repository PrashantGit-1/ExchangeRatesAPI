package Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import constants.Path;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.given;

public class RestUtilities {
	
	
	public  static String ENDPOINT;
	public  static RequestSpecBuilder REQUEST_BUILDER;
	public  static RequestSpecification REQUEST_SPEC;
	public  static ResponseSpecBuilder RESPONSE_BUILDER;
	public  static ResponseSpecification RESPONSE_SPEC;

	// /Set ENDPOINT
	public static void setEndPoint(String epoint) {

		ENDPOINT = epoint;
	}

	public static RequestSpecification getRequestSpecification() {
		REQUEST_SPEC = null;
		REQUEST_BUILDER = new RequestSpecBuilder();
		REQUEST_BUILDER.setBaseUri(Path.BASE_URI);
		REQUEST_SPEC = REQUEST_BUILDER.build();
		return REQUEST_SPEC;
	}

	public static ResponseSpecification getResponseSpecification() {
	//	RESPONSE_SPEC = null;
		RESPONSE_BUILDER = new ResponseSpecBuilder();
		RESPONSE_BUILDER.expectStatusCode(200);
		RESPONSE_SPEC = RESPONSE_BUILDER.build();
		return RESPONSE_SPEC;
	}

	// /Add single Query Param to RSPEC Request Specification
	public static RequestSpecification createQueryParam(
			RequestSpecification rspec, String param, String value) {

		return rspec.queryParam(param, value);
	}

	// /Delete single Query Param from RSPEC Request Specification
	public static RequestSpecification deleteQueryParam(
			RequestSpecification rspec, String param, String value) {
		
		return rspec.queryParam(param, value);
	}

	
	// /Add Multiple Query Parameters to RSPEC Request Specification
	public static RequestSpecification createQueryParam(
			RequestSpecification rspec, Map<String, String> queryMap) {

		return rspec.queryParams(queryMap);
	}

	public static Response getResponse() {

		return given().get(ENDPOINT);

	}

	public static Response getResponse(RequestSpecification reqSpec, String type) {

		REQUEST_SPEC.spec(reqSpec);
		Response response = null;
		if (type.equalsIgnoreCase("get")) {
			response = given().spec(REQUEST_SPEC).get(ENDPOINT);
		} else if (type.equalsIgnoreCase("Post")) {
			response = given().spec(REQUEST_SPEC).post(ENDPOINT);
		} else if (type.equalsIgnoreCase("Put")) {
			response = given().spec(REQUEST_SPEC).post(ENDPOINT);
		} else if (type.equalsIgnoreCase("Delete")) {
			response = given().spec(REQUEST_SPEC).post(ENDPOINT);
		} else {
			System.out.println("System not Supported !!!!!!!!!");
		}

		response.then().log().all();
		response.then().spec(RESPONSE_SPEC);
		return response;
	}


	public static String getYesterdaydate(){
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, -1);
	    String date1 = dateFormat.format(cal.getTime()); 
//		System.out.println(dateFormat.format(cal.getTime()));
		return dateFormat.format(cal.getTime());
	}
	
}
