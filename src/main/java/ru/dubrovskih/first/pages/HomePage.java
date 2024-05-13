package ru.dubrovskih.first.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(tagName = "h2")
    private WebElement header;

    @FindBy(xpath = "//span[@class='ng-binding']")
    private WebElement remainingTasks;

    public HomePage verifyHeaderPresence() {
        WebElement headerElement = waitUntilElementIsVisible(header);
        Assertions.assertTrue(headerElement.isDisplayed());
        Assertions.assertEquals(headerElement.getText(), "LambdaTest Sample App");
        return this;
    }

//    public void verifyRemainingTasks(int amount) {
//        WebElement remainingTasksElement = waitUntilElementIsVisible(remainingTasks);
//        Assertions.assertTrue(remainingTasksElement.isDisplayed());
//        String remainingTasksText = remainingTasksElement.getText();
//        Assertions.assertEquals(remainingTasksText, amount + " of 5 remaining");
//    }

    public HomePage verifyRemainingTasksPresence() {
        WebElement remainingTasksElement = waitUntilElementIsVisible(remainingTasks);
        Assertions.assertTrue(remainingTasksElement.isDisplayed());
        String remainingTasksText = remainingTasksElement.getText();
        Assertions.assertEquals("5 of 5 remaining", remainingTasksText);
        return this;
    }
}
