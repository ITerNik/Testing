package ru.ifmo.testing.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.ifmo.testing.page.MainPage;
import ru.ifmo.testing.page.SearchPage;

import java.time.Duration;

public class SeleniumTests extends BaseTest {
    public SearchPage searchPage;
    public MainPage mainPage;
    private final WebDriverWait defaultWait = new WebDriverWait(driver, Duration.ofSeconds(5));

    @Override
    protected boolean useSelenide() {
        return false;
    }

    public void waitForVisibility(By by) {
        defaultWait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void acceptCookies() {
        driver.findElement(By.xpath("//button[text()='Соглашаюсь с условиями']")).click();
    }

    public void setUp() {
        super.setUp();
        searchPage = new SearchPage(driver);
        mainPage = new MainPage(driver);
    }
}
