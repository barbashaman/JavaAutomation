package framework.webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static framework.utils.web.WebDriverCommon.waitForElementBy;
import static framework.utils.web.WebDriverFactory.getWebDriver;

public class TextField {

    private WebElement element;

    public TextField(By locator) {
        this.element = waitForElementBy(locator);
    }

    public void sendKeys(String text) {
        element.clear();
        element.sendKeys(text);
    }

    public void submit(){
        element.submit();
    }
}
