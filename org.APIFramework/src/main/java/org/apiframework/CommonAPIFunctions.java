package org.apiframework;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apiframework.utility.FileReaderUtility;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

public class CommonAPIFunctions {
	private static String placeId;
	private static final Logger logger = LogManager.getLogger(CommonAPIFunctions.class);

	JsonPath js;
	SoftAssert softAssert = new SoftAssert();
	JSONObject jsonObject;
	LinkedHashMap<String, String> data;

	// Utility method to set up common headers and query parameters for each API
	// request.
	private static RequestSpecification getRequestSpecification() {
		RestAssured.baseURI = APIConstants.BASE_URI;
		return given().log().all().queryParam("key", APIConstants.API_KEY).header("Content-Type", "application/json");
	}

	/**
	 * Method to POST a request to the specified endpoint with the provided payload.
	 * Returns parsed JsonPath object from the response.
	 * 
	 * @param payload   The request body to be sent in the POST request.
	 * @param resources The relative URL endpoint for the POST request.
	 */
	public JsonPath postAPIResponse(Object payload, String resources)
			throws FileNotFoundException, IOException, ParseException {

		String response = getRequestSpecification().body(payload).when().post(resources).then().assertThat()
				.statusCode(200).body("scope", equalTo("APP")).header("server", "Apache/2.4.52 (Ubuntu)").extract()
				.response().asString();

		logger.info("API Response: " + response);
		js = new JsonPath(response);
		System.out.print(response);
		setPlaceId(js.getString("place_id"));
		return js;
	}

	/**
	 * Method to send a PUT request to the specified endpoint with the provided
	 * payload. Returns parsed JsonPath object from the response.
	 * 
	 * @param payload   The request body to be sent in the PUT request.
	 * @param resources The relative URL endpoint for the PUT request.
	 */
	public JsonPath updateAPIResponse(Object payload, String resources, int statusCode)
			throws FileNotFoundException, IOException, ParseException {
		String response = getRequestSpecification().body(payload).when().put(resources).then().assertThat()
				.statusCode(statusCode).extract().response().asString();

		logger.info("Update API Response: " + response);
		System.out.print(response);
		js = new JsonPath(response);
		return js;
	}

	/**
	 * Method to send a GET request to the specified endpoint and extract the
	 * response. Returns parsed JsonPath object from the response.
	 * 
	 * @param resources The relative URL endpoint for the GET request.
	 */
	public JsonPath getAPIResponse(String resources, int statusCode) {
		String response = getRequestSpecification().queryParam("place_id", getPlaceId()).when().get(resources).then()
				.assertThat().statusCode(statusCode).extract().response().asString();

		logger.info("Get API Response: " + response);
		System.out.print(response);
		js = new JsonPath(response);
		return js;
	}

	/**
	 * Method to validate the POST response against expected values.
	 * 
	 * @param responseFile : writes the response to a file.
	 */
	public void postResponseValidation(String responseFile, String testCaseId) throws IOException {
		writeAndValidateResponse(responseFile, testCaseId, "scope", "status");
	}

	/**
	 * Method to validate the GET response against expected values.
	 * 
	 * @param responseFile : writes the response to a file.
	 */
	public void getAddressValidation(String responseFile, String testCaseId) throws IOException {
		writeAndValidateResponse(responseFile, testCaseId, "address", "website", "name");
	}

	/**
	 * Method to validate the PUT response against expected values.
	 * 
	 * @param responseFile : writes the response to a file.
	 */
	public void addressUpdateValidation(String responseFile, String testCaseID) throws IOException {
		writeAndValidateResponse(responseFile, testCaseID, "msg");

	}

	/**
	 * A method that writes the response to a file and validates json response
	 * against value stored in staticmap.xlsx. Performs soft assertions for expected
	 * fields in the response.
	 * 
	 * @param responseFile   The file to store expected response.
	 * @param expectedFields The fields in the response to validate.
	 */
	private void writeAndValidateResponse(String responseFile, String testCaseId, String... expectedFields)
			throws IOException {
		FileReaderUtility.writeJson(js, responseFile);
		data = FileReaderUtility.readStaticMapFromExcel(responseFile, testCaseId);
		for (String field : expectedFields) {
			if (js.get(field) == null) {
				softAssert.fail("Missing field in response: " + field);
			} else {
				softAssert.assertEquals(js.getString(field), data.get(field), "Value mismatch in field " + field);
				
			}
		}
		softAssert.assertAll();
	}

	public String getPlaceId() {
		return placeId;
	}

	public static void setPlaceId(String placeId) {
		CommonAPIFunctions.placeId = placeId;
	}

}
