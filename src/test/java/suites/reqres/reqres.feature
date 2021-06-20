Feature: Requests from reqres.in

  Background: Define URL
    * url baseUrl

  Scenario: TC123406. Get users from page 2
    Given path 'api/users/'
    And param page = 2
    When method get
    Then status 200
    And match response.page == 2
    And match response.data == "#array"
    And match each response.data ==
    """
            {
            "id": #number,
            "email": #string,
            "first_name": #string,
            "last_name": #string,
            "avatar": #regex https:\/\/(.+).(jpg|png)
        }
    """

  Scenario: TC123407. Get user by id
    Given path 'api/users/' + 7
    When method get
    Then status 200
    And match response.data == "#object"
    And match response.support == "#object"
    And match response.data.id == 7