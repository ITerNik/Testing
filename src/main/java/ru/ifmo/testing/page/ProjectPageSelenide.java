package ru.ifmo.testing.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class ProjectPageSelenide {

    private final SelenideElement respondButton = $x("//button[contains(text(), 'Откликнуться')] | //a[contains(text(), 'Откликнуться')]");
    private final SelenideElement responseText = $x("//textarea[@name='response' or contains(@placeholder, 'отклик')]");
    private final SelenideElement submitResponseButton = $x("//button[@type='submit' and contains(text(), 'Отправить')]");
    private final SelenideElement responseTitle = $x("//h2[contains(text(), 'Ваш отклик')] | //h3[contains(text(), 'Ваш отклик')]");
    private final SelenideElement errorMessage = $x("//*[contains(@class, 'error-message') or contains(@class, 'alert-danger')]");
    private final SelenideElement verificationModal = $x("//div[contains(@class, 'modal') and contains(., 'Выберите способ верификации')]");
    private final SelenideElement registrationModal = $x("//div[contains(@class, 'modal') and contains(., 'Регистрация')] | //form[contains(@class, 'registration-form')]");
    private final SelenideElement shortTextError = $x("//*[contains(text(), 'введите хотя бы 5 символов')]");

    public void openProject(String projectUrl) {
        open(projectUrl);
    }

    public void openSpecificProject() {
        open("/projects/5454855/razrabotka-dizayna-kostyuma-dlya-igryi-oflayn-.html");
    }

    public void openTelegramBotProject() {
        open("/projects/5455070/ischem-razrabotchika-dlya-bota-v-telegram-.html");
    }

    public void clickRespond() {
        respondButton.click();
    }

    public void fillResponseText(String text) {
        responseText.setValue(text);
    }

    public void submitResponse() {
        submitResponseButton.click();
    }

    public void respondToProject(String responseText) {
        clickRespond();
        fillResponseText(responseText);
        submitResponse();
    }

    public void respondEmpty() {
        clickRespond();
        submitResponse();
    }

    public boolean isRespondButtonVisible() {
        return respondButton.exists();
    }

    public boolean isResponseTitleVisible() {
        return responseTitle.isDisplayed();
    }

    public String getResponseTitleText() {
        return responseTitle.getText();
    }

    public boolean isErrorMessageVisible() {
        return errorMessage.isDisplayed();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public boolean isVerificationModalVisible() {
        return verificationModal.isDisplayed();
    }

    public boolean isRegistrationModalVisible() {
        return registrationModal.isDisplayed();
    }
}