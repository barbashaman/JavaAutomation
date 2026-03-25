package pageObjects;

import framework.webelements.*;
import org.openqa.selenium.By;

public class GoogleHomePage {

    /**
     * Main query box lives inside the search form; {@code name=q} alone can match hidden duplicates.
     */
    private static final By SEARCH_QUERY = By.cssSelector(
            "form[role='search'] textarea[name='q'], form[role='search'] input[name='q']");

    public TextField getSearchBox() {
        return new TextField(SEARCH_QUERY);
    }

    public Label getSearchResults() {
        return new Label(By.id("search"));
    }
}
