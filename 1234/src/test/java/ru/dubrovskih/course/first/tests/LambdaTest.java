package ru.dubrovskih.course.first.tests;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.dubrovskih.course.CustomTestWatcher;
import ru.dubrovskih.course.first.BaseTests;
import ru.dubrovskih.course.first.pages.HomePage;

public class LambdaTest extends BaseTests {

    @ExtendWith(CustomTestWatcher.class)
    @DisplayName("https://lambdatest.githib.io sample todo app basic tests")
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
