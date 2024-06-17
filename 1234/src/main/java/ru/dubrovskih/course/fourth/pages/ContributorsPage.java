package ru.dubrovskih.course.fourth.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.FindBy;
import ru.dubrovskih.course.BasePage;

import java.util.List;

public class ContributorsPage extends BasePage {

    @FindBy(xpath = "//div[@id = 'contributors']//li")
    private List<WebElement> contributors;

    private final String contributorUsernameXPath = ".//h3//a/following::span/following-sibling::a";
    private final String contributorCommitsXPath = ".//h3//span//a";

    public ContributorsPage verifyContributorsPageOpened() {
        wait.until(ExpectedConditions.titleContains("Contributors"));

        String title = driverManager.getDriver().getTitle();

        Assertions.assertTrue(title.contains("Contributors"));

        return this;
    }

    public ContributorsPage logContributors(int amount) {
        waitUntilElementsIsVisible(contributors);

        System.out.println();
        for (int i = 0; i < amount; i++) {
            WebElement contributor = contributors.get(i);

            String contributorUsername = contributor.findElement(By.xpath(contributorUsernameXPath)).getText();

            String commits = contributor.findElement(By.xpath(contributorCommitsXPath)).getText();

            System.out.printf("%s. %s - %s%n", i+1, contributorUsername, commits);
        }
        System.out.println();

        return this;
    }
}
