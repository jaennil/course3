package ru.dubrovskih.course.third.pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.dubrovskih.course.BasePage;

import java.time.Duration;
import java.util.List;


public class CategoryPage extends BasePage {

    @FindBy(xpath = "//div[@data-auto='filter']")
    private List<WebElement> filters;

    @FindBy(xpath = "//button[@data-zone-name='sort']")
    private List<WebElement> sortingButtons;

    @FindBy(xpath = "//div[@data-apiary-widget-name='@light/Organic']")
    private List<WebElement> products;

    @FindBy(xpath = "//input[@id='header-search']")
    private WebElement search;

    @FindBy(xpath = "//button[@data-auto='search-button']")
    private WebElement searchButton;

    private WebElement product;

    @Step("apply filter {key} to {value}")
    public CategoryPage applyFilter(String key, String value) {
        waitUntilElementsIsVisible(filters);

        for (WebElement filter : filters) {
            String filterName;

            try {
                filterName = filter.findElement(By.xpath(".//fieldset//legend//h4")).getText();
            } catch (NoSuchElementException e) {
                continue;
            }

            if (!filterName.equals(key)) {
                continue;
            }

            List<WebElement> filterValues = filter.findElements(By.xpath(".//fieldset//label"));

            for (WebElement filterValue : filterValues) {
                String filterValueText = filterValue.findElement(By.xpath(".//span//span/following-sibling::span")).getText();
                if (!filterValueText.equals(value)) {
                    continue;
                }

                waitUntilElementToBeClickable(filterValue);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                filterValue.click();
                waitForProductsUpdate();
                return this;
            }
        }

        return this;
    }

    @Step("apply sorting {type}")
    public CategoryPage applySorting(String type) {
        waitUntilElementsIsVisible(sortingButtons);

        for (WebElement sortingButton : sortingButtons) {
            if (!sortingButton.getText().equals(type)) {
                continue;
            }

            waitUntilElementToBeClickable(sortingButton);
            sortingButton.click();
            waitForProductsUpdate();
            return this;
        }


        return this;
    }

    @Step("log first {amount} products")
    public CategoryPage logProducts(int amount) {
        waitUntilElementsIsVisible(products);
        for (int i = 0; i < amount; i++) {
            WebElement product = products.get(i);
            String productName = getProductName(product);
            String productPrice = getProductPrice(product);
            System.out.println(productName + " - " + productPrice + " руб.");
        }

        return this;
    }

    @Step("remember product number {number}")
    public CategoryPage saveProduct(int number) {
        product = products.get(number - 1);
        return this;
    }

    @Step("enter in the search field remembered product")
    public CategoryPage searchSavedProduct() {

        String prevProductName = getProductName(product);
        String prevProductPrice = getProductPrice(product);


        waitUntilElementIsVisible(search);

        waitUntilElementToBeClickable(search);

        search.click();
        search.clear();
        search.sendKeys(getProductName(product));

        waitUntilElementIsVisible(searchButton);
        waitUntilElementToBeClickable(searchButton);

        searchButton.click();

        waitUntilElementsIsVisible(products);

        WebElement foundedProduct = products.getFirst();

        Allure.step("verify that in the search results first founded product is what was searched", step -> {
            Assertions.assertEquals(getProductName(foundedProduct), prevProductName);
            Assertions.assertEquals(getProductPrice(foundedProduct), prevProductPrice);
        });

        return this;
    }

    private String getProductName(WebElement product) {
        return product.findElement(By.xpath(".//h3")).getText();
    }

    private String getProductPrice(WebElement product) {
        return product.findElement(By.xpath(".//span[@data-auto='snippet-price-current']/span[1]")).getText();
    }

    private void waitForProductsUpdate() {
        WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), Duration.ofSeconds(5), Duration.ofMillis(100));
        String blurBackgroundXPath = "//div[@data-auto='SerpStatic-loader']";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(blurBackgroundXPath)));
        wait.until(driver -> driver.findElements(By.xpath(blurBackgroundXPath)).isEmpty());
        resetDriverWait();
    }
}
