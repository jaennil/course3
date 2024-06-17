package ru.dubrovskih.course.fourth.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.dubrovskih.course.BasePage;
import ru.dubrovskih.course.fourth.PageManager;

import java.util.List;

public class HomePage extends BasePage {

    @FindBy(xpath = "//qbsearch-input")
    private WebElement searchInput;

    @FindBy(id = "query-builder-test")
    private WebElement subSearchInput;

    @FindBy(xpath = "//header//nav/ul/li")
    private List<WebElement> headerMenuItems;

    @FindBy(xpath = "//span[@id='open-source-repositories-heading']/following-sibling::ul/li")
    private List<WebElement> openSourceRepositoriesButtons;

    @Step("open link https://github.com")
    public HomePage open() {
        driverManager.getDriver().get("https://github.com");

        verifyOpened();

        return this;
    }

    @Step("verify github.com home page opened")
    private void verifyOpened() {
        final String titlePart = "GitHub: Let’s build from here";

        wait.until(ExpectedConditions.titleContains(titlePart));

        String title = driverManager.getDriver().getTitle();

        Assertions.assertTrue(title.contains(titlePart));
    }

    @Step("click on the search input")
    public SearchPage search(String input) {
        waitUntilElementIsVisible(searchInput);
        waitUntilElementToBeClickable(searchInput);
        searchInput.click();

        waitUntilElementIsVisible(subSearchInput);

        subSearchInput.sendKeys(input);

        subSearchInput.sendKeys(Keys.RETURN);

        return PageManager.getInstance().getSearchPage();
    }

    @Step("hover header menu item '{name}'")
    public HomePage hoverHeaderMenuItem(String name) {

        waitUntilElementsIsVisible(headerMenuItems);

        for (WebElement menuItem : headerMenuItems) {
            String menuItemName = menuItem.findElement(By.tagName("button")).getText();
            if (menuItemName.equals(name)) {
                Actions actions = new Actions(driverManager.getDriver());
                actions.moveToElement(menuItem).perform();
                break;
            }
        }

        return this;
    }

    @Step("click button '{name}' in the Open Source submenu")
    public TrendingPage clickOpenSourceRepositoriesButton(String name) {

        waitUntilElementsIsVisible(openSourceRepositoriesButtons);

        WebElement foundedButton = null;
        for (WebElement button : openSourceRepositoriesButtons) {
            String buttonName = button.findElement(By.tagName("a")).getText();
            if (buttonName.equals(name)) {
                foundedButton = button;
                break;
            }
        }

        Assertions.assertNotNull(foundedButton, String.format("can't find button with name '%s'", name));

        waitUntilElementToBeClickable(foundedButton);
        foundedButton.click();

        return PageManager.getInstance().getTrendingPage();
    }

}
