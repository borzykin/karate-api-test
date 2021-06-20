@ignore
Feature: Create user

  Background: Define URL
    * url baseUrl

  Scenario: Create user utils method
    Given path 'api/users'
    * def user =
    """
    {
    "name": "morpheus",
    "job": "leader"
    }
    """
    And request user
    When method post
    Then status 201
    * def user = response
