package ru.dubrovskih.course.fourth;

import ru.dubrovskih.course.fourth.pages.HomePage;

public class PageManager {
    private static PageManager pageManager;
    private HomePage homePage;

    private PageManager() {
    }

    static PageManager getInstance() {
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

