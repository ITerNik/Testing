package ru.ifmo.testing.utils;

import lombok.Getter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import ru.ifmo.testing.driver.DriverFactory;
import ru.ifmo.testing.page.AuthPage;
import ru.ifmo.testing.page.MainPage;
import ru.ifmo.testing.report.AllureSelenideExtention;
import ru.ifmo.testing.report.AllureSeleniumExtension;

@Getter
@ExtendWith(AllureSeleniumExtension.class)
@ExtendWith(AllureSelenideExtention.class)
public class BaseTest {

    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    protected MainPage mainPage;
    protected AuthPage authPage;

    public static WebDriver getDriver() {
        return driverThread.get();
    }

    @BeforeEach
    public void preCondition(TestInfo testInfo) {
        WebDriver driver = new DriverFactory().createInstance();
        driver.manage().window().maximize();
        driver.get(PropertyLoader.returnConfigValue("fl.url.base"));
        driverThread.set(driver);
        mainPage = new MainPage(driver);
        authPage = new AuthPage(driver);
    }

    @AfterEach
    public void postCondition() {
        WebDriver driver = driverThread.get();
        if (driver != null) {
            driver.quit();
            driverThread.remove();
        }
    }
}
