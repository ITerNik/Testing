package ru.ifmo.testing.report;

import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import ru.ifmo.testing.driver.DriverFactory;
import ru.ifmo.testing.utils.BaseTest;

public class AllureSeleniumExtension implements AfterTestExecutionCallback {
    @Override
    public void afterTestExecution(ExtensionContext context) {
        WebDriver driver = new DriverFactory().createInstance();
        String testName = context.getDisplayName();

        takeScreenshot(testName, driver);
        attachPageSource(testName, driver);
    }

    @Attachment(value = "{name} - Screenshot", type = "image/png")
    private byte[] takeScreenshot(String name, WebDriver driver) {
        if (driver == null) return new byte[0];
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "{name} - Page Source", type = "text/html")
    private String attachPageSource(String name, WebDriver driver) {
        if (driver == null) return "";
        return driver.getPageSource();
    }
}