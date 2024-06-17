package ru.dubrovskih.course.first.pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import ru.dubrovskih.course.BasePage;

import java.util.List;

public class HomePage extends BasePage {

    @FindAll({
            @FindBy(tagName = "h1"),
            @FindBy(tagName = "h2"),
            @FindBy(tagName = "h3"),
            @FindBy(tagName = "h4"),
            @FindBy(tagName = "h5"),
            @FindBy(tagName = "h6")
    })
    private List<WebElement> headers;

    @FindBy(xpath = "//span[@class='ng-binding']")
    private WebElement remainingTodosSpan;

    @FindBy(xpath = "//ul/li")
    private List<WebElement> todos;

    @FindBy(id = "addbutton")
    private WebElement addButton;

    public HomePage open() {
        Allure.step("open link https://lambdatest.github.io/sample-todo-app/", step -> {
            driverManager.getDriver().get("https://lambdatest.github.io/sample-todo-app/");
            verifyHeaderPresence("LambdaTest Sample App");
        });
        return this;
    }

    public HomePage verifyHeaderPresence(String text) {
        Allure.step(String.format("verify '%s' header presence", text), step -> {
            waitUntilElementsIsVisible(headers);
            WebElement actualHeader = headers
                    .stream()
                    .filter(header -> header.getText().equals(text))
                    .findFirst()
                    .orElse(null);
            Assertions.assertNotNull(actualHeader, String.format("header '%s' is not founded", text));
            Assertions.assertTrue(actualHeader.isDisplayed(),
                    String.format("header '%s' is not displayed", text));
            Assertions.assertEquals(text, actualHeader.getText(),
                    String.format("header text is not equal to '%s'", text));
        });
        return this;
    }

    @Step("verify '5 of 5 remaining' text presence")
    public HomePage verifyRemainingTasksTextPresence() {
        String expectedText = "5 of 5 remaining";
        waitUntilElementIsVisible(remainingTodosSpan);
        Assertions.assertTrue(remainingTodosSpan.isDisplayed(), "remaining tasks text is not displayed");
        Assertions.assertEquals(expectedText, remainingTodosSpan.getText(),
                String.format("remaining tasks text is not equal to '%s'", expectedText));
        return this;
    }

    public HomePage verifyTodoState(int index, boolean state) {

        Allure.step(String.format("verify that todo number %s is %s", index + 1, state ? "done" : "not done"), step -> {
            WebElement todo = todos.get(index);
            WebElement todoSpan = todo.findElement(By.tagName("span"));
            String expectedClass = String.format("done-%s", state);

            Allure.step(String.format("verify that done-%s css class applied", state), substep -> {
                Assertions.assertEquals(expectedClass, todoSpan.getAttribute("class"),
                        String.format("css class 'done-%s' is not applied", state));
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

        Allure.step(String.format("mark the todo number %s as %s", index + 1, prevTodoState ? "not done" : "done"),
                step -> {
                    Allure.step(String.format("verify that remaining tasks amount %s by 1",
                            prevTodoState ? "increased" : "decreased"), substep -> {
                        Assertions.assertEquals(remainingTodosAmount + (prevTodoState ? 1 : -1),
                                getRemainingTodosAmount(),
                                "remaining tasks amount is not " + (prevTodoState ? "increased" : "decreased")
                                        + " by 1");
                    });
                    verifyTodoState(index, !prevTodoState);
                });

        return this;
    }

    private int getRemainingTodosAmount() {
        String remainingTodosText = remainingTodosSpan.getText();
        String[] parts = remainingTodosText.split(" ");
        return Integer.parseInt(parts[0]);
    }

    private int getTotalTodosAmount() {
        String remainingTodosText = remainingTodosSpan.getText();
        String[] parts = remainingTodosText.split(" ");
        return Integer.parseInt(parts[2]);
    }

    @Step("add new todo")
    public HomePage addTodo() {
        int totalTodosAmount = getTotalTodosAmount();
        int remainingTodosAmount = getRemainingTodosAmount();

        addButton.click();

        verifyTodoState(todos.size() - 1, false);

        Allure.step("verify that total and remaining todos amount is increased by 1", step -> {
            Assertions.assertEquals(totalTodosAmount + 1, getTotalTodosAmount(),
                    "total todos amount is not increased by 1");
            Assertions.assertEquals(remainingTodosAmount + 1, getRemainingTodosAmount(),
                    "remaining tasks amount is not increased by 1");
        });

        return this;
    }
}
