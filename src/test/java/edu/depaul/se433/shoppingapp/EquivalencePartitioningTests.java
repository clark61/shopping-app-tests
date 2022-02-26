/*
 * Project
 * Topic: Equivalence Partitions
 * Author: Nick Clark
 */

package edu.depaul.se433.shoppingapp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.api.Tag;


/**
 * Provides equivalence partitioning tests for the TotalCostCalculator.
 * Tests the calculate method.
 * Includes Strong / Normal tests and Weak / Robust tests.
 * Uses parameterized testing to pass arguments to the calculate() method.
 */
public class EquivalencePartitioningTests {
  private TotalCostCalculator calculator;

  @BeforeEach
  void setup() {
    calculator = new TotalCostCalculator();
  }

  @ParameterizedTest
  @Tag("Equivalence Partitions")
  @MethodSource("provideStrongNormal")
  @DisplayName("Strong Normal Tests")
  public void strongNormal(double initialCost, String state, ShippingType shipping, double expected) {
    assertEquals(expected, calculator.calculate(initialCost, state, shipping), 1e-2);
  }


  @ParameterizedTest
  @Tag("Equivalence Partitions")
  @MethodSource("provideWeakRobustValid")
  @DisplayName("Weak Robust Tests without errors")
  public void weakRobustValid(double initialCost, String state, ShippingType shipping) {
    assertThrows(IllegalArgumentException.class, () -> calculator.calculate(initialCost, state, shipping));
  }

  @ParameterizedTest
  @Tag("Equivalence Partitions")
  @MethodSource("provideWeakRobustInvalid")
  @DisplayName("Weak Robust Tests with errors")
  public void weakRobustInvalid(double initialCost, String state, ShippingType shipping) {

    assertThrows(IllegalArgumentException.class, () -> calculator.calculate(initialCost, state, shipping));
  }

  private static Stream<Arguments> provideStrongNormal() {
    return Stream.of(
        Arguments.of(35000, "IL", ShippingType.STANDARD, 37100),
        Arguments.of(35000, "IL", ShippingType.NEXT_DAY, 37125),
        Arguments.of(35000, "NY", ShippingType.STANDARD, 37100),
        Arguments.of(35000, "NY", ShippingType.NEXT_DAY, 37125),
        Arguments.of(35000, "CA", ShippingType.STANDARD, 37100),
        Arguments.of(35000, "CA", ShippingType.NEXT_DAY, 37125),
        Arguments.of(35000, "WI", ShippingType.STANDARD, 35000),
        Arguments.of(35000, "WI", ShippingType.NEXT_DAY, 35025)
    );
  }

  private static Stream<Arguments> provideWeakRobustValid() {
    return Stream.of(
        Arguments.of(35000, "IL", ShippingType.STANDARD, 37100),
        Arguments.of(35000, "WI", ShippingType.NEXT_DAY, 35025),
        Arguments.of(35000, "IL", ShippingType.NEXT_DAY, 37125)
    );
  }

  private static Stream<Arguments> provideWeakRobustInvalid() {
    return Stream.of(
        Arguments.of(-1, "IL", ShippingType.STANDARD),
        Arguments.of(105000, "WI", ShippingType.NEXT_DAY),
        Arguments.of(35000, "AA", ShippingType.STANDARD)
    );
  }
}
