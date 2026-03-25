package pageObjects;

import framework.context.WebSession;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * Dismisses Google's cookie / "Before you continue" consent layer. The markup and iframe vary by
 * region and A/B tests, so several strategies are tried in order.
 */
public final class GoogleConsentModal {

    private static final int TIMEOUT_SECONDS = 15;
    private static final long POLL_MS = 250;

    /**
     * Buttons and roles that confirm consent (not exhaustive; extend if your locale differs).
     */
    private static final List<By> ACCEPT_LOCATORS = Arrays.asList(
            By.xpath("//button[contains(., 'Accept all')]"),
            By.xpath("//button[.//*[contains(normalize-space(.), 'Accept all')]]"),
            By.xpath("//*[@role='button'][contains(., 'Accept all')]"),
            By.xpath("//button[contains(., 'Aceitar tudo')]"),
            By.xpath("//button[contains(., 'Aceptar todo')]"),
            By.xpath("//button[contains(., 'Tout accepter')]"),
            By.xpath("//button[contains(., 'Alle akzeptieren')]"),
            By.xpath("//button[contains(., 'I agree')]"),
            By.xpath("//button[contains(., 'Agree')]"),
            By.xpath("//button[contains(@aria-label, 'Accept')]"),
            By.cssSelector("button#L2AGLb")
    );

    private GoogleConsentModal() {
    }

    public static void acceptIfPresent() {
        WebDriver driver = WebSession.driver();
        driver.switchTo().defaultContent();
        if (!hasLikelyConsentIframe(driver) && searchFieldIsReady(driver)) {
            return;
        }
        long deadline = System.currentTimeMillis() + Duration.ofSeconds(TIMEOUT_SECONDS).toMillis();
        while (System.currentTimeMillis() < deadline) {
            if (tryClickVisibleAccept(driver)) {
                sleepBriefly();
                driver.switchTo().defaultContent();
                return;
            }
            if (tryClickAcceptInsideIframes(driver)) {
                sleepBriefly();
                driver.switchTo().defaultContent();
                return;
            }
            if (!hasLikelyConsentIframe(driver) && searchFieldIsReady(driver)) {
                return;
            }
            sleepPoll();
        }
    }

    private static boolean hasLikelyConsentIframe(WebDriver driver) {
        for (WebElement frame : driver.findElements(By.tagName("iframe"))) {
            String src = frame.getDomAttribute("src");
            if (src != null && src.toLowerCase().contains("consent")) {
                return true;
            }
            String name = frame.getDomAttribute("name");
            if (name != null && name.toLowerCase().contains("callout")) {
                return true;
            }
        }
        return false;
    }

    private static boolean searchFieldIsReady(WebDriver driver) {
        for (WebElement el : driver.findElements(By.name("q"))) {
            if (el.isDisplayed() && el.isEnabled()) {
                return true;
            }
        }
        return false;
    }

    private static boolean tryClickVisibleAccept(WebDriver driver) {
        for (By locator : ACCEPT_LOCATORS) {
            List<WebElement> found = driver.findElements(locator);
            for (WebElement el : found) {
                if (isClickableConsentButton(el)) {
                    clickElement(driver, el);
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean tryClickAcceptInsideIframes(WebDriver driver) {
        List<WebElement> frames = driver.findElements(By.tagName("iframe"));
        for (WebElement frame : frames) {
            try {
                driver.switchTo().frame(frame);
                if (tryClickVisibleAccept(driver)) {
                    return true;
                }
            } catch (Exception ignored) {
                // Stale frame or cross-origin; continue
            } finally {
                driver.switchTo().defaultContent();
            }
        }
        return false;
    }

    private static void clickElement(WebDriver driver, WebElement el) {
        try {
            el.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

    private static boolean isClickableConsentButton(WebElement el) {
        try {
            if (!el.isDisplayed() || !el.isEnabled()) {
                return false;
            }
            String text = el.getText();
            if (text != null && text.toLowerCase().contains("reject")) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static void sleepPoll() {
        sleepBriefly();
    }

    private static void sleepBriefly() {
        try {
            Thread.sleep(POLL_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
