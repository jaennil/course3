package ru.dubrovskih.first.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(tagName = "h2")
    private WebElement header;

    public void checkHeaderIsPresent() {
        WebElement headerElement = waitUntilElementIsVisible(header);
        Assertions.assertEquals(headerElement.getText(), "LambdaTest Sample App");
    }

}
