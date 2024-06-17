package ru.dubrovskih.course.fourth.pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.dubrovskih.course.BasePage;

import java.util.List;

public class TrendingPage extends BasePage {

    @FindBy(tagName = "article")
    private List<WebElement> repositories;

    @FindBy(xpath = "//details[@id = 'select-menu-language']")
    private WebElement languageFilterButton;

    @FindBy(xpath = "//div[@id = 'languages-menuitems']/div/a")
    private List<WebElement> languagesMenuItems;

    @Step("verify trending page opened")
    public TrendingPage verifyTrendingPageOpened() {
        final String titlePart = "Trending repositories on GitHub today";
        wait.until(ExpectedConditions.titleContains(titlePart));

        String title = driverManager.getDriver().getTitle();

        Assertions.assertTrue(title.contains(titlePart));

        return this;
    }

    @Step("apply filter by programming language to '{language}'")
    public TrendingPage applyFilterByProgrammingLanguage(String language) {

        waitUntilElementIsVisible(languageFilterButton);
        waitUntilElementToBeClickable(languageFilterButton);
        languageFilterButton.click();

        WebElement languageFilterSubmenu = languageFilterButton.findElement(By.tagName("details-menu"));

        Allure.step("verify language filter submenu opened", () -> {
            waitUntilElementIsVisible(languageFilterSubmenu);
            Assertions.assertTrue(languageFilterSubmenu.isDisplayed());
        });

        WebElement languageFilterSearchInput = languageFilterSubmenu.findElement(By.xpath(".//filter-input/input"));

        Allure.step(String.format("enter in the search field programming language name: %s", language), () -> {
            waitUntilElementIsVisible(languageFilterSearchInput);
            waitUntilElementToBeClickable(languageFilterSearchInput);
            languageFilterSearchInput.click();
            languageFilterSearchInput.sendKeys(language);
        });

        Allure.step("press enter", () -> {

            wait.until(d -> {
                WebElement displayedLanguage = languagesMenuItems.stream().filter(WebElement::isDisplayed).findFirst().orElse(null);

                if (displayedLanguage == null) {
                    return false;
                }

                return displayedLanguage.findElement(By.tagName("span")).getText().equals(language);
            });

            languageFilterSearchInput.sendKeys(Keys.RETURN);

            verifyFilterApplied(language);
        });

        return this;
    }

    @Step("verify filter by language '{language}' applied")
    private void verifyFilterApplied(String language) {
        String titlePart = String.format("Trending %s repositories", language);

        wait.until(ExpectedConditions.titleContains(titlePart));

        String title = driverManager.getDriver().getTitle();

        Assertions.assertTrue(title.contains(titlePart));
    }

    @Step("log first {amount} repositories")
    public TrendingPage logRepositories(int amount) {

        waitUntilElementsIsVisible(repositories);

        System.out.println();
        for (int i = 0; i < amount; i++) {
            WebElement repository = repositories.get(i);

            String repositoryName = repository.findElement(By.xpath(".//h2/a")).getText();

            System.out.printf("%s. %s%n", i + 1, repositoryName);
        }
        System.out.println();

        return this;
    }
}
