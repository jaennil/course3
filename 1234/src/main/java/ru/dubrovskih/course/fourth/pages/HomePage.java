package ru.dubrovskih.course.fourth.pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.dubrovskih.course.BasePage;
import ru.dubrovskih.course.fourth.PageManager;

public class HomePage extends BasePage {

	@FindBy(xpath = "//qbsearch-input")
	private WebElement searchInput;

	@FindBy(id = "query-builder-test")
	private WebElement subSearchInput;

	@Step("open link https://github.com")
	public HomePage open() {
		driverManager.getDriver().get("https://github.com");
		return this;
	}

	@Step("click on the search input")
	public SearchPage search(String input) {
		waitUntilElementIsVisible(searchInput);
		waitUntilElementToBeClickable(searchInput);
		searchInput.click();

		waitUntilElementIsVisible(subSearchInput);

		subSearchInput.sendKeys(input);

		subSearchInput.sendKeys(Keys.RETURN);

		return PageManager.getInstance().getSearchPage();
	}

}
