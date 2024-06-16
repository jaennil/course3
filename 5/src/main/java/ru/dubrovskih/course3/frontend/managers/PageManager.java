package ru.dubrovskih.course3.frontend.managers;

import ru.dubrovskih.course3.frontend.pages.HomePage;

public class PageManager {
    private static PageManager pageManager;
    private HomePage homePage;

    private PageManager() {
    }

    public static PageManager getInstance() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }

        return pageManager;
    }

    public HomePage getHomePage() {
        if (homePage == null) {
            homePage = new HomePage();
        }

        return homePage;
    }
}
