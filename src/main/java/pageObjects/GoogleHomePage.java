package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static utils.web.WebDriverCommon.waitForElementBy;

public class GoogleHomePage {

    public WebElement getAcceptAllCookiesButton() {
        return waitForElementBy(By.xpath("//button/div[contains(text(),'Accept all')]"));
    }

    public WebElement getSearchBox() {
        return waitForElementBy(By.name("q"));
    }

    public WebElement getSearchResults() {
        return waitForElementBy(By.id("search"));
    }
}
