package ru.dubrovskih.course3.frontend;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.dubrovskih.course3.frontend.managers.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static io.restassured.RestAssured.given;

public class BasePage {
    protected final DriverManager driverManager = DriverManager.getInstance();
    protected WebDriverWait wait;
    protected JavascriptExecutor js = (JavascriptExecutor) driverManager.getDriver();

    public BasePage() {
        resetDriverWait();
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    protected void resetDriverWait() {
        wait = new WebDriverWait(driverManager.getDriver(), Duration.ofSeconds(5), Duration.ofSeconds(1));
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

    protected Response get(String url) {
        return given().when().get(url).then().extract().response();
    }

    protected Response post(String url, String body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .post(url)
                .then().extract().response();
    }

    protected Response put(String url, String body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .put(url)
                .then().extract().response();
    }

    protected Response patch(String url, String body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .patch(url)
                .then().extract().response();
    }

    protected Response delete(String url) {
        return given()
                .delete(url)
                .then().extract().response();
    }


}
