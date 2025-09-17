package ru.ifmo.testing.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    private final String browser = System.getProperty("browser", "chrome");

    public WebDriver createInstance() {
        return switch (browser.toLowerCase()) {
            case "firefox" -> {
                FirefoxOptions firefoxOptions = new FirefoxOptionsFactory().build();
                yield new FirefoxLocalDriverFactory(firefoxOptions).driver();
            }
            default -> {
                ChromeOptions chromeOptions = new ChromeOptionsFactory().build();
                yield new ChromeLocalDriverFactory(chromeOptions).driver();
            }
        };
    }
}

