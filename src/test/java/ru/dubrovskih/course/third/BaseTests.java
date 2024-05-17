package ru.dubrovskih.course.third;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import ru.dubrovskih.course.managers.InitManager;

public class BaseTests {

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
