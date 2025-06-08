package org.apiframework.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;

public class CartCheckOutPage {

	private WebDriverWait wait;

	// Locators
	private static final By cartIcon = By.id("cartur");
	private static final By placeOrderButton = By.xpath("//button[@class='btn btn-success']");
	private static final By name = By.xpath("//input[@id='name']");
	private static final By country = By.id("country");
	private static final By city = By.id("city");
	private static final By creditCardNumber = By.id("card");
	private static final By purchaseButton = By.xpath("//button[text()='Purchase']");
	private static final By confirmationMessage = By.xpath("//h2[text()='Thank you for your purchase!']");
	private static final By orderconf = By.xpath("//p[@class='lead text-muted ']");
	private static final By cartHeader = By.xpath("//h2[text()='Products']");
	private static final By acceptOrderButton = By.xpath("//button[@class='confirm btn btn-lg btn-primary']");

	public CartCheckOutPage(WebDriver driver) {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	/**
	 * Navigates to the cart by clicking the cart icon.
	 */
	public void goToCart() {
		BasePage.clickElement(cartIcon);
	}

	/**
	 * Verifies if a specific product is present in the cart.
	 *
	 * @param product The name of the product to check.
	 * @return true if product is visible in cart; false otherwise.
	 */
	public boolean checkProductName(String product) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOfElementLocated(cartHeader));
		return BasePage.isElementVisible(By.xpath("//td[contains(text(),'" + product + "')]"));
	}

	/**
	 * Clicks the "Place Order" button to proceed with checkout.
	 */
	public void placeOrder() {
		BasePage.clickElement(placeOrderButton);
	}

	/**
	 * Clicks the "Purchase" button to proceed with Order.
	 */
	public void clickPurchaseButton() {
		BasePage.clickElement(purchaseButton);
	}

	/**
	 * Fills in the purchase form with user details.
	 *
	 * @param userName    Buyer's name
	 * @param userCountry Buyer's country
	 * @param userCity    Buyer's city
	 * @param userCard    Buyer's credit card number
	 */
	public void purchaseInfo(String userName, String userCountry, String userCity, String userCard) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(name));
		BasePage.sendText(name, userName);
		BasePage.sendText(country, userCountry);
		BasePage.sendText(city, userCity);
		BasePage.sendText(creditCardNumber, userCard);
	}

	/**
	 * Verifies confirmation message and user details in order summary.
	 *
	 * @param userName Buyer's name for verification
	 * @param userCard Buyer's card for verification
	 */
	public void orderConfimation() {
		Assert.assertTrue(BasePage.isElementVisible(confirmationMessage));
	}

	/**
	 * Clicks the "Accept Order" button to close success overlay
	 */
	public void clickAcceptOrder() {
		BasePage.clickElement(acceptOrderButton);
	}

	/**
	 * Verifies confirmation message and user details in order summary.
	 *
	 * @param userName Buyer's name for verification
	 * @param userCard Buyer's card for verification
	 */
	public void orderConfimationdetails(String userName, String userCard) {
		orderConfimation();
		String text = BasePage.getText(orderconf);
		Assert.assertTrue(text.contains(userName), "values not matching for name");
		Assert.assertTrue(text.contains(userCard), "values not matching for card details");
		wait.until(ExpectedConditions.visibilityOfElementLocated(acceptOrderButton));
		clickAcceptOrder();

	}

}
