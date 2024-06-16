package ru.dubrovskih.course.fourth.pages;

import io.qameta.allure.Allure;
import ru.dubrovskih.course.BasePage;

public class HomePage extends BasePage {

	public HomePage open() {
		Allure.step("open link https://github.com", step -> {
			driverManager.getDriver().get("https://github.com");
		});
		return this;
	}

}
