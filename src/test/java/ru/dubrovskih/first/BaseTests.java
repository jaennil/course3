package ru.dubrovskih.first;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.dubrovskih.first.managers.DriverManager;
import ru.dubrovskih.first.managers.InitManager;
import ru.dubrovskih.first.managers.PageManager;

public class BaseTests {

    private final DriverManager driverManager = DriverManager.getInstance();
    protected PageManager pageManager = PageManager.getInstance();

    @BeforeAll
    public static void beforeAll() {
        InitManager.init();
    }

    @AfterAll
    public static void afterAll() {
        InitManager.quit();
    }

    @BeforeEach
    public void beforeEach() {
        driverManager.getDriver().get("https://lambdatest.github.io/sample-todo-app/");
    }
}
