package ru.dubrovskih.course3.frontend;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import ru.dubrovskih.course3.frontend.managers.InitManager;
import ru.dubrovskih.course3.frontend.managers.PageManager;

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
