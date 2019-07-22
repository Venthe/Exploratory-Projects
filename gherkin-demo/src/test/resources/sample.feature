@Smoke
Feature: Test mock
  In order to realize a named business value
  As an explicit system actor
  I want to gain some beneficial outcome which furthers the goal

  Background:
    Given I execute step one

  Scenario: Test passing scenario
    Given a persistence set up with "mocked text"
    When I execute step two
    Then I get result "mocked text: Mocked second result"

  Scenario Outline: Test passing scenario
    Given a persistence set up with "<mockedText>"
    When I execute step two
    Then I get result "<expectedResult>"

    Examples:
      | mockedText | expectedResult          |
      | 1          | 1: Mocked second result |
      | 2          | 2: Mocked second result |

  @Disabled
  Scenario: Test failing scenario
    Given a persistence set up with "mocked text"
    When I execute step two
    Then I get result "not mocked text: Mocked second result"
