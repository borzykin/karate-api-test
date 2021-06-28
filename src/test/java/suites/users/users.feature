Feature: Sample karate test script
  for help, see: https://github.com/intuit/karate/wiki/IDE-Support

  Background:
    * url 'https://jsonplaceholder.typicode.com'
    * def user = Java.type('suites.users.model.User')

  Scenario: TC123401. Get all users and then get the first user by id
    Given path 'users'
    When method get
    Then status 200

    * def first = response[0]

    Given path 'users', first.id
    When method get
    Then status 200

  Scenario: TC123402. Create a user and then get it by id
    Given url 'https://jsonplaceholder.typicode.com/users'
    * def body = user.valid()
    And request body
    When method post
    Then status 201

    * def id = response.id
    * print 'created id is: ', id

    Given path id
    # When method get
    # Then status 200
    # And match response contains user
  