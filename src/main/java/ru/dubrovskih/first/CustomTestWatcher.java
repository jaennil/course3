package ru.dubrovskih.first;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import ru.dubrovskih.first.managers.DriverManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomTestWatcher implements TestWatcher {

    private final DriverManager driverManager = DriverManager.getInstance();

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        takeAndSaveScreenshot(context);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        takeAndSaveScreenshot(context);
    }

    private void takeAndSaveScreenshot(ExtensionContext context) {
        String fileName = generateScreenshotFileName(context);
        byte[] screenshot = takeScreenshot();
        saveScreenshot(fileName, screenshot);
        Allure.getLifecycle().addAttachment("failure screenshot", "image/png", "png", screenshot);
    }

    private byte[] takeScreenshot() {
        WebDriver driver = driverManager.getDriver();
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    private void saveScreenshot(String fileName, byte[] screenshot) {
        Path screenshotsPath;
        try {
            screenshotsPath = createScreenshotsDirectory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Path screenshotPath = screenshotsPath.resolve(fileName);
        File file = new File(screenshotPath.toString());

        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileOutputStream outputStream = new FileOutputStream(screenshotPath.toFile())) {
            outputStream.write(screenshot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path createScreenshotsDirectory() throws IOException {
        String strClassesPath = CustomTestWatcher.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        Path classesPath = Paths.get(strClassesPath);
        Path targetPath = Paths.get(classesPath.getParent().toString());
        return targetPath.resolve("failure-screenshots" + File.separator);
    }

    private String generateScreenshotFileName(ExtensionContext context) {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
        String strDate = dateFormat.format(date);

        String fileName = strDate + " " + context.getDisplayName().replace("/", "|") + ".png";

        return fileName.replace(" ", "_");
    }
}
