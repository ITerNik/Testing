package ru.ifmo.testing.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxLocalDriverFactory {

    private final FirefoxOptions firefoxOptions;

    FirefoxLocalDriverFactory(FirefoxOptions firefoxOptions) {
        this.firefoxOptions = firefoxOptions;
    }

    WebDriver driver() {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver(firefoxOptions);
    }
}