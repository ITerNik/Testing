package ru.ifmo.testing.driver;

import org.openqa.selenium.firefox.FirefoxOptions;
import ru.ifmo.testing.scrapper.BrowserScrapper;

public class FirefoxOptionsFactory {

    FirefoxOptions build() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        options.setAcceptInsecureCerts(true);

        BrowserScrapper.proxify(options);

        return options;
    }
}
