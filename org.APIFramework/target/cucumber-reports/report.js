$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("file:src/test/java/features/APITest.feature");
formatter.feature({
  "name": "to verify add location verify",
  "description": "",
  "keyword": "Feature",
  "tags": [
    {
      "name": "@APIValidation"
    }
  ]
});
formatter.scenarioOutline({
  "name": "Verify API Response for posting a new request",
  "description": "",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "name": "@addAddress"
    }
  ]
});
formatter.step({
  "name": "User sends a request to add a new location \u003cLocation\u003e and \u003cPhoneNumber\u003e",
  "keyword": "Given "
});
formatter.step({
  "name": "Verify the response after adding a new location",
  "keyword": "Then "
});
formatter.examples({
  "name": "",
  "description": "",
  "keyword": "Examples",
  "rows": [
    {
      "cells": [
        "Location",
        "PhoneNumber"
      ]
    },
    {
      "cells": [
        "1st Helena Drive, LA",
        "+41 125 568 963"
      ]
    },
    {
      "cells": [
        "30 Nuremberg, Germany",
        "+49 027 065 015"
      ]
    }
  ]
});
formatter.scenario({
  "name": "Verify API Response for posting a new request",
  "description": "",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "name": "@APIValidation"
    },
    {
      "name": "@addAddress"
    }
  ]
});
formatter.step({
  "name": "User sends a request to add a new location 1st Helena Drive, LA and +41 125 568 963",
  "keyword": "Given "
});
formatter.match({
  "location": "LocationAPISteps.sendAddLocationRequest(String,String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Verify the response after adding a new location",
  "keyword": "Then "
});
formatter.match({
  "location": "LocationAPISteps.verifyAddLocationResponse()"
});
formatter.result({
  "status": "passed"
});
formatter.scenario({
  "name": "Verify API Response for posting a new request",
  "description": "",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "name": "@APIValidation"
    },
    {
      "name": "@addAddress"
    }
  ]
});
formatter.step({
  "name": "User sends a request to add a new location 30 Nuremberg, Germany and +49 027 065 015",
  "keyword": "Given "
});
formatter.match({
  "location": "LocationAPISteps.sendAddLocationRequest(String,String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Verify the response after adding a new location",
  "keyword": "Then "
});
formatter.match({
  "location": "LocationAPISteps.verifyAddLocationResponse()"
});
formatter.result({
  "status": "passed"
});
});