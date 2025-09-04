package ru.ifmo.testing.report;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Attachment;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class AllureSelenideExtention implements AfterTestExecutionCallback {

    public AllureSelenideExtention() {
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true)
        );
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        attachScreenshot("Last screenshot");
        attachPageSource();
    }

    @Attachment(value = "{attachName}", type = "image/png")
    public byte[] attachScreenshot(String attachName) {
        try {
            return ((TakesScreenshot) Selenide.webdriver().driver().getWebDriver())
                    .getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            return new byte[0];
        }
    }

    @Attachment(value = "Page source", type = "text/html")
    public String attachPageSource() {
        try {
            return Selenide.webdriver().driver().source();
        } catch (Exception e) {
            return "";
        }
    }
}
