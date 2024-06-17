package ru.dubrovskih.course.fourth.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.dubrovskih.course.BasePage;
import ru.dubrovskih.course.fourth.PageManager;

import java.util.List;

public class SearchPage extends BasePage {

    private final String repositoryNameXPath = ".//h3//a//span";
    private final String repositoryLinkXPath = ".//a";
    @FindBy(xpath = "//div[@data-testid='results-list']/div")
    private List<WebElement> repositories;

    @Step("verify first founded repository name is '{name}")
    public SearchPage verifyFirstFoundedRepositoryName(String name) {

        waitUntilElementsIsVisible(repositories);

        WebElement firstRepository = repositories.getFirst();

        String repositoryName = getRepositoryName(firstRepository);

        Assertions.assertEquals(name, repositoryName, "first founded repository name doesn't match provided");

        return this;
    }

    String getRepositoryName(WebElement repository) {
        return repository.findElement(By.xpath(repositoryNameXPath)).getText();
    }

    @Step("click on the repository with name '{name}")
    public RepositoryPage clickOnTheRepository(String name) {

        waitUntilElementsIsVisible(repositories);

        WebElement foundedRepository = null;
        for (WebElement repository : repositories) {
            String repositoryName = getRepositoryName(repository);

            if (repositoryName.equals(name)) {
                foundedRepository = repository;
                break;
            }
        }

        Assertions.assertNotNull(foundedRepository, String.format("repository '%s' not founded", name));

        WebElement repositoryLink = foundedRepository.findElement(By.xpath(repositoryLinkXPath));

        waitUntilElementToBeClickable(repositoryLink);

        repositoryLink.click();

        return PageManager.getInstance().getRepositoryPage();
    }
}
