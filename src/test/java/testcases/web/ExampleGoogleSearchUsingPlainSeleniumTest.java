package testcases.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static java.lang.Thread.sleep;

public class ExampleGoogleSearchUsingPlainSeleniumTest {

    private WebDriver driver;
    private WebDriverWait wait;


    @Before
    public void setUp(){
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // Maximize the window
        options.addArguments("--disable-extensions"); // Disable extensions (if needed)
        options.addArguments("--disable-popup-blocking"); // Disable popup blocking (if needed)
        options.setAcceptInsecureCerts(true); // Accept insecure certificates (if needed)
        options.setCapability("acceptInsecureCerts", true);

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Initialize WebDriverWait with a timeout of 10 seconds

    }

    @Test
    public void validateGoogleSearchForCheeseTest() {
        driver.get("https://www.google.com");
        WebElement acceptAllCookies = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button/div[contains(text(),'Accept all')]")));
        acceptAllCookies.click();

        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
        searchBox.sendKeys("cheese");
        searchBox.sendKeys(Keys.RETURN);

        WebElement searchResults = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search")));
        boolean isCheeseIncluded = searchResults.getText().toLowerCase().contains("cheese");

        Assert.assertTrue("Cheese is not included in the search results.", isCheeseIncluded);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
