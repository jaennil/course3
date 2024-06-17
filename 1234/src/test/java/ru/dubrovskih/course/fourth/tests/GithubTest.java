package ru.dubrovskih.course.fourth.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.dubrovskih.course.CustomTestWatcher;
import ru.dubrovskih.course.fourth.BaseTests;

public class GithubTest extends BaseTests {

    @ExtendWith(CustomTestWatcher.class)
    @DisplayName("https://github.com basic tests")
    @Test
    public void basicTests() {

        final String input = "rust-lang/rust";

        pageManager.getHomePage()
                .open().search(input)
                .verifyFirstFoundedRepositoryName(input)
                .clickOnTheRepository(input)
                .verifyTitleContains(input);

        try {
            Thread.sleep(3000);
        } catch (Exception ignored) {

        }
    }
}
