@E2ECheckOut @Regression
Feature: End-to-end purchase flow for a Single product

  @SingleProdCheckout
  Scenario Outline: End-to-end purchase flow for <ProductType> Laptop
    Given User Launches application
    When the user selects the "Laptops" category and chooses the <ProductType> product
    Then the product details for <ProductType> should be displayed
    When the user adds the product to the cart
    Then the cart should contain the <ProductType> item
    When the user places the order
    Then the order confirmation screen should be displayed successfully

    Examples: 
      | ProductType  |
      | Sony vaio i7 |
      | Sony vaio i5 |
