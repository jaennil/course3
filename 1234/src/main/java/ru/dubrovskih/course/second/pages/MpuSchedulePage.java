package ru.dubrovskih.course.second.pages;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.dubrovskih.course.BasePage;
import ru.dubrovskih.course.second.PageManager;

import java.util.Set;

public class MpuSchedulePage extends BasePage {

    @FindBy(xpath = "//a[@href='https://rasp.dmami.ru/']")
    private WebElement seeOnTheSiteButton;

    public DmamiSchedulePage clickSeeOnTheSiteButton() {
        Allure.step("click 'see on the site' button", step -> {
            waitUntilElementIsVisible(seeOnTheSiteButton);
            scrollToElementJs(seeOnTheSiteButton);
            WebDriver driver = driverManager.getDriver();
            String currentHandle = driver.getWindowHandle();
            switchToNewTab(seeOnTheSiteButton);
            Allure.step("verify that page is opened in a new tab", subStep -> {
                Assertions.assertNotEquals(currentHandle, driver.getWindowHandle(), "new tab is not opened");
            });
        });
        return PageManager.getInstance().getDmamiSchedulePage();
    }

    private void switchToNewTab(WebElement newTabCause) {
        WebDriver driver = driverManager.getDriver();

        Set<String> oldHandles = driver.getWindowHandles();

        newTabCause.click();

        Set<String> newHandles = driver.getWindowHandles();
        for (String handle : newHandles) {

            if (!oldHandles.contains(handle)) {
                driver.switchTo().window(handle);
            }

        }
    }
}
