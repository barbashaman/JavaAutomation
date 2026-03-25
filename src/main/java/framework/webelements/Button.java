package framework.webelements;

import framework.context.WebSession;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Button {
    private WebElement element;

    public Button(By locator) {
        this.element = WebSession.waitForVisible(locator);
    }

    public void click() {
        element.click();
    }
}
