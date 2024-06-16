package ru.dubrovskih.course3;

import data.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

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
        checkStatusCodeGet("/users/22", 404)
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

    @Test
    void updateUserTest() {
        CreateUser expectedUpdateUser = new CreateUser("morpheus", "zion resident");

        Instant nowInstant = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();

        UpdateUserResponse actualUpdateUser = checkStatusCodePut("/users/2", expectedUpdateUser, 200)
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UpdateUser.json"))
                .extract().as(UpdateUserResponse.class);

        Instant updatedAtInstant = Instant.parse(actualUpdateUser.getUpdatedAt());

        Duration difference = Duration.between(nowInstant, updatedAtInstant);

        assertThat(Math.abs(difference.getSeconds())).isLessThan(7);

        assertThat(expectedUpdateUser).extracting("name", "job").containsExactly(actualUpdateUser.getName(), actualUpdateUser.getJob());

    }

	@Test
	void updateUserPatchTest() {
		CreateUser expectedUpdateUser = new CreateUser("morpheus", "zion resident");

		Instant nowInstant = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();

		UpdateUserResponse actualUpdateUser = checkStatusCodePatch("/users/2", expectedUpdateUser, 200)
				.assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UpdateUser.json"))
				.extract().as(UpdateUserResponse.class);

		Instant updatedAtInstant = Instant.parse(actualUpdateUser.getUpdatedAt());

		Duration difference = Duration.between(nowInstant, updatedAtInstant);

		assertThat(Math.abs(difference.getSeconds())).isLessThan(7);

		assertThat(expectedUpdateUser).extracting("name", "job").containsExactly(actualUpdateUser.getName(), actualUpdateUser.getJob());
	}

	@Test
	void deleteUserTest() {
		checkStatusCodeDelete("/users/2", 204)
                .assertThat().body(emptyOrNullString());
	}

	@Test
	void registerSuccessfulTest() {
		AuthDTO registerDTO = new AuthDTO("eve.holt@reqres.in", "pistol");

        RegisterResponse registerResponse = checkStatusCodePost("/register", registerDTO, 200)
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Register.json"))
                .extract().as(RegisterResponse.class);

        assertThat(registerResponse).extracting("id", "token").allMatch(Objects::nonNull);
	}

    @Test
    void registerUnsuccessfulTest() {
        Map response = checkStatusCodePost("/register", Map.of("email", "sydney@fife"), 400)
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Error.json"))
                .extract().as(Map.class);

        assertThat(response.get("error")).isEqualTo("Missing password");
    }

    @Test
    void loginSuccessful() {
        AuthDTO loginData = new AuthDTO("eve.holt@reqres.in", "cityslicka");

        Map loginResponse = checkStatusCodePost("/login", loginData, 200)
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Login.json"))
                .extract().as(Map.class);

        assertThat(loginResponse.get("token")).isNotNull();
    }

    @Test
    void loginUnsuccessful() {
        Map response = checkStatusCodePost("/login", Map.of("email", "peter@klaven"), 400)
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Error.json"))
                .extract().as(Map.class);

        assertThat(response.get("error")).isEqualTo("Missing password");
    }

    @Test
    void delayedResponseTest() {
        ListUsersResponse listUsersResponse = checkStatusCodeGet("/users?delay=3", 200)
                .time(greaterThan(3000L)).and().time(lessThan(4000L))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ListUsers.json"))
                .extract().as(ListUsersResponse.class);

        assertThat(listUsersResponse).isNotNull()
                .extracting("page", "perPage", "total", "totalPages")
                .containsExactly(1, 6, 12, 2);

        List<User> users = listUsersResponse.getData();

        assertThat(users).allMatch(user -> user.getId() != null);
    }
}
