package ru.dubrovskih.course;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.dubrovskih.course.managers.DriverManager;
import ru.dubrovskih.course.second.PageManager;

import java.time.Duration;
import java.util.List;

public class BasePage {
    protected final DriverManager driverManager = DriverManager.getInstance();
    protected final PageManager pageManager = PageManager.getInstance();
    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), Duration.ofSeconds(3), Duration.ofSeconds(1));
    protected JavascriptExecutor js = (JavascriptExecutor) driverManager.getDriver();

    public BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    protected WebElement waitUntilElementIsVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected List<WebElement> waitUntilElementsIsVisible(List<WebElement> elements) {
        return wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    protected WebElement scrollToElementJs(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }

    protected WebElement waitUntilElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected WebElement waitUntilElementIsVisibleLocated(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
