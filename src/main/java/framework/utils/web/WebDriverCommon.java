package framework.utils.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import framework.utils.config.PropertiesUtils;

import java.time.Duration;

/**
 * Web navigation and waits scoped to a single {@link WebDriver} instance.
 */
public class WebDriverCommon {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public WebDriverCommon(WebDriver driver, PropertiesUtils config) {
        this.driver = driver;
        int seconds = config.getIntegerProperty("wait", 10);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Math.max(1, seconds)));
    }

    public WebDriverCommon(WebDriver driver, int waitSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Math.max(1, waitSeconds)));
    }

    public void open(String url) {
        driver.get(url);
    }

    public WebElement waitForElementVisible(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public WebDriver getDriver() {
        return driver;
    }
}
