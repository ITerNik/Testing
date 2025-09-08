package ru.ifmo.testing.page;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AuthPage extends BasePage {
    public AuthPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Getter
    @FindBy(xpath = "//form")
    private WebElement loginForm;

    public WebElement getUsernameInput() {
        return getLoginForm().findElement(By.xpath("//input[@name='username']"));
    }

    public void fillUsernameInput(String text) {
        getUsernameInput().sendKeys(text);
    }

    public WebElement getPasswordInput() {
        return getLoginForm().findElement(By.xpath("//input[@name='password']"));
    }

    public void fillPasswordInput(String text) {
        getPasswordInput().sendKeys(text);
    }

    public WebElement getSubmitButton() {
        return getLoginForm().findElement(By.xpath("//button[@id='submit-button']"));
    }

    public void submitForm() {
        getSubmitButton().click();
    }

    public WebElement getRobotVerification() {
        return getLoginForm().findElement(By.xpath("//div[@data-testid='smartCaptcha-container']"));
    }
}
