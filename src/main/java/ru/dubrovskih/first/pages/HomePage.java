package ru.dubrovskih.first.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends BasePage {

    @FindBy(tagName = "h2")
    private WebElement header;

    @FindBy(xpath = "//span[@class='ng-binding']")
    private WebElement remainingTodos;

    @FindBy(xpath = "//ul/li")
    private List<WebElement> todos;

    @FindBy(id = "addbutton")
    private WebElement addButton;

    @Step()
    public HomePage verifyHeaderPresence() {
        WebElement headerElement = waitUntilElementIsVisible(header);
        Assertions.assertTrue(headerElement.isDisplayed());
        Assertions.assertEquals(headerElement.getText(), "LambdaTest Sample App");
        return this;
    }

    @Step()
    public HomePage verifyRemainingTasksPresence() {
        WebElement remainingTasksElement = waitUntilElementIsVisible(remainingTodos);
        Assertions.assertTrue(remainingTasksElement.isDisplayed());
        String remainingTasksText = remainingTasksElement.getText();
        Assertions.assertEquals("5 of 5 remaining", remainingTasksText);
        return this;
    }

    @Step()
    public HomePage verifyTodoState(int index, boolean state) {
        WebElement todo = todos.get(index);
        WebElement todoSpan = todo.findElement(By.tagName("span"));
        String expectedClass = String.format("done-%s", state);
        Assertions.assertEquals(expectedClass, todoSpan.getAttribute("class"));
        return this;
    }

    @Step()
    private boolean getTodoState(WebElement todo) {
        WebElement todoInput = todo.findElement(By.tagName("input"));
        return todoInput.isSelected();
    }

    @Step()
    public HomePage clickTodo(int index) {
        int remainingTodosAmount = getRemainingTodosAmount();
        WebElement todo = todos.get(index);
        boolean todoState = getTodoState(todo);
        WebElement todoInput = todo.findElement(By.tagName("input"));
        todoInput.click();
        if (todoState) {
            Assertions.assertEquals(getRemainingTodosAmount(), remainingTodosAmount + 1);
            verifyTodoState(index, false);
        } else {
            Assertions.assertEquals(getRemainingTodosAmount(), remainingTodosAmount - 1);
            verifyTodoState(index, true);
        }
        return this;
    }

    @Step()
    private int getRemainingTodosAmount() {
        String remainingTodosText = remainingTodos.getText();
        String[] parts = remainingTodosText.split(" ");
        return Integer.parseInt(parts[0]);
    }

    @Step()
    private int getTotalTodosAmount() {
        String remainingTodosText = remainingTodos.getText();
        String[] parts = remainingTodosText.split(" ");
        return Integer.parseInt(parts[2]);
    }

    @Step()
    public HomePage addTodo() {
        int totalTodosAmount = getTotalTodosAmount();
        int remainingTodosAmount = getRemainingTodosAmount();

        addButton.click();

        verifyTodoState(todos.size() - 1, false);

        Assertions.assertEquals(getTotalTodosAmount(), totalTodosAmount + 1);
        Assertions.assertEquals(getRemainingTodosAmount(), remainingTodosAmount + 1);

        return this;
    }
}
