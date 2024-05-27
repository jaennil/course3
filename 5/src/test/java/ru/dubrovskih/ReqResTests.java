package ru.dubrovskih;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ReqResTests {

    @Test
    public void test1() {
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .statusCode(200);

    }
}
