package ru.dubrovskih.course.fourth.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.dubrovskih.course.CustomTestWatcher;
import ru.dubrovskih.course.fourth.BaseTests;
import ru.dubrovskih.course.fourth.pages.HomePage;

public class GithubTest extends BaseTests {

    @ExtendWith(CustomTestWatcher.class)
    @DisplayName("https://github.com basic tests")
    @Test
    public void basicTests() {

        HomePage homePage = pageManager.getHomePage()
                .open();

        try {
            Thread.sleep(3000);
        } catch (Exception ignored) {

        }
    }
}
