package ru.dubrovskih.course.second.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.dubrovskih.course.CustomTestWatcher;
import ru.dubrovskih.course.second.BaseTests;
import ru.dubrovskih.course.second.pages.HomePage;

public class MospolytechScheduleTest extends BaseTests {

    @ExtendWith(CustomTestWatcher.class)
    @DisplayName("https://mospolytech.ru schedule test")
    @Test
    public void basicTests() {
        pageManager.getHomePage().open()
                .openSideMenuSection(HomePage.LeftNavigationMenuSection.SCHEDULE)
                .clickSeeOnTheSiteButton()
                .fillGroupSearchField("221-361")
                .clickOnTheFoundedGroup();
    }
}
