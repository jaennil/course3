package ru.dubrovskih.course3;

import org.junit.jupiter.api.Test;

import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.given;

import static ru.dubrovskih.course3.Specification.setupSpecification;
import static ru.dubrovskih.course3.Specification.requestSpecification;
import static ru.dubrovskih.course3.Specification.responseSpecification;

public class ReqResTests {

	@Test
	public void test1() {
		setupSpecification(responseSpecification(), requestSpecification());
		given()
				.when()
				.get("?page=2")
				.then()
				.assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UsersTemplate.json"))
				.extract().as(Users.class)
				.log().all();

	}
}
