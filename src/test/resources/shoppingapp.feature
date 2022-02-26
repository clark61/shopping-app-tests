Feature: Shopping App Total Cost Calculator
  Scenario: User checks out with 0% tax and standard shipping
    Given The shopping cart is empty
    And The items are being purchased in the state of "MN"
    And The items are being shipped with "STANDARD" shipping
    Then The shopping app throws an exception

  Scenario: User purchased an item with 6% tax and next day shipping
    Given The shopping cart is empty
    And The items are being purchased in the state of "IL"
    And The items are being shipped with "NEXT_DAY" shipping
    Then The shopping app throws an exception

  Scenario: User purchased an item with 0% tax and next day shipping
    Given The shopping cart is empty
    And The items are being purchased in the state of "WI"
    And The items are being shipped with "NEXT_DAY" shipping
    Then The shopping app throws an exception

  Scenario: User purchased an item with 6% tax and standard shipping
    Given The shopping cart is empty
    And The items are being purchased in the state of "NY"
    And The items are being shipped with "STANDARD" shipping
    Then The shopping app throws an exception
