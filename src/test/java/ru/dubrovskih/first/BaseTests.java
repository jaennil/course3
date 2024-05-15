package ru.dubrovskih.first;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import ru.dubrovskih.first.managers.InitManager;
import ru.dubrovskih.first.managers.PageManager;

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
