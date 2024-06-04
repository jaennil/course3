package ru.dubrovskih.course.first.pages;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindAll;
import ru.dubrovskih.course.second.BasePage;

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
	private WebElement remainingTodos;

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

	public HomePage verifyRemainingTasksTextPresence() {
		Allure.step("verify '5 of 5 remaining' text presence", step -> {
			WebElement remainingTasksElement = waitUntilElementIsVisible(remainingTodos);
			Assertions.assertTrue(remainingTasksElement.isDisplayed(), "remaining tasks text is not displayed");
			String remainingTasksText = remainingTasksElement.getText();
			Assertions.assertEquals("5 of 5 remaining", remainingTasksText,
					"remaining tasks text is not equal to '5 of 5 remaining'");
		});
		return this;
	}

	public HomePage verifyTodoState(int index, boolean state) {

		Allure.step(String.format("verify that todo number %s is %s", index + 1, state ? "done" : "not done"), step -> {
			Allure.step(String.format("verify that done-%s css class applied", state), subStep -> {
				WebElement todo = todos.get(index);
				WebElement todoSpan = todo.findElement(By.tagName("span"));
				String expectedClass = String.format("done-%s", state);
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
							prevTodoState ? "increased" : "decreased"), subStep -> {
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

			Assertions.assertEquals(totalTodosAmount + 1, getTotalTodosAmount(),
					"total todos amount is not increased by 1");
			Assertions.assertEquals(remainingTodosAmount + 1, getRemainingTodosAmount(),
					"remaining tasks amount is not increased by 1");

		});
		return this;
	}
}
