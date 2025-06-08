package org.apiframework.pageobjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class HomePage {
	private WebDriver driver;
	private WebDriverWait wait;

	// Locators
	public static final By searchBox = By.className("navbar-brand");

	public HomePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	/**
	 * Navigates to a specified category and selects a given product from the list.
	 *
	 * @param category The name of the category to click
	 * @param product  The name of the product to select within the category.
	 * @return The name of the selected product.
	 */
	public String selectCategoryAndProduct(String category, String product) {
		try {
			homePageValidation();
			selectCategory(category);
			selectProduct(product);
		} catch (Exception e) {
			System.err.println("Error selecting category/product: " + e.getMessage());
		}
		return product;
	}

	/**
	 * Navigates to a specified category.
	 *
	 * @param category The name of the category to click
	 */
	public void selectCategory(String category) {
		BasePage.clickElement(By.xpath("//a[text()='" + category + "']"));
	}

	/**
	 * Navigates to a specified product.
	 *
	 * @param product The name of the product to click
	 */
	public void selectProduct(String product) {
		By productLocator = By.xpath("//a[contains(text(),'" + product + "')]");
		wait.until(ExpectedConditions.visibilityOfElementLocated(productLocator));
		BasePage.clickElement(productLocator);
	}

	/** validate User is on HomePage **/
	public void homePageValidation() {
		Assert.assertEquals(driver.getTitle(), "STORE", "Unexpected page title : ");
	}

}
