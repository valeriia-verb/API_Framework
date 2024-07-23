package com.offenders.tests;

import com.offenders.utilities.TestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetRecordsTest extends TestBase {
    // read environment variable "key" to avoid hardcoding it in the code
    String key = System.getenv("key");

    /*
    User Story 01: Search by Name
As a user,
I want to search for sex offenders by their name,
so that I can obtain detailed information about them.

    Acceptance Criteria:

Given a valid name, when I search for it, then I should receive a list of offenders matching the name.    */
    @Test
    public void test01(){
        Response response = given()//.log().all()
                .accept(ContentType.JSON)
                .header("x-rapidapi-key", key) //8a758c2715msh95fa080821b5073p1f3689jsn9fc4f3235248
                .queryParam("firstName", "Joseph")
                .queryParam("lastName", "Nigro")
                .when().get("/sexoffender").prettyPeek();

        //assertEquals(200, response.statusCode());

        assertAll("",
                () -> assertEquals(200, response.statusCode()),
                () -> assertEquals("application/json; charset=utf-8", response.contentType()),
                () -> assertEquals("Nigro", response.jsonPath().getString("offenders[0].lastName"))
        );
    }

     /*
    User Story 02: Search by City
As a user,
I want to search for sex offenders by the city,
so that I can obtain detailed information about them.

    Acceptance Criteria:

Given a valid city name, when I search for it, then I should receive a list of offenders matching.    */

    @Test
    public void test02(){
        given()//.log().all()
                .accept(ContentType.JSON)
                .header("x-rapidapi-key", key)
                .queryParam("city", "Chicago")
                .queryParam("zipcode", "60659")
                .when().get("/sexoffender")//.prettyPeek()

                .then().statusCode(200)
                .contentType("application/json; charset=utf-8")
                //check every offender's state is Illinois
                .body("offenders.state", everyItem(is("Illinois")))
                //check there are 19 results
                .body("offenders", hasSize(19))
                //check the last offender's last name is Salgado
                .body("offenders[-1].lastName", is("Salgado"))
                //check if response time is between 500 and 2000 milliseconds
                .time(both(greaterThan(500L)).and(lessThan(2000L)));

    }
}
