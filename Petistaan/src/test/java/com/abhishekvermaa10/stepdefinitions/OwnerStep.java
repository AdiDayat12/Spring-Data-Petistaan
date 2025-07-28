package com.abhishekvermaa10.stepdefinitions;

import com.abhishekvermaa10.dto.OwnerDTO;
import com.abhishekvermaa10.dto.OwnerPetInfoDTO;
import com.abhishekvermaa10.util.CucumberUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class OwnerStep {
    @LocalServerPort
    private int port;
    private String urlEndpoint;
    private HttpMethod httpMethod;
    private Response response;
    private String requestBody;
    @Given("the endpoint is {word}")
    public void theEndpointIs(String endpoint) {
        urlEndpoint = endpoint;
    }

    @And("the HTTP method is {word}")
    public void theHTTPMethodIsPOST(String method) {
        httpMethod = HttpMethod.valueOf(method);
    }

    @And("the request body is")
    public void theRequestBodyIs(String docString) {
        requestBody = CucumberUtil.cleanJson(docString);
    }

    @When("the request is sent")
    public void theRequestIsSent() {
        response = CucumberUtil.executeApiCall(port, urlEndpoint, httpMethod, requestBody);
    }
    @Then("the response status should be {int}")
    public void theResponseStatusShouldBeInt(int expected) {
        assertNotNull(response, "Response is null");
        assertEquals(expected, response.getStatusCode());
    }

    @And("the response body contains owner ID")
    public void theResponseBodyContainsOwnerID() {
        assertNotNull(response, "Response is null");
        int ownerId = Integer.parseInt(response.jsonPath().getString("data.ownerID"));
        assertTrue(ownerId > 0, "Owner ID should be greater than 0");
    }

    @And("the first error message as {string}")
    public void theFirstErrorMessageIs(String errorMessage) {
        assertNotNull(response, "Response is null");
        String error = response.jsonPath().getString("message");
        assertEquals(errorMessage, error);
    }


    @And("the response body should be OwnerDTO")
    public void theResponseBodyShouldBeOwnerDTO() {
        assertNotNull(response, "Response is null");
        OwnerDTO ownerDTO = response.jsonPath().getObject("data", OwnerDTO.class);
        assertInstanceOf(OwnerDTO.class, ownerDTO);
    }

    @And("the error is {string}")
    public void theErrorIsCanTFindOwnerWithOwnerId(String expectedMessage) {
        assertNotNull(response, "Response is null");
        assertEquals(expectedMessage, response.jsonPath().getString("message"));
    }

    @And("the average age is {double}")
    public void theAverageAgeIs(double expected) {
        assertNotNull(response);
        double average = Double.parseDouble(response.jsonPath().getString("data.average"));
        assertEquals(expected, average);
    }

    @And("the response body should be List of OwnerDTO")
    public void theResponseBodyShouldBeListOfOwnerDTO() {
        assertNotNull(response, "Response is null");
        List<OwnerDTO> list = response.jsonPath().getList("data", OwnerDTO.class);
        for (OwnerDTO e : list) {
            assertInstanceOf(OwnerDTO.class, e);
        }
    }


    @And("the response body should be page of OwnerPetInfoDTO")
    public void theResponseBodyShouldBePageOfOwnerPetInfoDTO() {
        assertNotNull(response, "Response is null");
        List<OwnerPetInfoDTO> content = response.jsonPath().getList("data.content", OwnerPetInfoDTO.class);

        for (OwnerPetInfoDTO e : content) {
            assertInstanceOf(OwnerPetInfoDTO.class, e);
        }
        int totalPages = response.jsonPath().getInt("data.totalPages");
        int totalElements = response.jsonPath().getInt("data.totalElements");
        int number = response.jsonPath().getInt("data.number");

        assertTrue(totalPages > 0);
        assertTrue(totalElements >= content.size());
        assertEquals(0, number);
    }
}
