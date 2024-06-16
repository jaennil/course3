package ru.dubrovskih.course3.frontend.pages;

import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.support.FindBy;
import ru.dubrovskih.course3.frontend.BasePage;
import io.qameta.allure.Step;

import java.util.List;

public class HomePage extends BasePage {

    @Getter
    public enum RequestButton {
        LIST_USERS("LIST USERS", HttpMethod.GET),
        SINGLE_USER("SINGLE USER", HttpMethod.GET),
        SINGLE_USER_NOT_FOUND("SINGLE USER NOT FOUND", HttpMethod.GET),
        LIST_RESOURCE("LIST <RESOURCE>", HttpMethod.GET),
        SINGLE_RESOURCE("SINGLE <RESOURCE>", HttpMethod.GET),
        SINGLE_RESOURCE_NOT_FOUND("SINGLE <RESOURCE> NOT FOUND", HttpMethod.GET),
        CREATE("CREATE", HttpMethod.POST),
        UPDATE("UPDATE", HttpMethod.PUT),
        UPDATE_PATCH("UPDATE", HttpMethod.PATCH),
        DELETE("DELETE", HttpMethod.DELETE),
        REGISTER_SUCCESSFUL("REGISTER - SUCCESSFUL", HttpMethod.POST),
        REGISTER_UNSUCCESSFUL("REGISTER - UNSUCCESSFUL", HttpMethod.POST),
        LOGIN_SUCCESSFUL("LOGIN - SUCCESSFUL", HttpMethod.POST),
        LOGIN_UNSUCCESSFUL("LOGIN - UNSUCCESSFUL", HttpMethod.POST),
        DELAYED_RESPONSE("DELAYED RESPONSE", HttpMethod.GET);

        private final String name;
        private final HttpMethod method;

        RequestButton(String name, HttpMethod method) {
            this.name = name;
            this.method = method;
        }

    }

    @FindBy(xpath = "//ul/li[@data-key='endpoint']")
    private List<WebElement> requestButtons;

    @Step("open link https://reqres.in")
    public HomePage open() {
        driverManager.getDriver().get("https://reqres.in");
        return this;
    }

    @Step("click request button '{name}' with method '{httpMethod}'")
    public HomePage clickRequestButton(String name, HttpMethod httpMethod) {

        waitUntilElementsIsVisible(requestButtons);

        WebElement foundedButton = null;

        for (WebElement button : requestButtons) {

            String buttonMethod = button.getAttribute("data-http").toUpperCase();

            if (!buttonMethod.equals(httpMethod.name())) {
                continue;
            }

            String buttonName = button.findElement(By.xpath(".//a")).getText();

            if (buttonName.equals(name)) {
                foundedButton = button;
                break;
            }
        }

        Assertions.assertNotNull(foundedButton, String.format("request button with name '%s' and http method '%s' not founded", name, httpMethod));

        scrollToElementJs(foundedButton);

        waitUntilElementToBeClickable(foundedButton);

        foundedButton.click();

        return this;
    }
}
