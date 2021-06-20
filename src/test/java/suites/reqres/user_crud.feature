Feature: Reqres request to demonstrate calling other files and post test hooks

  Background: Configure preconditions
    * url baseUrl
    * def uuid = function(){ return java.util.UUID.randomUUID() + '' }
    * def uuid = call uuid
    * def user = call read('/utils/create_user.feature')
    * def userId = user.userId
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
    * def body = {"name":"#(uuid)","job":"user"}
    When request body
    And method put
    Then status 200
    And match response.name == uuid
    And match response.job == "user"

