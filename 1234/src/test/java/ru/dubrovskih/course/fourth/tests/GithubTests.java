package ru.dubrovskih.course.fourth.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.dubrovskih.course.CustomTestWatcher;
import ru.dubrovskih.course.fourth.BaseTests;

public class GithubTests extends BaseTests {

    @ExtendWith(CustomTestWatcher.class)
    @DisplayName("https://github.com search test")
    @Test
    public void searchTest() {

        final String input = "rust-lang/rust";

        pageManager.getHomePage()
                .open().search(input)
                .verifyFirstFoundedRepositoryName(input)
                .clickOnTheRepository(input)
                .verifyTitleContains(input);
    }

    @ExtendWith(CustomTestWatcher.class)
    @DisplayName("https://github.com contributors test")
    @Test
    public void contributorsTest() {
        pageManager.getRepositoryPage().open("rust-lang/rust")
                .clickContributorsButton().verifyContributorsPageOpened()
                .logContributors(5);
    }
}
