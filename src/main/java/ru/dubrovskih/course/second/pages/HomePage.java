package ru.dubrovskih.course.second.pages;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.dubrovskih.course.BasePage;

import java.util.List;


public class HomePage extends BasePage {
    @FindBy(xpath = "//div[@class= 'navigation-menu__left-nav']//a[@class='user-nav__item-link']")
    private List<WebElement> leftNavigationMenu;

    public HomePage open() {
        Allure.step("open link https://mospolytech.ru", step -> {
            driverManager.getDriver().get("https://mospolytech.ru");
        });
        return this;
    }

    public MpuSchedulePage openSideMenuSection(LeftNavigationMenuSection section) {
        Allure.step(String.format("open left navigation menu item '%s'", section.getName()), step -> {
            waitUntilElementsIsVisible(leftNavigationMenu);
            WebElement menuItem = getLeftNavigationMenuItem(section);
            Assertions.assertNotNull(menuItem, String.format("menu item '%s' is not found", section.getName()));
            menuItem.click();
        });
        return pageManager.getMpuSchedulePage();
    }

    private WebElement getLeftNavigationMenuItem(LeftNavigationMenuSection section) {
        for (WebElement menuItem : leftNavigationMenu) {
            if (menuItem.getAttribute("title").equals(section.getName())) {
                return menuItem;
            }
        }
        return null;
    }

    public enum LeftNavigationMenuSection {
        SEARCH("Поиск"), PERSONAL_ACCOUNT("Личный кабинет"), SCHEDULE("Расписание");

        private final String name;

        LeftNavigationMenuSection(String name) {
            this.name = name;
        }

        private String getName() {
            return name;
        }
    }
}