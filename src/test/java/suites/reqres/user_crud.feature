Feature: Reqres request to demonstrate calling other files and post test hooks

  Background: Configure preconditions
    * url baseUrl
    * def user = call read('/utils/create_user.feature')
    * def userId = user.id
    * configure afterScenario =
    """
    function(userId) {
      var info = karate.info;
      karate.log('After hook for: ', info.scenarioName);
      karate.call('utils/delete_user.feature');
    }
    """

  Scenario: TC123408. Create user in utils method, update and delete in after hook
    Given path 'api/users', userId
    * def body = {"name":"test","job":"user"}
    When request body
    And method put
    Then status 200
    And match response.name == "test"
    And match response.job == "user"

