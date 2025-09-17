package ru.ifmo.testing.driver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import ru.ifmo.testing.scrapper.BrowserScrapper;

import java.util.List;
import java.util.logging.Level;

class ChromeOptionsFactory {

    ChromeOptions build(){

        ChromeOptions optionsChrome = new ChromeOptions();
        optionsChrome.addArguments("start-maximized");
        optionsChrome.addArguments("--ignore-certificate-errors");
        optionsChrome.addArguments("--disable-web-security");
        optionsChrome.addArguments("--allow-insecure-localhost");
        optionsChrome.addArguments("--disable-site-isolation-trials");
        optionsChrome.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36");

        BrowserScrapper.proxify(optionsChrome);

        optionsChrome.setExperimentalOption("excludeSwitches",
                List.of("enable-automation"));
        optionsChrome.setExperimentalOption("useAutomationExtension", false);

        optionsChrome.addArguments("--disable-blink-features=AutomationControlled");
//        optionsChrome.addArguments("--headless=new");
        optionsChrome.setAcceptInsecureCerts(true);

        return optionsChrome;
    }
}
