package ru.dubrovskih.course.third.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.dubrovskih.course.second.BasePage;

import java.util.List;


public class CategoryPage extends BasePage {

    @FindBy(xpath = "//div[@data-auto='filter']")
    private List<WebElement> filters;

    @FindBy(xpath = "//button[@data-zone-name='sort']")
    private List<WebElement> sortingButtons;


    public CategoryPage applyFilter(String key, String value) {
        waitUntilElementsIsVisible(filters);

        for (WebElement filter : filters) {
            String filterName;
            try {
                filterName = filter.findElement(By.xpath(".//fieldset//legend//h4")).getText();
            } catch (NoSuchElementException e) {
                System.out.println("[WARNING] no filter name found for some filter");
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
                filterValue.click();
                return this;
            }
        }

        return this;
    }

    public CategoryPage applySorting(String type) {
        waitUntilElementsIsVisible(sortingButtons);

        for (WebElement sortingButton : sortingButtons) {
            if (!sortingButton.getText().equals(type)) {
                continue;
            }

            waitUntilElementToBeClickable(sortingButton);
            sortingButton.click();
            return this;
        }

        return this;
    }
}