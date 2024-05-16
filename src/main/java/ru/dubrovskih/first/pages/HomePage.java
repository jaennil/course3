package ru.dubrovskih.first.pages;

import io.qameta.allure.Allure;
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

    public HomePage open() {
        Allure.step("open link https://lambdatest.github.io/sample-todo-app/ %s", step -> {
            driverManager.getDriver().get("https://lambdatest.github.io/sample-todo-app/");
            verifyHeaderPresence();
        });
        return this;
    }

    public HomePage verifyHeaderPresence() {
        Allure.step("verify 'LambdaTest Sample App' header presence", step -> {
            WebElement headerElement = waitUntilElementIsVisible(header);
            Assertions.assertTrue(headerElement.isDisplayed(), "header 'LambdaTest Sample App' is not displayed");
            Assertions.assertEquals(headerElement.getText(), "LambdaTest Sample App", "header text is not equal to 'LambdaTest Sample App'");
        });
        return this;
    }

    public HomePage verifyRemainingTasksTextPresence() {
        Allure.step("verify '5 of 5 remaining' text presence", step -> {
            WebElement remainingTasksElement = waitUntilElementIsVisible(remainingTodos);
            Assertions.assertTrue(remainingTasksElement.isDisplayed(), "remaining tasks text is not displayed");
            String remainingTasksText = remainingTasksElement.getText();
            Assertions.assertEquals("5 of 5 remaining", remainingTasksText, "remaining tasks text is not equal to '5 of 5 remaining'");
        });
        return this;
    }

    public HomePage verifyTodoState(int index, boolean state) {

        // Allure step
        Allure.step(String.format("verify that todo number %s is %s", index + 1, state ? "done" : "not done"), step -> {
            Allure.step(String.format("verify that done-%s css class applied", state), subStep -> {
                WebElement todo = todos.get(index);
                WebElement todoSpan = todo.findElement(By.tagName("span"));
                String expectedClass = String.format("done-%s", state);
                Assertions.assertEquals(expectedClass, todoSpan.getAttribute("class"), String.format("css class 'done-%s' is not applied", state));
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
                Assertions.assertEquals(getRemainingTodosAmount(), remainingTodosAmount + (prevTodoState ? 1 : -1), "remaining tasks amount is not " + (prevTodoState ? "increased" : "decreased") + " by 1");
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

    public HomePage addTodo() {
        Allure.step("add new todo", step -> {
            int totalTodosAmount = getTotalTodosAmount();
            int remainingTodosAmount = getRemainingTodosAmount();

            addButton.click();

            verifyTodoState(todos.size() - 1, false);

            Assertions.assertEquals(getTotalTodosAmount(), totalTodosAmount + 1, "total todos amount is not increased by 1");
            Assertions.assertEquals(getRemainingTodosAmount(), remainingTodosAmount + 1, "remaining tasks amount is not increased by 1");

        });
        return this;
    }
}
