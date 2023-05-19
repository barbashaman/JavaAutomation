package utils.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.config.PropertiesUtils;

public class WebDriverFactory {
    private static WebDriver driver;

    private WebDriverFactory() {
        // Private constructor to prevent instantiation
    }

    public static WebDriver getWebDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized"); // Maximize the window
            options.addArguments("--disable-extensions"); // Disable extensions (if needed)
            options.addArguments("--disable-popup-blocking"); // Disable popup blocking (if needed)
            options.setAcceptInsecureCerts(true); // Accept insecure certificates (if needed)
            options.setCapability("acceptInsecureCerts", true);

            driver = new ChromeDriver(options);
        }
        return driver;
    }

}
