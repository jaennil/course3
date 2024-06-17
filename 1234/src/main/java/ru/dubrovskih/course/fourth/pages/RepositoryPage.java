package ru.dubrovskih.course.fourth.pages;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.dubrovskih.course.BasePage;
import ru.dubrovskih.course.fourth.PageManager;

public class RepositoryPage extends BasePage {

    @FindBy(xpath = "//a[contains(text(), 'Contributors')]")
    private WebElement contributorsButton;

    @Step("open repository page https://github.com/{repositoryName}")
    public RepositoryPage open(String repositoryName) {
        driverManager.getDriver().get("https://github.com/"+repositoryName);

        verifyTitleContains(repositoryName);

        return this;
    }

    @Step("verify title contains '{text}'")
    public RepositoryPage verifyTitleContains(String text) {
        wait.until(ExpectedConditions.titleContains(text));

        String title = driverManager.getDriver().getTitle();

        Assertions.assertTrue(title.contains(text));

        return this;
    }

    public ContributorsPage clickContributorsButton() {

        waitUntilElementIsVisible(contributorsButton);
        scrollToElementJs(contributorsButton);
        waitUntilElementToBeClickable(contributorsButton);

        contributorsButton.click();

        return PageManager.getInstance().getContributorsPage();
    }
}
