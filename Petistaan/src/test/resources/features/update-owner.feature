Feature: Update owner

  Scenario: Successfully update
    Given the endpoint is /petistaan/owners
    And the HTTP method is PATCH
    And the request body is
    """
      {
        "ownerId" : 2,
        "petName" : "Mikhaaa"
      }
    """
    When the request is sent
    Then the response status should be 200

  Scenario Outline: Failed to update Owner
    Given the endpoint is /petistaan/owners
    And the HTTP method is PATCH
    And the request body is
    """
    {
      "ownerId": "<ownerId>",
      "petName": "<petName>"
    }
    """
    When the request is sent
    Then the response status should be 404
    And the first error message as "<errorMessage>"
    Examples:
    |ownerId|petName|errorMessage|
    |-1     |Mikhaaa|Can't find owner with ownerId -1.    |
    |100    |Mikhaaa|Can't find owner with ownerId 100.      |
