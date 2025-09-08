package ru.ifmo.testing.page;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends BasePage {
    public MainPage(WebDriver webDriver){
        super(webDriver);
    }

    @FindBy(xpath = "//nav[1]")
    @Getter
    private WebElement navBar;

    @FindBy(xpath = "//nav//a[@data-id='qa-head-sign-in']")
    @Getter
    private WebElement loginButton;

    public WebElement getLeftNavBarBlock () {
        return navBar.findElement(By.xpath("//div[@data-id='qa-head-ul-main']"));
    }

    public WebElement getRightNavBarBlock () {
        return navBar.findElement(By.xpath("//div[@data-id='qa-head-right-block']"));
    }
}
