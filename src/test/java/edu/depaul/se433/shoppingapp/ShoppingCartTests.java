/*
 * Project
 * Topic: Unit Tests
 * Author: Nick Clark
 */

package edu.depaul.se433.shoppingapp;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests the ShoppingCart class.
 * Tests the itemCount(), cost(), and getItems() methods.
 */
public class ShoppingCartTests {

  // cart will be empty because PurchaseItem is not a public class
  private ShoppingCart cart;

  @BeforeEach
  void setup() {
    cart = new ShoppingCart();
  }

  @Test
  @DisplayName("Return a count of 0 with an empty cart")
  public void testCount() {
    assertEquals(0, cart.itemCount());
  }

  @Test
  @DisplayName("Return a cost of 0.00 for an empty cart")
  public void testCost() {
    assertEquals(0.00, cart.cost());
  }

  @Test
  @DisplayName("Return an empty list for an empty cart")
  public void testGetItems() {
    assertEquals(new HashMap<Long, PurchaseItem>(), cart.getItems());
  }

  @Test
  @DisplayName("Return an empty list after clearing the cart")
  public void testClear() {
    cart.clear();
    assertEquals(new HashMap<Long, PurchaseItem>(), cart.getItems());
  }
}
