package ru.dubrovskih.first.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.dubrovskih.first.BaseTests;
import ru.dubrovskih.first.pages.HomePage;

public class LambdaTest extends BaseTests {
    @DisplayName("https://lambdatest.githib.io sample todo app basic tests")
    @Test
    public void basicTests() {

        HomePage homePage = pageManager.getHomePage()
                .open()
                .verifyRemainingTasksTextPresence();

        for (int i = 0; i < 5; i++) {
            homePage.verifyTodoState(i, false)
                    .clickTodo(i);
        }

        homePage.addTodo()
                .clickTodo(5);
    }
}
