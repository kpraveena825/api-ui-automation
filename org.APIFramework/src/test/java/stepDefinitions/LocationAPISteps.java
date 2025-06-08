package stepDefinitions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apiframework.CommonAPIFunctions;
import org.apiframework.utility.FileReaderUtility;
import org.apiframework.pageobjects.BasePage;
import org.apiframework.utility.ConfigManager;
import org.json.simple.parser.ParseException;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class LocationAPISteps {

	private final CommonAPIFunctions apiFunctions = new CommonAPIFunctions();

	@Given("User sends a request to add a new location {} with phone number {}")
	public void sendAddLocationRequest(String address, String phoneNumber)
			throws FileNotFoundException, IOException, ParseException {
		Map<String, Object> updatedData = new HashMap<>();
		updatedData.put("address", address);
		updatedData.put("phone_number", phoneNumber);
		Object payload = FileReaderUtility.buildUpdatedLocationPayload(BasePage.POST_LOCATION_FILE, updatedData);
		String apiUrl = ConfigManager.get("addAPI");
		apiFunctions.postAPIResponse(payload, apiUrl);
	}

	@Then("Verify the response after adding a new location for the test case {}")
	public void verifyAddLocationResponse(String testCaseID) throws FileNotFoundException, IOException, ParseException {
		apiFunctions.postResponseValidation("addLocation", testCaseID);
	}

	@Given("User updates the address in the request payload with {}")
	public void updateLocationAddress(String address) throws FileNotFoundException, IOException, ParseException {
		Map<String, Object> updatedData = new HashMap<>();
		updatedData.put("place_id", apiFunctions.getPlaceId());
		updatedData.put("address", address);
		Object updatedPayload = FileReaderUtility.buildUpdatedLocationPayload(BasePage.PUT_LOCATION_FILE, updatedData);
		String apiUrl = ConfigManager.get("updateAPI");
		apiFunctions.updateAPIResponse(updatedPayload, apiUrl, 200);
	}

	@Then("Verify the response after updating the location address for the test case {}")
	public void verifyUpdateLocationResponse(String testCaseID)
			throws FileNotFoundException, IOException, ParseException {
		apiFunctions.addressUpdateValidation("updateLocation", testCaseID);
	}

	@Given("User retrieves the location data via GET request")
	public void getLocationData() throws FileNotFoundException, IOException, ParseException {
		String apiUrl = ConfigManager.get("getAPI");
		apiFunctions.getAPIResponse(apiUrl, 200);
	}

	@Then("Verify the newly {} location by retrieving the location data for the test case {}")
	public void verifyGetLocationResponse(String type, String testCaseID)
			throws FileNotFoundException, IOException, ParseException {
		System.out.println("******" + type + "*******");
		if (type.equalsIgnoreCase("Added")) {
			apiFunctions.getAddressValidation("addLocation", testCaseID);
		} else if (type.equalsIgnoreCase("Updated")) {
			apiFunctions.getAddressValidation("updateLocation", testCaseID);
		} else {
			throw new IllegalArgumentException("Unsupported API type: " + type);
		}
	}
}
