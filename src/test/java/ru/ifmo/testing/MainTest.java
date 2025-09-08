package ru.ifmo.testing;

import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import ru.ifmo.testing.scrapper.CookieUtils;
import ru.ifmo.testing.utils.BaseTest;
import ru.ifmo.testing.utils.PropertyLoader;


import java.net.HttpCookie;
import java.net.URI;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest extends BaseTest {
    private static final Logger LOGGER = LogManager.getLogger();

    @Test
    public void shouldValidateTitle() {
        String title = mainPage.getTitle().trim();;
        assertEquals(title.trim(), "Крупнейшая биржа фриланса с лучшими профессионалами рунета. Фриланс и удаленная работа - FL.ru");
        LOGGER.info("Title found: {}", title);
    }

    @Test
    public void shouldValidateButton() {
        mainPage.clickByText("Разместить заказ");
    }

    @Test
    public void login() throws InterruptedException {
        CookieUtils.addCookiesFromString(getDriver(), PropertyLoader.returnConfigValue("cookie.value"));
        getDriver().navigate().refresh();

        Thread.sleep(3000);
//        mainPage.getLoginButton().click();
//        authPage.getCookieAcceptButton().click();
//        authPage.fillUsernameInput("Ternik2705@list.ru");
//        authPage.fillPasswordInput("#2Wsxcft7");
//        authPage.getRobotVerification().click();
//        authPage.submitForm();
    }

    @Test
    public void searchBaeldung() throws Exception {
        open("https://duckduckgo.com/");

        SelenideElement searchbox = $(By.id("searchbox_input"));
        searchbox.click();
        searchbox.sendKeys("Baeldung");
        searchbox.pressEnter();

        SelenideElement firstResult = $(By.id("r1-0"));
        firstResult.shouldHave(text("Baeldung"));
    }
}
