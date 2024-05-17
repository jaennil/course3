package ru.dubrovskih.course.second;

import ru.dubrovskih.course.second.pages.DmamiSchedulePage;
import ru.dubrovskih.course.second.pages.HomePage;
import ru.dubrovskih.course.second.pages.MpuSchedulePage;

public class PageManager {
    private static PageManager pageManager;
    private HomePage homePage;
    private MpuSchedulePage mpuSchedulePage;
    private DmamiSchedulePage dmamiSchedulePage;

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

    public MpuSchedulePage getMpuSchedulePage() {
        if (mpuSchedulePage == null) {
            mpuSchedulePage = new MpuSchedulePage();
        }

        return mpuSchedulePage;
    }

    public DmamiSchedulePage getDmamiSchedulePage() {
        if (dmamiSchedulePage == null) {
            dmamiSchedulePage = new DmamiSchedulePage();
        }

        return dmamiSchedulePage;
    }
}
