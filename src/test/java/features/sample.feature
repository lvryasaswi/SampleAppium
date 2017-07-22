Feature: Sample Feature
  Scenario: Simple login scenario
    When I launch the test app
    And I choose the google login
    Then I see account picker with my email "picstand@gmail.com"
