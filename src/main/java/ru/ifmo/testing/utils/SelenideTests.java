package ru.ifmo.testing.utils;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Keys;
import ru.ifmo.testing.page.FreelancersPageSelenide;
import ru.ifmo.testing.page.RegistrationPageSelenide;
import ru.ifmo.testing.report.AllureSelenideExtention;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;

@ExtendWith(AllureSelenideExtention.class)
public class SelenideTests extends BaseTest {
    protected RegistrationPageSelenide regPage;
    protected FreelancersPageSelenide flPage;

    @Override
    protected boolean useSelenide() {
        return true;
    }

    public void acceptCookies() {
        $x("//button[text()='Соглашаюсь с условиями']").click();
    }

    public void pressKeyTab() {
        actions().sendKeys(Keys.TAB).perform();
    }

    public void waitForUrl(String url) {
        Wait().until(urlContains(url));
    }

    public void waitForText(String text) {
        $x("//*[contains(text(), '%s')]".formatted(text)).shouldBe(visible);
    }

    @BeforeEach
    public void setUp() {
        super.setUp();
        regPage = new RegistrationPageSelenide();
        flPage = new FreelancersPageSelenide();
    }
}
