package ru.dubrovskih.first.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.dubrovskih.first.BaseTests;
import ru.dubrovskih.first.pages.HomePage;

public class LambdaTest extends BaseTests {
    @DisplayName("aoeu")
    @Test
    public void test() {

        HomePage homePage = pageManager.getHomePage();
        homePage.verifyHeaderPresence()
                .verifyRemainingTasksPresence();

        for (int i = 0; i < 5; i++) {
            homePage.verifyTodoState(i, false)
                    .clickTodo(i);
        }

        homePage.addTodo()
                .clickTodo(5);
    }
}
