package framework.utils.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import framework.utils.config.PropertiesUtils;

import java.time.Duration;

public class WebDriverCommon {

    private WebDriver driver;
    private static WebDriverWait wait;

    public WebDriverCommon(WebDriver driver){

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(new PropertiesUtils().getIntegerProperty("wait")));
    }

    public void open(String URL) {
        this.driver.get(URL);
    }

    public static WebElement waitForElementBy(By by){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}
