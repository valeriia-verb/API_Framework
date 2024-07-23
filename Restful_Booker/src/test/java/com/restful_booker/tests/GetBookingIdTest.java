package com.restful_booker.tests;

import com.restful_booker.utilities.TestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)  //To share state between tests, you can use a static variable or use the JUnit 5 TestInstance annotation with TestInstance.Lifecycle.PER_CLASS.
public class GetBookingIdTest extends TestBase {

    static int id;

    /*
    Test Case: Get All Booking IDs
Steps:
Send a GET request to /booking.
Validate the status code is 200.
Verify the response contains a list of booking IDs.

Expected Result:
Status code 200
Response includes a list of booking IDs.
     */

    @Test
    @Order(1)
    public void getBookingID() {

        Response response = given().log().all()
                .accept(ContentType.JSON)
                .when()
                .get("/booking")
                .then()
                .statusCode(200).extract().response();

        id = response.jsonPath().getInt("[0].bookingid");

    }

    /*
    Test Case: Get Booking with Valid ID
Steps:
Send a GET request to /booking/{id} with a valid booking ID.
Validate the status code is 418.
Verify the response contains the correct booking details.

Expected Result:
Status code 418
Response includes the correct booking details for the given ID.
     */

    @Test
    @Order(2)
    public void getBookingWithValidID() {

        Response response = given().log().all()
                .accept(ContentType.JSON)
                .when()
                .get("/booking/"+id).prettyPeek()
                .then()
                .statusCode(418).extract().response();
    }

}
