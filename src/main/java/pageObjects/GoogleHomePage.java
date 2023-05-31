package pageObjects;

import framework.webelements.*;
import org.openqa.selenium.By;

public class GoogleHomePage {

    public Button getAcceptAllCookiesButton() {
        return new Button(By.xpath("//button/div[contains(text(),'Accept all')]"));
    }

    public TextField getSearchBox() {
        return new TextField(By.name("q"));
    }

    public Label getSearchResults() {
        return new Label(By.id("search"));
    }
}
