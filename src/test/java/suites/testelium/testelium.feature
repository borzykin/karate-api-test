Feature: Testelium SMS

  Background:
    * url "https://api.testelium.com"

  Scenario: Test SMS delivery
    # authorize, post call with body like {"email":"","password":""}
    Given path "/api/auth/login"
    * def authBody = read('/utils/auth.json')
    * request authBody
    When method post
    Then status 200
    * def auth = responseHeaders['Authorization'][0]

    # send test (mocked response, todo change URL)
    Given url "http://localhost:8080/"
    And path "/api/send"
    * header Authorization = auth
    * def sendBody =
    """
    {
    "mcc_mnc" : 25503,
    "callback_url": "https://your-site.com/callback"
    }
    """
    * request sendBody
    When method post
    Then status 200
    And response.status == "success"
    * def messageKey = response.data.message_key
    * def msisdn = response.data.msisdn
    * def uuid = response.data.uuid

    # send sms with provider to number=msisdn containing message=messageKey

    # check result at testelium (mocked response, todo change URL)
    Given url "http://localhost:8080/"
    And path "/api/result/", uuid
    * header Authorization = auth
    When method get
    Then status 200
    And response.status == "success"
    And match response.data.sender_delivered == "#string"
    * print response.data.sender_delivered
