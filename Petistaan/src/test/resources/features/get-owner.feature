Feature: Get Owner
  Scenario: Successfully get owner
    Given the endpoint is /petistaan/owners/2
    And the HTTP method is GET
    When the request is sent
    Then the response status should be 200
    And the response body should be OwnerDTO


  Scenario Outline: Failed to get owner
    Given the endpoint is /petistaan/owners/<ownerId>
    And the HTTP method is GET
    When the request is sent
    Then the response status should be <responseStatus>
    And the error is "<errorMessage>"
    Examples:
    |ownerId|responseStatus|errorMessage|
    |-1     |400           |Owner id must be a positive number.    |
    |100    |404           |Can't find owner with ownerId 100.      |


  Scenario: Get all owners
    Given the endpoint is /petistaan/owners/all
    And the HTTP method is GET
    When the request is sent
    Then the response status should be 200
    And the response body should be List of OwnerDTO

  Scenario: Get all owners in page
    Given the endpoint is /petistaan/owners/page
    And the HTTP method is GET
    When the request is sent
    Then the response status should be 200
    And the response body should be page of OwnerPetInfoDTO

