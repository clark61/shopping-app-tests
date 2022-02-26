/*
 * Project
 * Topic: Boundary Testing
 * Author: Nick Clark
 */

package edu.depaul.se433.shoppingapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.api.Tag;

import java.util.stream.Stream;


/**
 *  Normal boundary testing for the TotalCostCalculator.
 *  Uses parameterized testing to pass arguments to the calculate() method.
 */
public class BoundaryTests {
  private TotalCostCalculator calculator;

  @BeforeEach
  void setup() {
    // Assemble app pieces
    calculator = new TotalCostCalculator();
  }

  @ParameterizedTest
  @Tag("Calculator Bounds")
  @MethodSource("provideStandard0")
  @DisplayName("Standard, 0% Tax")
  public void standard0(double initialCost, String state, ShippingType shipping, double expected) {
    assertEquals(expected, calculator.calculate(initialCost, state, shipping), 1e-2);
  }

  @ParameterizedTest
  @Tag("Calculator Bounds")
  @MethodSource("provideStandard6")
  @DisplayName("Standard, 6% Tax")
  public void standard6(double initialCost, String state, ShippingType shipping, double expected) {
    assertEquals(expected, calculator.calculate(initialCost, state, shipping), 1e-2);
  }

  @ParameterizedTest
  @Tag("Calculator Bounds")
  @MethodSource("provideNextDay0")
  @DisplayName("Next Day, 0% Tax")
  public void nextDay0(double initialCost, String state, ShippingType shipping, double expected) {
    assertEquals(expected, calculator.calculate(initialCost, state, shipping), 1e-2);
  }

  @ParameterizedTest
  @Tag("Calculator Bounds")
  @MethodSource("provideNextDay6")
  @DisplayName("Next Day, 6% Tax")
  public void nextDay6(double initialCost, String state, ShippingType shipping, double expected) {
    assertEquals(expected, calculator.calculate(initialCost, state, shipping), 1e-2);
  }

  @ParameterizedTest
  @Tag("Shipping Bounds")
  @MethodSource("standardShippingBounds0")
  @DisplayName("Standard, 0% Tax")
  public void standardShipping0(double initialCost, String state, ShippingType shipping, double expected) {
    assertEquals(expected, calculator.calculate(initialCost, state, shipping), 1e-2);
  }

  @ParameterizedTest
  @Tag("Shipping Bounds")
  @MethodSource("standardShippingBounds6")
  @DisplayName("Standard, 6% Tax")
  public void standardShipping6(double initialCost, String state, ShippingType shipping, double expected) {
    assertEquals(expected, calculator.calculate(initialCost, state, shipping), 1e-2);
  }

  private static Stream<Arguments> provideStandard0() {
    // WI state abbreviation for 0% tax
    return Stream.of(
        Arguments.of(1, "WI", ShippingType.STANDARD, 11),
        Arguments.of(1.01, "WI", ShippingType.STANDARD, 11.01),
        Arguments.of(50000, "WI", ShippingType.STANDARD, 50000),
        Arguments.of(99999.98, "WI", ShippingType.STANDARD, 99999.98),
        Arguments.of(99999.99, "WI", ShippingType.STANDARD, 99999.99)
    );
  }

  private static Stream<Arguments> provideStandard6() {
    // IL state abbreviation for 6% tax
    return Stream.of(
        Arguments.of(1, "IL", ShippingType.STANDARD, 11.06),
        Arguments.of(1.01, "IL", ShippingType.STANDARD, 11.06),
        Arguments.of(50000, "IL", ShippingType.STANDARD, 53000),
        Arguments.of(99999.98, "IL", ShippingType.STANDARD, 105999.98),
        Arguments.of(99999.99, "IL", ShippingType.STANDARD, 105999.99)
    );
  }

  private static Stream<Arguments> provideNextDay0() {
    // WI state abbreviation for 0% tax
    return Stream.of(
        Arguments.of(1, "WI", ShippingType.NEXT_DAY, 26),
        Arguments.of(1.01, "WI", ShippingType.NEXT_DAY, 26.01),
        Arguments.of(50000, "WI", ShippingType.NEXT_DAY, 50026),
        Arguments.of(99999.98, "WI", ShippingType.NEXT_DAY, 100025.98),
        Arguments.of(99999.99, "WI", ShippingType.NEXT_DAY, 100025.99)
    );
  }

  private static Stream<Arguments> provideNextDay6() {
    // IL state abbreviation for 6% tax
    return Stream.of(
        Arguments.of(1, "IL", ShippingType.NEXT_DAY, 26.06),
        Arguments.of(1.01, "IL", ShippingType.NEXT_DAY, 26.07),
        Arguments.of(50000, "IL", ShippingType.NEXT_DAY, 53025),
        Arguments.of(99999.98, "IL", ShippingType.NEXT_DAY, 160024.97),
        Arguments.of(99999.99, "IL", ShippingType.NEXT_DAY, 160024.98)
    );
  }

  private static Stream<Arguments> standardShippingBounds0() {
    // WI state abbreviation for 0% tax
    return Stream.of(
        Arguments.of(49, "WI", ShippingType.STANDARD, 59),
        Arguments.of(50, "WI", ShippingType.STANDARD, 60),
        Arguments.of(51, "WI", ShippingType.STANDARD, 51)
    );
  }

  private static Stream<Arguments> standardShippingBounds6() {
    // IL state abbreviation for 6% tax
    return Stream.of(
        Arguments.of(49, "IL", ShippingType.STANDARD, 61.94),
        Arguments.of(50, "IL", ShippingType.STANDARD, 63),
        Arguments.of(51, "IL", ShippingType.STANDARD, 54.06)
    );
  }
}
