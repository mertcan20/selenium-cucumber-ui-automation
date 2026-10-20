@local
Feature: Controlled shop regression
  # These criteria describe the fixture, not N11 or a real payment provider.
  Background:
    Given the practice shop is open

  @smoke
  Scenario: Valid credentials open the shop
    When I sign in with "demo@example.test" and "demo-pass"
    Then the shop is visible

  Scenario Outline: Invalid login is rejected
    When I sign in with "<email>" and "<password>"
    Then the login error is "<error>"
    Examples:
      | email             | password  | error                           |
      | wrong@example.test| demo-pass | Invalid credentials             |
      | demo@example.test | wrong     | Invalid credentials             |
      |                   | demo-pass | Email and password are required |

  @cart
  Scenario: Cart total and removal
    When I sign in with "demo@example.test" and "demo-pass"
    And I add 2 items
    Then the cart has 2 items and total "50.00"
    When I remove the items
    Then the cart has 0 items and total "0.00"

  Scenario Outline: Invalid quantity does not change the cart
    When I sign in with "demo@example.test" and "demo-pass"
    And I add <quantity> items
    Then the shop notice is "<notice>"
    And the cart has 0 items and total "0.00"
    Examples:
      | quantity | notice                              |
      | 0        | Quantity must be a positive integer  |
      | -1       | Quantity must be a positive integer  |
      | 3        | Insufficient stock                  |

  @smoke
  Scenario: Checkout changes stock once and an empty retry creates no order
    When I sign in with "demo@example.test" and "demo-pass"
    And I add 1 items
    And I check out
    Then the shop notice is "Order created"
    And the stock is 1 and order count is 1
    And the cart has 0 items and total "0.00"
    When I check out
    Then the shop notice is "Cart is empty"
    And the stock is 1 and order count is 1
