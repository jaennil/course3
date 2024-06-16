package ru.dubrovskih.course3;

import data.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import io.restassured.module.jsv.JsonSchemaValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ReqResTests extends BaseTest {

	@Test
	void listUsersTest() {

		ListUsersResponse listUsersResponse = checkStatusCodeGet("/users?page=2", 200)
				.assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ListUsers.json"))
				.extract().as(ListUsersResponse.class);

		assertThat(listUsersResponse).isNotNull()
				.extracting("page", "perPage", "total", "totalPages")
				.containsExactly(2, 6, 12, 2);

		List<User> users = listUsersResponse.getData();

		assertThat(users).allMatch(user -> user.getId() != null);

		assertThat(users).anyMatch(user -> user.getFirstName().equals("Tobias") && user.getLastName().equals("Funke"));
	}

	@Test
	void singleUserTest() {
		SingleUserResponse singleUserResponse = checkStatusCodeGet("/users/2", 200)
				.assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SingleUser.json"))
				.extract().as(SingleUserResponse.class);

		User actualUser = singleUserResponse.getData();

		User expectedUser = new User(2, "janet.weaver@reqres.in", "Janet", "Weaver", "https://reqres.in/img/faces/2-image.jpg");

		assertThat(actualUser).isEqualTo(expectedUser);
	}

	@Test
	void singleUserNotFoundTest() {
		checkStatusCodeGet("/22", 404)
				.assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Empty.json"));
	}

	@Test
	void createUserTest() {
		CreateUser expectedCreatedUser = new CreateUser("morpheus", "leader");

		CreateUser actualCreatedUser = checkStatusCodePost("/users", expectedCreatedUser, 201)
				.assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CreateUser.json"))
				.extract().as(CreateUser.class);

		assertThat(actualCreatedUser).isEqualTo(expectedCreatedUser);
	}

	@Test
	void listResourceTest() {
		ListResourceResponse listResourceResponse = checkStatusCodeGet("/unknown", 200)
				.assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ListResource.json"))
				.extract().as(ListResourceResponse.class);

		List<Resource> resources = listResourceResponse.getData();

		assertThat(resources).allMatch(resource -> resource.getId() != null);
	}

	@Test
	void singleResourceTest() {
		SingleResourceResponse singleResourceResponse = checkStatusCodeGet("/unknown/2", 200)
				.assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SingleResource.json"))
				.extract().as(SingleResourceResponse.class);

		Resource expectedResource = new Resource(2, "fuchsia rose", 2001, "#C74375", "17-2031");

		Resource actualResource = singleResourceResponse.getData();

		assertThat(expectedResource).isEqualTo(actualResource);
	}

	@Test
	void singleResourceNotFoundTest() {
		checkStatusCodeGet("/unknown/23", 404)
				.assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Empty.json"));
	}
}
