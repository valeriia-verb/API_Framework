package com.restful_booker.tests;

import com.restful_booker.pojo.Booking;
import com.restful_booker.pojo.BookingDates;
import com.restful_booker.utilities.TestBase;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreateBookingTest extends TestBase {

    static int id;
    static Booking requestBody;
    static Booking updatedRequestBody;
    static BookingDates bookingDates;


    /*
    Test Case: Create Booking with Valid Data
Steps:
Send a POST request to /booking with valid booking details.
Validate the status code is 200.
Verify the response contains the booking details and a unique booking ID.

Expected Result:
Status code 200
Response includes a valid booking ID and the provided booking details.
     */


    @Test()
    @Order(1)
    public void createBookingWithValidData() {

        bookingDates = new BookingDates();
        bookingDates.setCheckin("2023-01-01");
        bookingDates.setCheckout("2023-01-10");

        requestBody = new Booking();
        requestBody.setFirstname("Jim");
        requestBody.setLastname("Brown");
        requestBody.setTotalprice(111);
        requestBody.setDepositpaid(true);
        requestBody.setBookingdates(bookingDates);
        requestBody.setAdditionalneeds("Breakfast");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/booking").prettyPeek()
                .then()
                .statusCode(200)
                .body("bookingid", notNullValue())
                .body("booking.firstname", equalTo(requestBody.getFirstname()))
                .body("booking.lastname", equalTo(requestBody.getLastname()))
                .body("booking.totalprice", equalTo(requestBody.getTotalprice()))
                .body("booking.depositpaid", equalTo(requestBody.isDepositpaid()))
                .body("booking.bookingdates.checkin", equalTo(requestBody.getBookingdates().getCheckin()))
                .body("booking.bookingdates.checkout", equalTo(requestBody.getBookingdates().getCheckout()))
                .body("booking.additionalneeds", equalTo(requestBody.getAdditionalneeds()))
                .extract().response();

        id = response.path("bookingid");
        //System.out.println(id);
    }

    /*
    Test Case: Update Booking with Valid Data
Steps:
Send a PUT request to /booking/{id} with a valid booking ID and updated booking details.
Validate the status code is 200.
Verify the response contains the updated booking details.

Expected Result:
Status code 200
Response includes the updated booking details.
     */

    @Test
    @Order(2)
    public void updateBookingWithValidData() {

        bookingDates = new BookingDates();
        bookingDates.setCheckin("2023-01-01");
        bookingDates.setCheckout("2023-01-10");

        updatedRequestBody = new Booking();
        updatedRequestBody.setFirstname("James");
        updatedRequestBody.setLastname("Brown");
        updatedRequestBody.setTotalprice(111);
        updatedRequestBody.setDepositpaid(false);
        updatedRequestBody.setBookingdates(bookingDates);
        updatedRequestBody.setAdditionalneeds("Lunch");


        given()
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .contentType(ContentType.JSON)
                .body(updatedRequestBody)
                .when()
                .put("/booking/" + id).prettyPeek()
                .then()
                .statusCode(200)
                .body("firstname", equalTo(updatedRequestBody.getFirstname()))
                .body("lastname", equalTo(updatedRequestBody.getLastname()))
                .body("totalprice", equalTo(updatedRequestBody.getTotalprice()))
                .body("depositpaid", equalTo(updatedRequestBody.isDepositpaid()))
                .body("bookingdates.checkin", equalTo(updatedRequestBody.getBookingdates().getCheckin()))
                .body("bookingdates.checkout", equalTo(updatedRequestBody.getBookingdates().getCheckout()))
                .body("additionalneeds", equalTo(updatedRequestBody.getAdditionalneeds()));
    }

    /*
    Test Case: Delete Booking with Valid ID
Steps:
Send a DELETE request to /booking/{id} with a valid booking ID.
Validate the status code is 201.
Verify the response indicates the booking was successfully deleted.

Expected Result:
Status code 201
Response indicates successful deletion.
     */

    @Test
    @Order(3)
    public void deleteBookingWithValidID() {
        given()
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .contentType(ContentType.JSON)
                .when()
                .delete("/booking/" + id)
                .then()
                .statusCode(201)
                .body(equalTo("Created"));

        // Verify that the booking no longer exists
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/booking/" + id)
                .then()
                .statusCode(404);
    }

    /*
    Test Case: Create Booking with Missing Required Fields

Steps:
Send a POST request to /booking with the following incomplete details:
firstname: "Jim"
lastname: null (missing)
totalprice: 111
depositpaid: true
checkin: "2023-01-01"
checkout: "2023-01-10"
additionalneeds: "Breakfast"
Validate the response status code is 500.
Verify the response contains an appropriate error message.

Expected Result:
The status code should be 500.
The response should include an error message.
     */

    @Test
    @Order(4)
    public void createBookingWithMissingRequiredFields() {

        bookingDates = new BookingDates();
        bookingDates.setCheckin("2023-01-01");
        bookingDates.setCheckout("2023-01-10");

        requestBody = new Booking();
        requestBody.setFirstname("Jim");
        requestBody.setLastname(null);
        requestBody.setTotalprice(111);
        requestBody.setDepositpaid(true);
        requestBody.setBookingdates(bookingDates);
        requestBody.setAdditionalneeds("Breakfast");

         given()
                .contentType(ContentType.JSON)
                .accept(ContentType.ANY)
                .body(requestBody)
                .when()
                .post("/booking")
                .then()
                .statusCode(500)
                .body(containsString("Internal Server Error"));

    }

    /*
    Test Case: Update Booking with Invalid ID

Steps:
Send a PUT request to /booking/{invalid_id} with the following updated details:
firstname: "James"
lastname: "Brown"
totalprice: 111
depositpaid: false
checkin: "2023-01-01"
checkout: "2023-01-10"
additionalneeds: "Lunch"
Validate the response status code is 405.
Verify the response contains error message "Method Not Allowed".

Expected Result:
The status code should be 405.
The response should include an error message "Method Not Allowed".
     */

    @Test
    @Order(5)
    public void updateBookingWithInvalidID() {
        bookingDates = new BookingDates();
        bookingDates.setCheckin("2023-01-01");
        bookingDates.setCheckout("2023-01-10");

        updatedRequestBody = new Booking();
        updatedRequestBody.setFirstname("James");
        updatedRequestBody.setLastname("Brown");
        updatedRequestBody.setTotalprice(111);
        updatedRequestBody.setDepositpaid(false);
        updatedRequestBody.setBookingdates(bookingDates);
        updatedRequestBody.setAdditionalneeds("Lunch");

        int invalidId = 999999; // Assuming this ID does not exist

        given()
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .contentType(ContentType.JSON)
                .body(updatedRequestBody)
                .when()
                .put("/booking/" + invalidId)
                .then()
                .statusCode(405)
                .body(containsString("Method Not Allowed"));

    }

    /*
    Test Case: Delete Booking with Invalid ID

Steps:
Send a DELETE request to /booking/{invalid_id}.
Validate the response status code is 405.
Verify the response contains an appropriate error message.

Expected Result:
The status code should be 405.
The response should include an error message.
     */

    @Test
    @Order(6)
    public void deleteBookingWithInvalidID() {
        int invalidId = 999999; // Assuming this ID does not exist

        given()
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .contentType(ContentType.JSON)
                .when()
                .delete("/booking/" + invalidId)
                .then()
                .statusCode(405)
                .body(containsString("Method Not Allowed"));
    }





}

