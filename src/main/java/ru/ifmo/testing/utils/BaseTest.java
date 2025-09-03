package ru.ifmo.testing.utils;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import ru.ifmo.testing.driver.DriverFactory;
import ru.ifmo.testing.page.MainPage;
import ru.ifmo.testing.report.ExtentManager;
import ru.ifmo.testing.report.ExtentTestManager;
import ru.ifmo.testing.report.ReportListener;
import lombok.Getter;

@ExtendWith(ReportListener.class)
public abstract class BaseTest {

    @Getter
    protected static WebDriver driver;
    protected MainPage mainPage;

    @BeforeAll
    public static void setUpExtentReports() {
        ExtentManager.createExtentReports();
    }

    @BeforeEach
    public void preCondition(TestInfo testInfo) {
        ExtentTestManager.startTest(testInfo.getDisplayName(), "");

        driver = new DriverFactory().createInstance();
        driver.manage().window().maximize();
        driver.get(PropertyLoader.returnConfigValue("fl.url.base"));
        mainPage = new MainPage(driver);
    }

    @AfterEach
    public void postCondition(){
        driver.quit();
    }

    @AfterAll
    public static void tearDownExtentReports() {
        ExtentManager.flushReports();
    }
}
