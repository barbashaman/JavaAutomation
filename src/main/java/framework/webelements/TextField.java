package framework.webelements;

import framework.context.WebSession;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class TextField {

    private final WebElement element;

    public TextField(By locator) {
        this.element = WebSession.waitForClickable(locator);
    }

    public void sendKeys(String text) {
        WebElement el = element;
        WebDriver driver = WebSession.driver();
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center',inline:'nearest'});", el);
        try {
            new Actions(driver).moveToElement(el).pause(Duration.ofMillis(100)).click().perform();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", el);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
        try {
            el.clear();
        } catch (Exception e) {
            el.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            el.sendKeys(Keys.DELETE);
        }
        el.sendKeys(text);
    }

    public void submit() {
        try {
            element.submit();
        } catch (Exception e) {
            element.sendKeys(Keys.ENTER);
        }
    }
}
