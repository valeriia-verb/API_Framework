package com.offenders.utilities;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.reset;

public class TestBase {

    @BeforeAll
    public static void init(){
        baseURI="https://sex-offenders.p.rapidapi.com";
    }

    @AfterAll
    public static void destroy(){
        reset();
    }
}
