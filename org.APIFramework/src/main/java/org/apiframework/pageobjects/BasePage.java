package org.apiframework.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apiframework.utility.DriverManager;
import org.openqa.selenium.*;

public class BasePage {

	protected static WebDriver driver;
	protected static JavascriptExecutor js;

	public static final String USER_DATA_FILE = "userDetails.json";
	public static final String PROP_WEB_URL = "webUrl";
	public static final String PUT_LOCATION_FILE = "updateLocation.json";
	public static final String POST_LOCATION_FILE = "postLocation.json";

	private static final Logger logger = LogManager.getLogger(BasePage.class);

	public BasePage() {
		driver = DriverManager.getDriver();
		js = (JavascriptExecutor) driver;
	}

	/**
	 * Click on element using standard WebDriver click.
	 * 
	 * @param locator By locator of the element.
	 * @return true if click was successful; false otherwise.
	 */
	public static boolean clickElement(By locator) {
		try {
			driver.findElement(locator).click();
			return true;
		} catch (Exception e) {
			System.err.println("Click failed on element: " + locator + " | Error: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Click on element using js click, can be used for force click.
	 * 
	 * @param locator By locator of the element.
	 * @return true if click was successful; false otherwise.
	 */
	public static boolean clickElementJS(By locator) {
		try {
			WebElement element = driver.findElement(locator);
			js.executeScript("arguments[0].click();", element);
			return true;
		} catch (Exception e) {
			System.err.println("JS Click failed on element: " + locator + " | Error: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Send text using standard WebDriver sendKeys option.
	 * 
	 * @param locator By locator of the element.
	 * @return true if sendKey was successful; false otherwise.
	 */
	public static boolean sendText(By locator, String text) {
		try {
			WebElement element = driver.findElement(locator);
			element.clear();
			element.sendKeys(text);
			return true;
		} catch (Exception e) {
			System.err.println("Send text failed on element: " + locator + " | Error: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Get text using standard WebDriver getText option.
	 * 
	 * @param locator By locator of the element.
	 * @return true if getText was successful; false otherwise.
	 */
	public static String getText(By locator) {
		try {
			return driver.findElement(locator).getText();
		} catch (Exception e) {
			System.err.println("Get text failed on element: " + locator + " | Error: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Checks if the web element specified by the locator is visible on the page.
	 * 
	 * @param locator The By locator of the element to be checked.
	 * @return true if the element is displayed; false if it is not visible.
	 */
	public static boolean isElementVisible(By locator) {
		try {
			return driver.findElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
			logger.warn("Element not visible: " + locator);
			return false;
		}
	}

	/**
	 * Handles browser alert dialogs based on the specified action type.
	 * 
	 * @param type The action to perform on the alert. Accepts: "Accept" to accept
	 *             the alert. "Dismiss" to dismiss the alert.
	 * 
	 *             Logs a message if the provided option is unsupported.
	 */
	public static void alert(String type) {
		if (type.equals("Accept")) {
			driver.switchTo().alert().accept();
		} else if (type.equals("Dismiss")) {
			driver.switchTo().alert().dismiss();
		} else {
			logger.info("option is not listed");
		}

	}
}
