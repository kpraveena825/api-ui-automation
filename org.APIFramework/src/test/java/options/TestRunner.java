package options;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/features/", 
			glue = "stepDefinitions", 
			tags = "@APIValidation", 
			dryRun = false, 
			plugin = {"json:target/cucumber.json" })

public class TestRunner {

}
