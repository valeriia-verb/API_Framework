package com.restful_booker.utilities;


import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.reset;

public class TestBase {

    @BeforeAll
    public static void init(){
        baseURI = "https://restful-booker.herokuapp.com";
    }

    @AfterAll
    public static void destroy(){
        reset();
    }
}
