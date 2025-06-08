@APIValidation @Regression
Feature: Verify ADD, Update and GET API's for location

  @addAddress
  Scenario Outline: Verify API Response for posting a new request
    Given User sends a request to add a new location <Location> with phone number <PhoneNumber>
    Then Verify the response after adding a new location for the test case <TestCaseID>
    Given User retrieves the location data via GET request
    Then Verify the newly Added location by retrieving the location data for the test case <TestCaseID>

    Examples: 
      | TestCaseID | Location              | PhoneNumber     |
      | TC_001     | 1st Helena Drive, LA  | +41 125 568 963 |
      | TC_002     | 30 Nuremberg, Germany | +49 027 065 015 |

  @updateAddress
  Scenario Outline: Verify API response for location update
    Given User updates the address in the request payload with <Address>
    Then Verify the response after updating the location address for the test case <TestCaseID>
    Given User retrieves the location data via GET request
    Then Verify the newly Updated location by retrieving the location data for the test case <TestCaseID>

    Examples: 
      | TestCaseID | Address                 |
      | TC_001     | 70 winter walk, USA     |
      | TC_002     | 27 Stein Kirche, Germany |
