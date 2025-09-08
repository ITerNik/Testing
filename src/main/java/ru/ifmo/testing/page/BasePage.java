package ru.ifmo.testing.page;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.DataInput;
import java.time.Duration;

public abstract class BasePage {
    protected final WebDriver driver;
    private static final Duration BASE_TIMEOUT = Duration.ofSeconds(5);
    protected Logger LOGGER = LogManager.getLogger();

    public BasePage(WebDriver webDriver){
        this.driver = webDriver;
        PageFactory.initElements(driver, this);
    }

    @Getter
    @FindBy(xpath = "//button[text()='Соглашаюсь с условиями']")
    private WebElement cookieAcceptButton;

    public WebElement getByText(String text) {
        return driver.findElement(By.xpath("//*[contains(text(),'%s')][1]".formatted(text)));
    }

    public void waitForTimeout(int ms) throws InterruptedException {
        Thread.sleep(ms);
    }

    public void shouldBeVisibleByText(String text) {
        waitVisibilityOf(getByText(text));
        LOGGER.info("Element with text '{}' is visible", text);
    }

    public void clickByText(String text) {
        getByText(text).click();
        LOGGER.info("Clicked on element with text '{}'", text);
    }

    public void waitVisibilityOf(WebElement webElement) {
        WebDriverWait explicitWaitByElement = new WebDriverWait(driver, BASE_TIMEOUT);
        explicitWaitByElement.until(ExpectedConditions.visibilityOf(webElement));
    }

    public String getTitle() {
        return driver.getTitle();
    }
}
