package com.abhishekvermaa10.util;

import static io.restassured.RestAssured.given;

import org.springframework.http.HttpMethod;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CucumberUtil {
    public static String cleanJson(String docString) {
        return docString.replace("\"null\"", "null");
    }

    public static Response executeApiCall(int port, String urlEndpoint, HttpMethod httpMethod,
                                          String requestBody) {
        try {
            if (HttpMethod.POST.equals(httpMethod)) {
                return given()
                        .port(port)
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .post(urlEndpoint);
            } else if (HttpMethod.GET.equals(httpMethod)) {
                return given()
                        .port(port)
                        .get(urlEndpoint);
            } else if (HttpMethod.PATCH.equals(httpMethod)) {
                return given()
                        .port(port)
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .patch(urlEndpoint);
            } else if (HttpMethod.DELETE.equals(httpMethod)) {
                return given()
                        .port(port)
                        .delete(urlEndpoint);
            } else {
                throw new IllegalArgumentException("Unsupported HTTP method: " + httpMethod);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute API call: " + e.getMessage(), e);
        }
    }
}
