package tasks;

import pageObjects.GoogleConsentModal;
import pageObjects.GoogleHomePage;

public class GoogleSearchTask {
    private GoogleHomePage googleHomePage;

    public GoogleSearchTask() {
        this.googleHomePage = new GoogleHomePage();
    }

    /**
     * Accepts Google's consent / cookie modal when shown (iframe or main document).
     */
    public void acceptAllCookies() {
        GoogleConsentModal.acceptIfPresent();
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
