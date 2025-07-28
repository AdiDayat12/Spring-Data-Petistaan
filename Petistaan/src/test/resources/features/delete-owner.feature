Feature: Delete Owner
  Scenario: Successfully delete owner by ID
    Given the endpoint is /petistaan/owners/1
    And the HTTP method is DELETE
    When the request is sent
    Then the response status should be 200
