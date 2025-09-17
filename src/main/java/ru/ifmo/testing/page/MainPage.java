package ru.ifmo.testing.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage extends BasePage {
    private final WebDriverWait wait;

    @FindBy(xpath = "//div[@id='page-up-button']")
    private WebElement scrollToTopButton;

    @FindBy(xpath = "//a[@title='Разместить заказ']")
    private WebElement placeOrderButton;

    @FindBy(xpath = "//a[@data-id='qa-head-buy-pro']")
    private WebElement buyResponsesButton;


    @FindBy(xpath = "//a[contains(@class, 'fl-card-freelancer')][1]")
    private WebElement firstFreelancerTile;

    @FindBy(xpath = "//a[@title='Меню профиля']")
    private WebElement profileIcon;

    @FindBy(xpath = "//a[@id='navbarLeftDropdown']")
    private WebElement moreButton;

    @FindBy(linkText = "О проекте")
    private WebElement aboutProjectLink;

    @FindBy(linkText = "Правила")
    private WebElement rulesLink;

    @FindBy(linkText = "Безопасность")
    private WebElement securityLink;

    @FindBy(linkText = "Помощь")
    private WebElement helpLink;

    @FindBy(xpath = "//a[contains(@href,'vk.com')]")
    private WebElement vkLink;

    @FindBy(xpath = "//a[contains(@href,'sk.ru')]")
    private WebElement skLink;

    @FindBy(xpath = "//a[contains(@href,'dizkon.ru')]")
    private WebElement dizkonLink;

    @FindBy(xpath = "//nav")
    private WebElement modal;

    @FindBy(xpath = "//nav")
    private WebElement okButton;

    public MainPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("https://www.fl.ru/");
    }

    public void clickAnywhere() {
        driver.findElement(By.xpath("//body")).click();
    }

    public void clickMoreButton() {
        moreButton.click();
    }

    public void clickProfileIcon() {
        profileIcon.click();
    }

    public void scrollDown() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public void scrollUp() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    public void clickScrollToTop() {
        scrollToTopButton.click();
    }

    public boolean isScrollToTopButtonVisible() {
        try {
            return scrollToTopButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickPlaceOrder() {
        placeOrderButton.click();
    }

    public void clickBuyResponses() {
        buyResponsesButton.click();
    }

    public void clickFirstFreelancerTile() {
        firstFreelancerTile.click();
    }

    // Footer methods
    public void clickAboutProject() {
        aboutProjectLink.click();
    }

    public void clickRules() {
        rulesLink.click();
    }

    public void clickSecurity() {
        securityLink.click();
    }

    public void clickHelp() {
        helpLink.click();
    }

    public void clickVkLink() {
        vkLink.click();
    }

    public void clickSkLink() {
        skLink.click();
    }

    public void clickDizkonLink() {
        dizkonLink.click();
    }

    public void clickOkInModal() {
        okButton.click();
    }

    public boolean isModalDisplayed() {
        try {
            return modal.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}