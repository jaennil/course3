package ru.dubrovskih.course.fourth.pages;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.dubrovskih.course.BasePage;

public class RepositoryPage extends BasePage {

    @Step("verify title contains '{text}'")
    public RepositoryPage verifyTitleContains(String text) {
        wait.until(ExpectedConditions.titleContains(text));

        String title = driverManager.getDriver().getTitle();

        Assertions.assertTrue(title.contains(text));

        return this;
    }
}
