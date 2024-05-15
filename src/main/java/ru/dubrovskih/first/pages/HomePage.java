package ru.dubrovskih.first.pages;

import io.qameta.allure.Allure;
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

    @Step("open link https://lambdatest.github.io/sample-todo-app/")
    public HomePage open() {
        driverManager.getDriver().get("https://lambdatest.github.io/sample-todo-app/");
        verifyHeaderPresence();
        return this;
    }

    @Step("verify 'LambdaTest Sample App' header presence")
    public HomePage verifyHeaderPresence() {
        WebElement headerElement = waitUntilElementIsVisible(header);
        Assertions.assertTrue(headerElement.isDisplayed());
        Assertions.assertEquals(headerElement.getText(), "LambdaTest Sample App");
        return this;
    }

    @Step("verify '5 of 5 remaining' text presence")
    public HomePage verifyRemainingTasksTextPresence() {
        WebElement remainingTasksElement = waitUntilElementIsVisible(remainingTodos);
        Assertions.assertTrue(remainingTasksElement.isDisplayed());
        String remainingTasksText = remainingTasksElement.getText();
        Assertions.assertEquals("5 of 5 remaining", remainingTasksText);
        return this;
    }

    public HomePage verifyTodoState(int index, boolean state) {

        // Allure step
        Allure.step(String.format("verify that todo number %s is %s", index + 1, state ? "done" : "not done"), step -> {
            Allure.step(String.format("verify that done-%s css class applied", state), subStep -> {
                WebElement todo = todos.get(index);
                WebElement todoSpan = todo.findElement(By.tagName("span"));
                String expectedClass = String.format("done-%s", state);
                Assertions.assertEquals(expectedClass, todoSpan.getAttribute("class"));
            });
        });

        return this;
    }

    private boolean getTodoState(WebElement todo) {
        WebElement todoInput = todo.findElement(By.tagName("input"));
        return todoInput.isSelected();
    }

    public HomePage clickTodo(int index) {
        int remainingTodosAmount = getRemainingTodosAmount();
        WebElement todo = todos.get(index);
        boolean prevTodoState = getTodoState(todo);
        WebElement todoInput = todo.findElement(By.tagName("input"));
        todoInput.click();

        Allure.step(String.format("mark the todo number %s as %s", index + 1, prevTodoState ? "not done" : "done"), step -> {
            Allure.step(String.format("verify that remaining tasks amount %s by 1", prevTodoState ? "increased" : "decreased"), subStep -> {
                Assertions.assertEquals(getRemainingTodosAmount(), remainingTodosAmount + (prevTodoState ? 1 : -1));
            });
            verifyTodoState(index, !prevTodoState);
        });

        return this;
    }

    private int getRemainingTodosAmount() {
        String remainingTodosText = remainingTodos.getText();
        String[] parts = remainingTodosText.split(" ");
        return Integer.parseInt(parts[0]);
    }

    private int getTotalTodosAmount() {
        String remainingTodosText = remainingTodos.getText();
        String[] parts = remainingTodosText.split(" ");
        return Integer.parseInt(parts[2]);
    }

    @Step("add new todo")
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
