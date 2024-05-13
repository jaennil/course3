package ru.dubrovskih.first;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.dubrovskih.first.managers.InitManager;
import ru.dubrovskih.first.managers.PageManager;
import ru.dubrovskih.first.managers.DriverManager;

import java.time.Duration;

public class BaseTests {

    protected PageManager pageManager = PageManager.getInstance();

    private final DriverManager driverManager = DriverManager.getInstance();

    @BeforeAll
    public static void beforeAll() {
        InitManager.init();
    }

    @BeforeEach
    public void beforeEach() {
        driverManager.getDriver().get("https://lambdatest.github.io/sample-todo-app/");
    }

    @AfterAll
    public static void afterAll() {
        InitManager.quit();
    }
}
