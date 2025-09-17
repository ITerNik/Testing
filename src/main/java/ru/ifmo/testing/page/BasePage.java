package ru.ifmo.testing.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {
    protected final WebDriver driver;

    public BasePage(WebDriver webDriver){
        this.driver = webDriver;
        PageFactory.initElements(driver, this);
    }
}
