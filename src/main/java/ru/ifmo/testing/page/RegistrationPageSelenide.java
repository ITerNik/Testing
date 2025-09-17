package ru.ifmo.testing.page;

import static com.codeborne.selenide.Condition.*;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ClickOptions;

import static com.codeborne.selenide.Selenide.*;

public class RegistrationPageSelenide {
    private final SelenideElement authenticationBox = $x("//div[@class='fl-authentication-page-block']");
    private final SelenideElement emailField = authenticationBox.$x(".//input[@data-id='qa-ui-input-user-email']");
    private final SelenideElement passwordField = authenticationBox.$x(".//input[@id='user-password']");
    private final SelenideElement registerButton = authenticationBox.$x(".//div[@id='qa-registration-button']");
    private final SelenideElement emailErrorMessage = authenticationBox.$x(".//span[@data-id='qa-valid-error-user-email']");
    private final SelenideElement robotCheckboxIframe = authenticationBox.$x(".//iframe[@data-testid='checkbox-iframe']");
    private final SelenideElement passwordErrorIcon = authenticationBox.$x("//*[contains(@class, 'password-error-icon') or contains(@class, 'error-icon')]");

    public void openPage() {
        open("/account/registration/");
    }

    public SelenideElement getAgreementCheckbox(int index) {
        return authenticationBox.$x(".//label[@for='ui-checkbox-rules.rule_%s']".formatted(index));
    }

    public void clickAgreementCheckBox(int index) {
        SelenideElement label = getAgreementCheckbox(index);
        label.click(ClickOptions.withOffset(-label.getRect().width / 2 + 10, 0));
    }

    public void fillEmail(String email) {
        emailField.clear();
        emailField.setValue(email);
    }

    public void fillPassword(String password) {
        passwordField.clear();
        passwordField.setValue(password);
    }

    public void checkAgreement() {
        clickAgreementCheckBox(1);
        clickAgreementCheckBox(3);
    }

    public void checkRobotVerification() {
        switchTo().frame(robotCheckboxIframe);
        SelenideElement checkBox = $x("//input[@id='js-button']");
        checkBox.click();
        checkBox.shouldHave(attribute("aria-checked", "true"));
        switchTo().defaultContent();
    }

    public void clickRegister() {
        registerButton.click();
    }

    public void register(String email, String password, boolean checkAgreements) {
        fillEmail(email);
        fillPassword(password);
        if (checkAgreements) {
            checkAgreement();
            checkRobotVerification();
        }
        clickRegister();
    }

    public String getEmailErrorText() {
        return emailErrorMessage.getText();
    }

    public void waitForText(String text) {
        $x("//*[contains(text(), '%s')]".formatted(text)).shouldBe(visible);
    }

    public void waitEmailErrorMessageVisible() {
        emailErrorMessage.shouldBe(visible);
    }

    public boolean isPasswordErrorIconVisible() {
        return passwordErrorIcon.exists();
    }

    public String getPasswordErrorText() {
        return passwordErrorIcon.getAttribute("title");
    }
}