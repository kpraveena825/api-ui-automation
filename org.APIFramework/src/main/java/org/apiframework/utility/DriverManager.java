
package org.apiframework.utility;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class DriverManager {
	private static WebDriver driver;
	private static final String CHROME_PATH = System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe";

	/** Initialize driver only if not already initialized **/
	@BeforeClass
	public static void initialize() {
		if (driver == null) {
			System.setProperty("webdriver.chrome.driver", CHROME_PATH);
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			driver = new ChromeDriver(options);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		}
	}

	/** initialize driver if not yet created **/
	public static WebDriver getDriver() {
		if (driver == null) {
			initialize();
		}
		return driver;
	}

	/** Quit driver after all tests completed **/
	@AfterClass
	public static void quitDriver() {
		if (driver != null) {
			driver.quit();
		}
	}
}
