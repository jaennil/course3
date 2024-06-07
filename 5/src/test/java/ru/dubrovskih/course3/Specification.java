package ru.dubrovskih.course3;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specification {

	public static RequestSpecification requestSpecification() {
		return new RequestSpecBuilder()
				.setBaseUri("https://reqres.in/api/users")
				.setContentType(ContentType.JSON)
				.build()
				.accept(ContentType.JSON);
	}

	public static ResponseSpecification responseSpecification() {
		return new ResponseSpecBuilder() 
			.expectStatusCode(200)
			.build();
	}

	public static void setupSpecification(RequestSpecification specification) {
		RestAssured.requestSpecification = specification;
	}

	public static void setupSpecification(ResponseSpecification specification) {
		RestAssured.responseSpecification = specification;
	}

	public static void setupSpecification(ResponseSpecification resSpec, RequestSpecification reqSpec) {
		RestAssured.requestSpecification = reqSpec;
		RestAssured.responseSpecification = resSpec;
	}

	public static void resetSpecification() {
		RestAssured.requestSpecification = new RequestSpecBuilder().build();
		RestAssured.responseSpecification = new ResponseSpecBuilder().build();
	}
}
