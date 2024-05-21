package ru.dubrovskih.course.third.pages;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.dubrovskih.course.BasePage;

import java.time.Duration;
import java.util.List;


public class HomePage extends BasePage {

    @FindBy(xpath = "//div[@data-zone-name='catalog']")
    private WebElement catalogButton;

    @FindBy(xpath = "//li[@data-zone-name='category-link']")
    private List<WebElement> leftCatalogLinks;

    @FindBy(xpath = "//div[@data-apiary-widget-name='@MarketNode/NavigationTree']//div[@data-auto='category']")
    private List<WebElement> navigationTreeCategories;

    private WebElement selectedCategory;

    public HomePage open() {
        Allure.step("open link https://market.yandex.ru", step -> {
            driverManager.getDriver().get("https://market.yandex.ru");
            Allure.step("verify Yandex Market main page opened", subStep -> {
                verifyYandexMarketMainPageOpened();
            });
        });
        return this;
    }

    private void verifyYandexMarketMainPageOpened() {
        String title = "Интернет-магазин Яндекс Маркет — покупки с быстрой доставкой";
        WebDriver driver = driverManager.getDriver();

        // to solve captcha
        wait = new WebDriverWait(driver, Duration.ofSeconds(60), Duration.ofSeconds(1));
        wait.until(ExpectedConditions.titleIs(title));

        Assertions.assertEquals(title, driver.getTitle(), "Yandex Market main page is not opened");
        resetDriverWait();
    }

    public HomePage clickCatalogButton() {
        Allure.step("choose tablet category", step -> {
            waitUntilElementIsVisible(catalogButton);
            waitUntilElementToBeClickable(catalogButton);
            catalogButton.click();
        });

        return this;
    }

    public HomePage hoverLeftCatalogItem(String category) {
        waitUntilElementsIsVisible(leftCatalogLinks);
        WebElement categoryElement = null;
        for (WebElement element : leftCatalogLinks) {
            if (element.findElement(By.tagName("span")).getText().equals(category)) {
                categoryElement = element;
                break;
            }
        }

        Assertions.assertNotNull(categoryElement, String.format("category '%s' is not found", category));

        Actions actions = new Actions(driverManager.getDriver());

        actions.moveToElement(categoryElement).perform();

        return this;
    }

    public HomePage findCategory(String category) {
        waitUntilElementsIsVisible(navigationTreeCategories);
        for (WebElement element : navigationTreeCategories) {
            WebElement heading = element.findElement(By.xpath(".//div[@role='heading']"));
            String headingText = heading.findElement(By.tagName("a")).getText();
            if (headingText.equals(category)) {
                selectedCategory = element;
                break;
            }
        }

        return this;
    }

    public HomePage clickSubcategory(String subcategory) {
        for (WebElement item : selectedCategory.findElements(By.xpath(".//li//a"))) {
            if (item.getText().equals(subcategory)) {
                waitUntilElementIsVisible(item);
                item.click();
                break;
            }
        }

        verifySubcategoryOpened(subcategory);
        return this;
    }

    private void verifySubcategoryOpened(String subcategory) {
        String title = driverManager.getDriver().getTitle();
        Assertions.assertTrue(title.toLowerCase().contains(subcategory.toLowerCase()));
    }

}