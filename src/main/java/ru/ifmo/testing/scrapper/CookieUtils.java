package ru.ifmo.testing.scrapper;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import java.net.URI;

public class CookieUtils {
    public static void addCookiesFromString(WebDriver driver, String cookieString) {
        String currentUrl = driver.getCurrentUrl();
        String domain = URI.create(currentUrl).getHost();

        String[] cookies = cookieString.split(";");
        for (String cookie : cookies) {
            String[] pair = cookie.trim().split("=", 2);
            if (pair.length == 2) {
                String name = pair[0].trim();
                String value = pair[1].trim();
                Cookie seleniumCookie = new Cookie.Builder(name, value)
                        .domain(domain)
                        .path("/")
                        .build();
                driver.manage().addCookie(seleniumCookie);
            }
        }
    }
}

