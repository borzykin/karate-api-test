@ignore
Feature: Delete user util

  Background: Define URL
    * url baseUrl

  Scenario: Delete user utils method
    Given path 'api/users', userId
    When method delete
    Then status 204
