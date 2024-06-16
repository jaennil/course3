package ru.dubrovskih.course3.frontend.pages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.support.FindBy;
import ru.dubrovskih.course3.frontend.BasePage;

import java.util.List;

public class HomePage extends BasePage {

    private final String BASE_URL = "https://reqres.in";
    @FindBy(xpath = "//ul/li[@data-key='endpoint']")
    private List<WebElement> requestButtons;
    @FindBy(xpath = "//span[@data-key='url']")
    private WebElement requestUrlSpan;
    @FindBy(xpath = "//span[@data-key='response-code']")
    private WebElement responseCodeSpan;
    @FindBy(xpath = "//pre[@data-key='output-response']")
    private WebElement responseOutputElement;
    @FindBy(xpath = "//pre[@data-key='output-request']")
    private WebElement requestOutput;

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

        waitUntilElementIsVisible(requestUrlSpan);
        String requestUrn = requestUrlSpan.getText();
        String requestUrl = BASE_URL + requestUrn;


        Response response = switch (httpMethod) {
            case DELETE -> delete(requestUrl);
            case GET -> get(requestUrl);
            case POST -> {
                waitUntilElementIsVisible(requestOutput);
                yield post(requestUrl, requestOutput.getText());
            }
            case PUT -> {
                waitUntilElementIsVisible(requestOutput);
                yield put(requestUrl, requestOutput.getText());
            }
            case PATCH -> {
                waitUntilElementIsVisible(requestOutput);
                yield patch(requestUrl, requestOutput.getText());
            }
            default -> null;
        };

        Assertions.assertNotNull(response, "wrong http method: " + httpMethod);

        waitUntilElementIsVisible(responseCodeSpan);
        int expectedStatusCode = Integer.parseInt(responseCodeSpan.getText());

        int actualStatusCode = response.statusCode();
        Assertions.assertEquals(expectedStatusCode, actualStatusCode, "api and frontend status codes doesn't match");

        if (httpMethod == HttpMethod.DELETE) {
            return this;
        }

        waitUntilElementIsVisible(responseOutputElement);
        String expectedResponseOutput = responseOutputElement.getText();

        String actualResponseOutput = response.getBody().asString();

        Assertions.assertDoesNotThrow(() -> compareJsons(expectedResponseOutput, actualResponseOutput));

        return this;
    }

    @Step("compare api and frontend apis")
    private void compareJsons(String json1, String json2) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode expectedResponseOutput = mapper.readTree(json1);
        JsonNode actualResponseOutput = mapper.readTree(json2);
        ObjectNode expectedObjectNode = (ObjectNode) expectedResponseOutput;
        ObjectNode actualObjectNode = (ObjectNode) actualResponseOutput;

        expectedObjectNode.remove("id");
        expectedObjectNode.remove("createdAt");
        expectedObjectNode.remove("updatedAt");
        actualObjectNode.remove("id");
        actualObjectNode.remove("createdAt");
        actualObjectNode.remove("updatedAt");
        Assertions.assertEquals(expectedResponseOutput, actualResponseOutput, "api and frontend response bodies doesn't match");
    }

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

}
