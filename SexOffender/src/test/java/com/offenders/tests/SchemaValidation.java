package com.offenders.tests;

import com.offenders.utilities.TestBase;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class SchemaValidation extends TestBase {

    /*
    Test Case 1: Valid Response Schema
    Test Description: Verify that the response schema for a valid request matches the predefined schema.

    Steps:
1. Send a valid request to the API (e.g., search by name, location, or offense type).
2. Receive the response from the API.
3. Validate the response JSON against the predefined schema.
    Expected Result:
The response JSON should match the predefined schema without any discrepancies.
     */

    String key = System.getenv("key");

    @Test
    @DisplayName("Schema Error: expectation failed")
    public void schema() {
        given()
                .accept(ContentType.JSON)
                .header("x-rapidapi-key", key)
                .queryParam("firstName", "Joseph")
                .queryParam("lastName", "Nigro")
                .queryParam("zipcode", "10465")
                .when().get("/sexoffender").prettyPeek()
                .then().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema.json"));
    }

    @Test
    @DisplayName("Successful Schema Validation")
    public void schema2() {
        given()
                .accept(ContentType.JSON)
                .header("x-rapidapi-key", key )
                .queryParam("lastName", "Lagunas")
                .queryParam("city", "Chicago")
                .when().get("/sexoffender").prettyPeek()
                .then().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema.json"));
    }

    //if the schema gets updated I only need to update the file or change the file name
    //not the code itself
}
