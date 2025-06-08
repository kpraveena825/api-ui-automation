package org.apiframework.pageobjects;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {
	protected WebDriver driver;

	// Locators
	public static final By addToCart = By.xpath("//a[@class='btn btn-success btn-lg']");
	public static final By productName = By.xpath("//h2[@class='name']");
	public static final By priceTag = By.className("price-container");

	public ProductPage(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Clicks the "Add to Cart" button on the product page and handles the alert pop
	 * up. If the alert is present after clicking, it will be accepted. If no alert
	 * appears, it logs a message without throwing an exception.
	 */
	public void addItemtoCart() {
		BasePage.clickElement(addToCart);
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			alert.accept();
		} catch (Exception e) {
			System.out.println("No alert appeared after clicking 'Add to Cart'.");
		}
	}

	/**
	 * Retrieves the product name displayed on the product page.
	 *
	 * @return The product name as a String.
	 */
	public String getProductName() {
		return BasePage.getText(productName);
		
	}

	/**
	 * Retrieves the product price displayed on the product page.
	 *
	 * @return The product price as a String.
	 */
	public String getProductPrice() {
		return BasePage.getText(priceTag);

	}
}
