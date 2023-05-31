package framework.webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static framework.utils.web.WebDriverCommon.waitForElementBy;
import static framework.utils.web.WebDriverFactory.getWebDriver;

public class Button {
    private WebElement element;

    public Button(By locator) {
        this.element = waitForElementBy(locator);
    }

    public void click() {
        element.click();
    }
}
