package ru.ifmo.testing.utils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import ru.ifmo.testing.driver.DriverFactory;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public abstract class BaseTest {
    private final String browser = System.getProperty("browser", "chrome");
    private final String baseUrl = "https://www.fl.ru";
    protected WebDriver driver;

    public void setUp() {
        setupBrowser();
        if (useSelenide()) {
            setupSelenide();
        }
    }

    @AfterEach
    public void tearDown() {
        if (useSelenide()) {
            closeWebDriver();
        } else if (driver != null) {
            driver.quit();
        }
    }

    private void setupBrowser() {
        driver = new DriverFactory().createInstance();
        driver.manage().window().maximize();
        if (useSelenide()) {
            Configuration.browser = browser;
        }
    }

    private void setupSelenide() {
        WebDriverRunner.setWebDriver(driver);

        Configuration.baseUrl = baseUrl;
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
        Configuration.headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
    }

    protected String getBaseUrl() {
        return baseUrl;
    }

    protected abstract boolean useSelenide();
    public abstract void acceptCookies();
}