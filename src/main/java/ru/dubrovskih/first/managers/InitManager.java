package ru.dubrovskih.first.managers;

public class InitManager {

    private static final DriverManager driverManager = DriverManager.getInstance();

    public static void init() {
        driverManager.getDriver().manage().window().maximize();
    }

    public static void quit() {
        driverManager.quit();
    }
}
