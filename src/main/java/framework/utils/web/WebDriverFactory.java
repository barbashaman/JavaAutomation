package framework.utils.web;

import framework.utils.config.PropertiesUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Creates WebDriver instances from configuration (no global singleton — safe for parallel tests when each
 * test uses its own driver and {@link framework.context.WebSession}).
 */
public final class WebDriverFactory {

    private WebDriverFactory() {
    }

    public static WebDriver createDriver(PropertiesUtils config) {
        String browser = config.getProperty("browser", "chrome").trim().toLowerCase();
        switch (browser) {
            case "firefox":
                return createFirefox(config);
            case "edge":
                return createEdge(config);
            case "chrome":
            default:
                return createChrome(config);
        }
    }

    private static WebDriver createChrome(PropertiesUtils config) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        applyChromiumOptions(options, config);
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefox(PropertiesUtils config) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        if (config.getBooleanProperty("browser.headless", false)) {
            options.addArguments("-headless");
        }
        return new FirefoxDriver(options);
    }

    private static WebDriver createEdge(PropertiesUtils config) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        applyChromiumOptions(options, config);
        return new EdgeDriver(options);
    }

    private static <T extends ChromiumOptions<?>> void applyChromiumOptions(T options, PropertiesUtils config) {
        options.addArguments("--start-maximized");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.setAcceptInsecureCerts(config.getBooleanProperty("browser.acceptInsecureCerts", true));
        if (config.getBooleanProperty("browser.headless", false)) {
            options.addArguments("--headless=new");
        }
    }
}
