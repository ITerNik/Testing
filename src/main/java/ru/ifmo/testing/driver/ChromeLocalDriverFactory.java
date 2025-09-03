package ru.ifmo.testing.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

class ChromeLocalDriverFactory{

    private final ChromeOptions chromeOptions;

    ChromeLocalDriverFactory(ChromeOptions chromeOptions) {
        this.chromeOptions = chromeOptions;
    }

    WebDriver driver() {
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver(chromeOptions);

        ((JavascriptExecutor) driver).executeScript(
                "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})"
        );

        return driver;
    }


}
