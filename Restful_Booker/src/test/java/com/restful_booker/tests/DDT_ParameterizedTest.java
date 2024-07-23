package com.restful_booker.tests;

import com.restful_booker.pojo.Booking;
import com.restful_booker.pojo.BookingDates;
import com.restful_booker.utilities.ExcelUtil;
import com.restful_booker.utilities.TestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class DDT_ParameterizedTest extends TestBase {

    static int id;

    /*
    Test Case: Create Booking with Valid Data using Csv file as the data source

Preconditions:
The application is up and running.
The CSV file (bookings.csv) containing the booking details is available in the src/test/resources directory.

Steps:
Read data from the CSV file.
Send a POST request to /booking with the following details:
firstname
lastname
totalprice
depositpaid
checkin
checkout
additionalneeds
Validate the response status code is 200.
Verify the response contains the booking details and a unique booking ID.

Expected Result:
The status code should be 200.
The response should include a valid booking ID and the provided booking details.
     */

    @ParameterizedTest
    @CsvFileSource(resources = "/bookings.csv", numLinesToSkip = 1)
    @Order(1)
    public void createBookingWithValidData(String firstname, String lastname, int totalprice, boolean depositpaid,
                                           String checkin, String checkout, String additionalneeds) {

        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin(checkin);
        bookingDates.setCheckout(checkout);

        Booking requestBody = new Booking();
        requestBody.setFirstname(firstname);
        requestBody.setLastname(lastname);
        requestBody.setTotalprice(totalprice);
        requestBody.setDepositpaid(depositpaid);
        requestBody.setBookingdates(bookingDates);
        requestBody.setAdditionalneeds(additionalneeds);

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
    }


    /*
    Test Case: Update Booking with Valid Data using Excel as the data source

Preconditions:
The application is up and running.
An initial booking is created and its ID is stored.
The Excel file (updated_bookings.xlsx) containing the updated booking details is available in the src/test/resources directory.

Steps:
Read data from the Excel file.
Send a PUT request to /booking/{id} with the following updated details:
firstname
lastname
totalprice
depositpaid
checkin
checkout
additionalneeds
Validate the response status code is 200.
Verify the response contains the updated booking details.

Expected Result:
The status code should be 200.
The response should include the updated booking details.
     */

    @ParameterizedTest
    @MethodSource("excelDataProvider")
    @Order(2)
    public void updateBookingWithValidData(String firstname, String lastname, int totalprice, boolean depositpaid,
                                           String checkin, String checkout, String additionalneeds) {

        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin(checkin);
        bookingDates.setCheckout(checkout);

        Booking updatedRequestBody = new Booking();
        updatedRequestBody.setFirstname(firstname);
        updatedRequestBody.setLastname(lastname);
        updatedRequestBody.setTotalprice(totalprice);
        updatedRequestBody.setDepositpaid(depositpaid);
        updatedRequestBody.setBookingdates(bookingDates);
        updatedRequestBody.setAdditionalneeds(additionalneeds);

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
                .body("bookingdates.checkin", equalTo(String.valueOf(updatedRequestBody.getBookingdates().getCheckin())))
                .body("bookingdates.checkout", equalTo(String.valueOf(updatedRequestBody.getBookingdates().getCheckout())))
                .body("additionalneeds", equalTo(updatedRequestBody.getAdditionalneeds()));
    }

    //The excelDataProvider method calls readExcelData to provide data to the parameterized test method.
    public static List<Object[]> excelDataProvider() throws IOException {
        return ExcelUtil.readExcelData("src/test/resources/updated_bookings.xlsx");
    }



}
