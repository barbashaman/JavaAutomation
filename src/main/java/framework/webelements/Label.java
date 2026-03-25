package framework.webelements;

import framework.context.WebSession;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Label {

    private WebElement element;

    public Label(By locator) {
        this.element = WebSession.waitForVisible(locator);
    }

    public String getText() {
        return element.getText();
    }

    public boolean labelContainsText(String text){
        return element.getText().toLowerCase().contains(text.toLowerCase());
    }
}
