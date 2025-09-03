package ru.ifmo.testing.report;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.ifmo.testing.utils.BaseTest;

import java.util.Optional;

public class ReportListener implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    @Override
    public void beforeTestExecution(ExtensionContext context) {
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        reportPrint(context);
    }

    private void reportPrint(ExtensionContext context) {
        ExtentTest test = ExtentTestManager.getTest();

        Optional<Throwable> exception = context.getExecutionException();

        if (exception.isPresent()) {
            test.fail("Test Failed: " + exception.get().getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot()).build());
        } else {
            test.pass("Test Passed", MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot()).build());
        }
    }

    private String takeScreenshot() {
        return ((TakesScreenshot) BaseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
    }
}
