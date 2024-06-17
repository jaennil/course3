package ru.dubrovskih.course.fourth;

import ru.dubrovskih.course.fourth.pages.*;

public class PageManager {
    private static PageManager pageManager;
    private HomePage homePage;
    private SearchPage searchPage;
    private RepositoryPage repositoryPage;
    private ContributorsPage contributorsPage;
    private TrendingPage trendingPage;

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

    public ContributorsPage getContributorsPage() {
        if (contributorsPage == null) {
            contributorsPage = new ContributorsPage();
        }

        return contributorsPage;
    }

    public TrendingPage getTrendingPage() {
        if (trendingPage == null) {
            trendingPage = new TrendingPage();
        }

        return trendingPage;
    }
}

