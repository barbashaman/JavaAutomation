package tasks;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageObjects.GoogleHomePage;

public class GoogleSearchTask {
    private GoogleHomePage googleHomePage;

    public GoogleSearchTask() {
        this.googleHomePage = new GoogleHomePage();
    }

    public void acceptAllCookies() {
        this.googleHomePage.getAcceptAllCookiesButton().click();
    }

    public void enterSearchKeyword(String keyword) {
        this.googleHomePage.getSearchBox().sendKeys(keyword);
    }

    public void submitSearch() {
        this.googleHomePage.getSearchBox().submit();
    }

    public boolean isSearchResultVisible(String keyword) {
        return this.googleHomePage.getSearchResults().labelContainsText(keyword);

    }

    public void searchFor(String keyword) {
        enterSearchKeyword(keyword);
        submitSearch();
    }

}
