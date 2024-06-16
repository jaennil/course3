package ru.dubrovskih.course3.frontend;

import io.restassured.response.Response;
import ru.dubrovskih.course3.frontend.managers.InitManager;
import ru.dubrovskih.course3.frontend.managers.PageManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public class BaseTest {
    protected PageManager pageManager = PageManager.getInstance();

    @BeforeAll
    public static void beforeAll() {
        InitManager.init();
    }

    @AfterAll
    public static void afterAll() {
        InitManager.quit();
    }

}
