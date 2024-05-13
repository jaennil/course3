package ru.dubrovskih.first.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.dubrovskih.first.managers.DriverManager;

import java.time.Duration;

public class BasePage {
    protected final DriverManager driverManager = DriverManager.getInstance();
    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), Duration.ofSeconds(3), Duration.ofSeconds(1));

    public BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    protected WebElement waitUntilElementIsVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
}
