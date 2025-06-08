package stepDefinitions;

import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apiframework.pageobjects.BasePage;
import org.apiframework.pageobjects.CartCheckOutPage;
import org.apiframework.pageobjects.HomePage;
import org.apiframework.pageobjects.ProductPage;
import org.apiframework.utility.FileReaderUtility;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class UiStepDefinitions extends BasePage {

	HomePage homePage;
	ProductPage productPage;
	CartCheckOutPage cartCheckoutPage;
	String productPrice;
	JSONObject testData;
	private final Properties properties = FileReaderUtility.readPropertiesFile("property.properties");

	public UiStepDefinitions() throws IOException {

		this.homePage = new HomePage(driver);
		this.productPage = new ProductPage(driver);
		this.cartCheckoutPage = new CartCheckOutPage(driver);
		this.testData = FileReaderUtility.loadTestData(USER_DATA_FILE);
	}

	@Given("User Launches application")
	public void launchApplication() throws FileNotFoundException, IOException, ParseException {
		driver.get(properties.getProperty(PROP_WEB_URL));
		driver.manage().window().maximize();

	}

	@When("the user selects the {} category and chooses the {} product")
	public void userSelectsCategoryAndProduct(String category, String product) {
		homePage.selectCategoryAndProduct(category, product);

	}

	@Then("the product details for {} should be displayed")
	public void productValidation(String product) {
		assertEquals(product, productPage.getProductName(), "product name is  missing");
	}

	@When("the user adds the product to the cart")
	public void addProductToCart() {
		productPage.addItemtoCart();
	}

	@Then("the cart should contain the {} item")
	public void cartValidation(String product) throws InterruptedException {
		cartCheckoutPage.goToCart();
		Assert.assertTrue("Expected product not found in cart", cartCheckoutPage.checkProductName(product));
	}

	@Then("the user places the order")
	public void placeTheOrder() {
		cartCheckoutPage.placeOrder();
		cartCheckoutPage.purchaseInfo((String) testData.get("user"), (String) testData.get("country"),
				(String) testData.get("city"), (String) testData.get("card"));
		cartCheckoutPage.clickPurchaseButton();
	}

	@Then("the order confirmation screen should be displayed successfully")
	public void OrderConfirmationValidation() {
		cartCheckoutPage.orderConfimationdetails((String) testData.get("user"), (String) testData.get("card"));
	}
}
