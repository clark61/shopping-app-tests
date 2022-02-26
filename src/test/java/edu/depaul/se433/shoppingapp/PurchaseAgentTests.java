/*
 * Project
 * Topic: Mocks
 * Author: Nick Clark
 */

package edu.depaul.se433.shoppingapp;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

/**
 * Tests the PurchaseAgent class by creating a mock of the PurchaseDBO.
 * Tests the getPurchases(), averagePurchase(), and save() methods.
 */
public class PurchaseAgentTests {

  private PurchaseAgent purchaseAgent;
  private PurchaseDBO mockPurchaseDBO;

  private List<Purchase> zeroPurchases;
  private List<Purchase> singlePurchase;
  private List<Purchase> multiPurchase;

  @BeforeEach
  void addPurchases() {
    zeroPurchases = new ArrayList<>();

    multiPurchase = new ArrayList<Purchase>();

    Purchase oneDollarPurchase = Purchase.make("Clark", LocalDate.now(), 1.00, "IL", "STANDARD");
    Purchase fiveDollarPurchase = Purchase.make("Clark", LocalDate.now(), 5.00, "IL", "STANDARD");
    Purchase sevenDollarPurchase = Purchase.make("Clark", LocalDate.now(), 7.00, "IL", "STANDARD");
    Purchase fiftyDollarPurchase = Purchase.make("Clark", LocalDate.now(), 50.00, "IL", "STANDARD");

    multiPurchase.add(oneDollarPurchase);
    multiPurchase.add(fiveDollarPurchase);
    multiPurchase.add(sevenDollarPurchase);
    multiPurchase.add(fiftyDollarPurchase);
    return;
  }

  @BeforeEach
  void setup() {
    // Assemble app pieces
    mockPurchaseDBO = mock(PurchaseDBO.class);
    purchaseAgent = new PurchaseAgent(mockPurchaseDBO);
  }

  @Test
  @Tag("getPurchases")
  @DisplayName("getPurchases should return a list of purchases from the DB")
  void testGetPurchases() {
    when(mockPurchaseDBO.getPurchases("Clark")).thenReturn(multiPurchase);

    assertEquals(multiPurchase, purchaseAgent.getPurchases("Clark"));
  }

  @Test
  @Tag("getPurchases")
  @DisplayName("getPurchases should return an empty list if there are no purchases")
  void testGetPurchasesEmpty() {
    when(mockPurchaseDBO.getPurchases("Zero Purchases")).thenReturn(zeroPurchases);

    assertEquals(new ArrayList<Purchase>(), purchaseAgent.getPurchases("Zero Purchases"));
  }

  @Test
  @Tag("getPurchases")
  @DisplayName("getPurchases should trap all errors and return an empty list")
  void testGetPurchasesWithError() {
    when(mockPurchaseDBO.getPurchases("Unknown")).thenThrow(new IllegalArgumentException("Unknown User"));

    assertThrows(IllegalArgumentException.class, () -> mockPurchaseDBO.getPurchases("Unknown"));
    assertEquals(new ArrayList<Purchase>(), purchaseAgent.getPurchases("Unknown"));
  }

  @Test
  @Tag("averagePurchase")
  @DisplayName("Average purchase should be 10.00")
  void averagePurchaseSingleItem() {
    singlePurchase = new ArrayList<Purchase>();
    Purchase tenDollarPurchase = Purchase.make("Nick", LocalDate.now(), 10.00, "IL", "STANDARD");
    singlePurchase.add(tenDollarPurchase);

    when(mockPurchaseDBO.getPurchases("Nick")).thenReturn(singlePurchase);

    assertEquals(10.00, purchaseAgent.averagePurchase("Nick"));
  }

  @Test
  @Tag("averagePurchase")
  @DisplayName("Average purchase should be 0.0")
  void averagePurchaseNoItem() {
    when(mockPurchaseDBO.getPurchases("Zero Purchases")).thenReturn(zeroPurchases);

    assertEquals(0.00, purchaseAgent.averagePurchase("Zero Purchases"));
  }

  @Test
  @Tag("averagePurchase")
  @DisplayName("Average purchase should be 15.75")
  void averagePurchaseNominalItems() {
    when(mockPurchaseDBO.getPurchases("Clark")).thenReturn(multiPurchase);

    assertEquals(15.75, purchaseAgent.averagePurchase("Clark"));
  }

  @Test
  @Tag("averagePurchase")
  @DisplayName("Average purchase should be 42.95")
  void averagePurchaseComplex() {
    List<Purchase> complexPurchase = new ArrayList<>();

    Purchase firstPurchase = Purchase.make("Complex", LocalDate.now(), 11.11, "IL", "STANDARD");
    Purchase secondPurchase = Purchase.make("Complex", LocalDate.now(), 23.29, "IL", "STANDARD");
    Purchase thirdPurchase = Purchase.make("Complex", LocalDate.now(), 47.61, "IL", "STANDARD");
    Purchase fourthPurchase = Purchase.make("Complex", LocalDate.now(), 89.77, "IL", "STANDARD");

    complexPurchase.add(firstPurchase);
    complexPurchase.add(secondPurchase);
    complexPurchase.add(thirdPurchase);
    complexPurchase.add(fourthPurchase);

    when(mockPurchaseDBO.getPurchases("Complex")).thenReturn(complexPurchase);

    // Fails. Actual is 42.944999
    assertEquals(42.95, purchaseAgent.averagePurchase("Complex"));
  }

  @Test
  @Tag("save")
  @DisplayName("1 call to save() with a non-null purchase")
  void saveCall() {
    Purchase nonNullPurchase = Purchase.make("Test", LocalDate.now(), 15.00, "IL", "STANDARD");
    purchaseAgent.save(nonNullPurchase);
    verify(mockPurchaseDBO, times(1)).savePurchase(nonNullPurchase);
  }

  @Test
  @Tag("save")
  @DisplayName("0 calls to save() with a null purchase")
  void saveCallNull() {
    Purchase nullPurchase = null;
    purchaseAgent.save(nullPurchase); // throws null pointer exception
    verify(mockPurchaseDBO, times(0)).savePurchase(nullPurchase);
  }
}
