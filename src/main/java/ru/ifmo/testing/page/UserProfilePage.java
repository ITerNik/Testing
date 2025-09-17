package ru.ifmo.testing.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UserProfilePage {

    private final WebDriver driver;

    @FindBy(xpath = "//h1 | //*[contains(@class, 'user-name')]")
    private WebElement userNameHeader;

    @FindBy(xpath = "//button[contains(text(), 'Добавить в избранные')] | //a[contains(text(), 'Добавить в избранные')]")
    private WebElement addToFavoritesButton;

    @FindBy(xpath = "//button[contains(text(), 'У вас в избранных')] | //a[contains(text(), 'У вас в избранных')]")
    private WebElement inFavoritesButton;

    @FindBy(xpath = "//button[contains(text(), 'Оставить заметку')] | //a[contains(text(), 'Оставить заметку')]")
    private WebElement addNoteButton;

    @FindBy(xpath = "//textarea[contains(@name, 'note')] | //textarea[contains(@class, 'note-textarea')]")
    private WebElement noteTextarea;

    @FindBy(xpath = "//button[contains(text(), 'Сохранить')] | //*[contains(@class, 'save-note-btn')]")
    private WebElement saveNoteButton;

    @FindBy(xpath = "//*[contains(@class, 'note-edit-icon')] | //*[contains(@class, 'edit-note')]")
    private WebElement editNoteIcon;

    @FindBy(xpath = "//button[contains(text(), 'Показать контакты')] | //a[contains(text(), 'Показать контакты')]")
    private WebElement showContactsButton;

    @FindBy(xpath = "//a[contains(@href, 't.me')] | //*[contains(@class, 'telegram-contact')]")
    private WebElement telegramContact;

    @FindBy(xpath = "//*[contains(@class, 'profile-menu')]//a | //*[contains(@class, 'user-menu')]")
    private WebElement profileIcon;

    @FindBy(xpath = "//a[contains(text(), 'Избранные')] | //*[contains(@class, 'favorites-link')]")
    private WebElement favoritesLink;

    public UserProfilePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void openUserProfile(String username) {
        driver.get("https://www.fl.ru/users/" + username + "/");
    }

    public void openBurraProfile() {
        driver.get("https://www.fl.ru/users/burra/");
    }

    public String getUserName() {
        return userNameHeader.getText();
    }

    public boolean isUserNameDisplayed() {
        try {
            return userNameHeader.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickAddToFavorites() {
        addToFavoritesButton.click();
    }

    public void clickRemoveFromFavorites() {
        inFavoritesButton.click();
    }

    public boolean isInFavoritesButtonDisplayed() {
        try {
            return inFavoritesButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getAddToFavoritesButtonText() {
        return addToFavoritesButton.getText();
    }

    public void clickAddNote() {
        addNoteButton.click();
    }

    public void fillNoteText(String text) {
        noteTextarea.clear();
        noteTextarea.sendKeys(text);
    }

    public void clickSaveNote() {
        saveNoteButton.click();
    }

    public void addNote(String noteText) {
        clickAddNote();
        fillNoteText(noteText);
        clickSaveNote();
    }

    public void clickEditNote() {
        editNoteIcon.click();
    }

    public void clearNote() {
        clickEditNote();
        noteTextarea.clear();
        clickSaveNote();
    }

    public void clickShowContacts() {
        showContactsButton.click();
    }

    public void clickTelegramContact() {
        telegramContact.click();
    }

    public String getTelegramContactText() {
        return telegramContact.getText();
    }

    public boolean isTelegramContactDisplayed() {
        try {
            return telegramContact.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickProfileIcon() {
        profileIcon.click();
    }

    public void clickFavorites() {
        favoritesLink.click();
    }

    public void navigateToFavorites() {
        clickProfileIcon();
        clickFavorites();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}