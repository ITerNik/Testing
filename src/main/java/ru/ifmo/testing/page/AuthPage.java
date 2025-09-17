package ru.ifmo.testing.page;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AuthPage extends BasePage {
    public AuthPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Getter
    @FindBy(xpath = "//form")
    private WebElement loginForm;

    @Getter
    @FindBy(xpath = "//div[@class='fl-authentication-page-block']")
    private WebElement authenticationBlock;

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

    public WebElement getRegistrationButton() {
        return getAuthenticationBlock().findElement(By.xpath("//div[@id='qa-registration-button']"));
    }

    public WebElement getUserEmailInput() {
        return getAuthenticationBlock().findElement(By.xpath("//input[@data-id='qa-ui-input-user-email']"));
    }

    public void fillUserEmailInput(String text) {
        getUserEmailInput().sendKeys(text);
    }

    public WebElement getUserPasswordInput() {
        return getAuthenticationBlock().findElement(By.xpath("//input[@id='user-password']"));
    }

    public void fillUserPasswordInput(String text) {
        getUserPasswordInput().sendKeys(text);
    }

    @Getter
    @FindBy(xpath = "//iframe[@data-testid='checkbox-iframe']")
    private WebElement robotVerificationFrame;

    public void checkRobotVerification() {
        driver.switchTo().frame(getRobotVerificationFrame());
        WebElement checkBox = driver.findElement(By.xpath("//input[@id='js-button']"));
        checkBox.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(attributeContains(checkBox, "aria-checked", "true"));
        driver.switchTo().defaultContent();
    }

    public WebElement getRuleLabel(int index) {
        return getAuthenticationBlock().findElement(By.xpath("//label[@for='ui-checkbox-rules.rule_%s']".formatted(index)));
    }

    public void clickRuleCheckBox(int index) {
        WebElement label = getRuleLabel(index);

        Actions actions = new Actions(driver);
        actions.moveToElement(label, -label.getRect().width / 2 + 10, 0).click().perform();
    }

    public WebElement getUserEmailError() {
        return getAuthenticationBlock().findElement(By.xpath("//span[@data-id='qa-valid-error-user-email']"));
    }
}
