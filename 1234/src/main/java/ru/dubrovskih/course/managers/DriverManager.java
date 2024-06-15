package ru.dubrovskih.course.managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverManager {
    private static DriverManager instance;
    private WebDriver driver;

    private DriverManager() {
    }

    public static DriverManager getInstance() {
        if (instance == null) {
            instance = new DriverManager();
        }

        return instance;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            driver = new FirefoxDriver();
        }

        return driver;
    }

    void quit() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
