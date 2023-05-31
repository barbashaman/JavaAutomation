package framework.webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static framework.utils.web.WebDriverCommon.waitForElementBy;
import static framework.utils.web.WebDriverFactory.getWebDriver;

public class Label {

    private WebElement element;

    public Label(By locator) {
        this.element = waitForElementBy(locator);
    }

    public String getText() {
        return element.getText();
    }

    public boolean labelContainsText(String text){
        return element.getText().toLowerCase().contains(text.toLowerCase());
    }
}
