package ru.dubrovskih.course3;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public class BaseTest {

    static final String BASE_URL = "https://reqres.in/api";
    static RequestSpecification requestSpecification;

    @BeforeAll
    public static void setup() {
        requestSpecification = given()
                .baseUri(BASE_URL)
                .log().all()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    static ValidatableResponse checkStatusCodeGet(String url, int code) {
        return given(requestSpecification)
                .get(url)
                .then()
                .log().all()
                .statusCode(code);
    }

    static ValidatableResponse checkStatusCodePost(String url, Object body, int code) {
        return given(requestSpecification)
                .body(body)
                .post(url)
                .then()
                .log().all()
                .statusCode(code);
    }

    static ValidatableResponse checkStatusCodePut(String url, Object body, int code) {
        return given(requestSpecification)
                .body(body)
                .put(url)
                .then()
                .log().all()
                .statusCode(code);
    }

    static ValidatableResponse checkStatusCodePatch(String url, Object body, int code) {
        return given(requestSpecification)
                .body(body)
                .patch(url)
                .then()
                .log().all()
                .statusCode(code);
    }
}
