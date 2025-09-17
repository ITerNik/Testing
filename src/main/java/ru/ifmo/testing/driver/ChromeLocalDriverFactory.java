package ru.ifmo.testing.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.ifmo.testing.utils.PropertyLoader;

class ChromeLocalDriverFactory{

    private final ChromeOptions chromeOptions;

    ChromeLocalDriverFactory(ChromeOptions chromeOptions) {
        this.chromeOptions = chromeOptions;
    }

    WebDriver driver() {
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver(chromeOptions);

        String login = PropertyLoader.returnConfigValue("proxy.login");
        String password = PropertyLoader.returnConfigValue("proxy.password");
        driver.register(UsernameAndPassword.of(login, password));

        return driver;
    }


}
