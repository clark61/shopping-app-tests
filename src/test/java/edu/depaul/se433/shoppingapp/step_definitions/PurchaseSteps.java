/*
 * Project
 * Topic: Cucumber
 * Author: Nick Clark
 */

package edu.depaul.se433.shoppingapp.step_definitions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.depaul.se433.shoppingapp.Bill;
import edu.depaul.se433.shoppingapp.ShippingType;
import edu.depaul.se433.shoppingapp.ShoppingCart;
import edu.depaul.se433.shoppingapp.TotalCostCalculator;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

/**
 * Provides the steps for a Cucumber feature file.
 * Tests the TotalCostCalculator's calculate method.
 * The class uses a ShoppingCart to pass to the calculate method.
 */
public class PurchaseSteps {

  private TotalCostCalculator calculator = new TotalCostCalculator();

  private String state;
  private ShippingType shippingType;

  private ShoppingCart cart = new ShoppingCart();

  @ParameterType("STANDARD|NEXT_DAY")
  public ShippingType setShippingType(String shippingType) {
    if (shippingType.equalsIgnoreCase("STANDARD")) {
      return ShippingType.STANDARD;
    } else {
      return ShippingType.NEXT_DAY;
    }
  }

  @Given("The shopping cart is empty")
  public void the_shopping_cart_is_empty() {}

  @Given("The items are being purchased in the state of {string}")
  public void the_items_are_being_purchased_in(String s) { state = s; }

  @Given("The items are being shipped with {string} shipping")
  public void the_items_are_being_shipped_with(String st) { shippingType = setShippingType(st); }

  @Then("The total cost is {double} dollars")
  public void the_final_cost_is(double expectedAmount) {
    Bill bill = calculator.calculate(cart, state, shippingType);
    assertEquals(expectedAmount, bill.getTotal(), 1e-2);
  }

  @Then("The shopping app throws an exception")
  public void the_shopping_app_throws_an_exception() {
    assertThrows(IllegalArgumentException.class, () -> calculator.calculate(cart, state, shippingType));
  }
}

