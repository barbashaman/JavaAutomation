package framework.context;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Thread-local web driver binding so page objects stay agnostic of how the driver was created.
 * Call {@link #bind(WebDriver, int)} in test setup and {@link #unbind()} in teardown.
 */
public final class WebSession {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> WAIT = new ThreadLocal<>();

    private WebSession() {
    }

    public static void bind(WebDriver driver, int implicitWaitSeconds) {
        DRIVER.set(driver);
        WAIT.set(new WebDriverWait(driver, Duration.ofSeconds(Math.max(1, implicitWaitSeconds))));
    }

    public static WebDriver driver() {
        WebDriver d = DRIVER.get();
        if (d == null) {
            throw new IllegalStateException("No WebDriver bound; call WebSession.bind() in @Before");
        }
        return d;
    }

    public static WebElement waitForVisible(By locator) {
        return WAIT.get().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickable(By locator) {
        return WAIT.get().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void unbind() {
        WebDriver d = DRIVER.get();
        if (d != null) {
            try {
                d.quit();
            } finally {
                DRIVER.remove();
                WAIT.remove();
            }
        } else {
            DRIVER.remove();
            WAIT.remove();
        }
    }
}
