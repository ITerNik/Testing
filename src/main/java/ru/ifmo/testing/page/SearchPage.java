package ru.ifmo.testing.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchPage extends BasePage {
    @FindBy(xpath = "//input[@id='search-request']")
    private WebElement searchField;

    @FindBy(xpath = "//a[@id='search-button']")
    private WebElement searchButton;

    @FindBy(xpath = "//div[@id='search-lenta']//div[@class='cf-user-in'][1]")
    private WebElement firstResult;

    public SearchPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void open() {
        driver.get("https://www.fl.ru/search/");
    }

    public void enterSearchQuery(String query) {
        searchField.clear();
        searchField.sendKeys(query);
    }

    public void clickSearch() {
        searchButton.click();
    }

    public void search(String query) {
        enterSearchQuery(query);
        clickSearch();
    }

    public void searchEmpty() {
        clickSearch();
    }

    public String getFirstResultText() {
        return firstResult.getText();
    }

    public String getSuggestionText() {
        return searchField.getAttribute("placeholder");
    }

    public String getSearchText() {
        return searchField.getAttribute("value");
    }
}