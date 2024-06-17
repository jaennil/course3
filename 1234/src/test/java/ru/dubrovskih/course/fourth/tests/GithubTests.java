package ru.dubrovskih.course.fourth.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.dubrovskih.course.CustomTestWatcher;
import ru.dubrovskih.course.fourth.BaseTests;

public class GithubTests extends BaseTests {

    @Test
    @DisplayName("https://github.com search test")
    @ExtendWith(CustomTestWatcher.class)
    public void searchTest() {

        final String input = "rust-lang/rust";

        pageManager.getHomePage()
                .open().search(input)
                .verifyFirstFoundedRepositoryName(input)
                .clickOnTheRepository(input)
                .verifyTitleContains(input);
    }

    @Test
    @DisplayName("https://github.com contributors test")
    @ExtendWith(CustomTestWatcher.class)
    public void contributorsTest() {
        pageManager.getRepositoryPage().open("rust-lang/rust")
                .clickContributorsButton().verifyContributorsPageOpened()
                .logContributors(5);
    }

    @Test
    @DisplayName("https://github.com trending repositories filters test")
    @ExtendWith(CustomTestWatcher.class)
    public void trendingRepositoriesFiltersTest() {
        pageManager.getHomePage().open().hoverHeaderMenuItem("Open Source").clickOpenSourceRepositoriesButton("Trending").verifyTrendingPageOpened().applyFilterByProgrammingLanguage("Rust")
                .logRepositories(5);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
