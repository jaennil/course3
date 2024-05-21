package ru.dubrovskih.course.second.pages;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.dubrovskih.course.second.BasePage;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class DmamiSchedulePage extends BasePage {

    @FindBy(xpath = "//input[@class='groups']")
    private WebElement groupSearchField;

    @FindBy(xpath = "//div[@class='found-groups row not-print']/*")
    private List<WebElement> foundedGroups;

    @FindBy(xpath = "//div[@class='schedule']")
    private WebElement scheduleDiv;

    @FindBy(xpath = "//div[contains(@class,'schedule-day__title')]")
    private List<WebElement> scheduleDays;

    public DmamiSchedulePage fillGroupSearchField(String group) {
        waitUntilElementToBeClickable(groupSearchField);
        Allure.step(String.format("fill group search field with group '%s'", group), step -> {
            groupSearchField.click();
            groupSearchField.clear();
            groupSearchField.sendKeys(group);

            Allure.step(String.format("verify that the only founded group is '%s'", group), subStep -> {
                verifyFoundedGroup(group);
            });
        });

        return this;
    }

    private void verifyFoundedGroup(String group) {
        waitUntilElementsIsVisible(foundedGroups);
        Assertions.assertEquals(foundedGroups.size(), 1, String.format("founded more than 1 group with name %s", group));
        Assertions.assertEquals(foundedGroups.getFirst().getText(), group, String.format("founded group is not %s", group));
    }

    public DmamiSchedulePage clickOnTheFoundedGroup() {
        Allure.step("click on the founded group", step -> {
            waitUntilElementsIsVisible(foundedGroups);
            WebElement foundedGroup = foundedGroups.getFirst();
            waitUntilElementToBeClickable(foundedGroup);
            foundedGroup.click();

            Allure.step("verify that schedule is displayed", subStep -> {
                waitUntilElementIsVisible(scheduleDiv);
                Assertions.assertTrue(scheduleDiv.isDisplayed(), "schedule is not displayed");
            });

            Allure.step("verify that current day is colored", subStep -> {
                WebElement currentDayDiv = getCurrentDayDiv();

                Assertions.assertNotNull(currentDayDiv, "current day div is null");

                WebElement parentDiv = currentDayDiv.findElement(By.xpath(".."));

                String backgroundColor = parentDiv.getCssValue("background-color");

                Assertions.assertNotEquals(backgroundColor, "", "current day doesnt have background color");
            });

        });
        return this;
    }

    private String getCurrentWeekDay() {
        return LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.of("ru"));
    }

    private WebElement getCurrentDayDiv() {
        waitUntilElementsIsVisible(scheduleDays);

        String currentWeekDay = getCurrentWeekDay();

        for (WebElement dayDiv : scheduleDays) {
            if (dayDiv.getText().toLowerCase().equals(currentWeekDay)) {
                return dayDiv;
            }
        }

        return null;
    }
}
