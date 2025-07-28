Feature: Pet API
  Scenario: Successfully getting Pet
    Given the endpoint is /petistaan/pets/2
    And the HTTP method is GET
    When the request is sent
    Then the response status should be 200


  Scenario Outline: Failed to get pet
    Given the endpoint is /petistaan/pets/<petId>
    And the HTTP method is GET
    When the request is sent
    Then the response status should be <responseStatus>
    And the error is "<errorMessage>"
    Examples: 
    |petId|responseStatus|errorMessage|
    |-1   |400           |Pet id must be a positive number.|
    |100  |404           |Can't find pet with petId 100.|

  Scenario: Get average age
    Given the endpoint is /petistaan/pets/avg
    And the HTTP method is GET
    When the request is sent
    Then the response status should be 200
    And the average age is 7.0