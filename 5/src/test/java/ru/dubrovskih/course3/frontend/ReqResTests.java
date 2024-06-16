package ru.dubrovskih.course3.frontend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.dubrovskih.course3.frontend.pages.HomePage;

public class ReqResTests extends BaseTest {

    @Test
    @ExtendWith(CustomTestWatcher.class)
    @DisplayName("https://reqres.in basic test")
    public void basicTest() {
        HomePage homePage = pageManager.getHomePage().open();

        for (HomePage.RequestButton button : HomePage.RequestButton.values()) {
            homePage.clickRequestButton(button.getName(), button.getMethod());
        }
    }

}
