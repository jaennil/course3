package ru.dubrovskih.first.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends BasePage {

    @FindBy(tagName = "h2")
    private WebElement header;

    @FindBy(xpath = "//span[@class='ng-binding']")
    private WebElement remainingTasks;

    @FindBy(xpath = "//ul/li")
    private List<WebElement> todos;

    public HomePage verifyHeaderPresence() {
        WebElement headerElement = waitUntilElementIsVisible(header);
        Assertions.assertTrue(headerElement.isDisplayed());
        Assertions.assertEquals(headerElement.getText(), "LambdaTest Sample App");
        return this;
    }

    public HomePage verifyRemainingTasksPresence() {
        WebElement remainingTasksElement = waitUntilElementIsVisible(remainingTasks);
        Assertions.assertTrue(remainingTasksElement.isDisplayed());
        String remainingTasksText = remainingTasksElement.getText();
        Assertions.assertEquals("5 of 5 remaining", remainingTasksText);
        return this;
    }

    public HomePage verifyTodoState(int index, boolean state) {
        WebElement todo = todos.get(index);
        WebElement todoSpan = todo.findElement(By.tagName("span"));
        String expectedClass = String.format("done-%s", state);
        Assertions.assertEquals(expectedClass, todoSpan.getAttribute("class"));
        return this;
    }
}
