package ru.dubrovskih.course.fourth;

import ru.dubrovskih.course.fourth.pages.HomePage;
import ru.dubrovskih.course.fourth.pages.RepositoryPage;
import ru.dubrovskih.course.fourth.pages.SearchPage;

public class PageManager {
    private static PageManager pageManager;
    private HomePage homePage;
    private SearchPage searchPage;
    private RepositoryPage repositoryPage;

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

    public SearchPage getSearchPage() {
        if (searchPage == null) {
            searchPage = new SearchPage();
        }

        return searchPage;
    }

    public RepositoryPage getRepositoryPage() {
        if (repositoryPage == null) {
            repositoryPage = new RepositoryPage();
        }

        return repositoryPage;
    }
}

