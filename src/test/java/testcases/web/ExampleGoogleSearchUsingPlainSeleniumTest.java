package testcases.web;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import tasks.GoogleSearchTask;
import framework.utils.web.WebDriverCommon;
import framework.utils.web.WebDriverFactory;
import framework.utils.config.PropertiesUtils;

public class ExampleGoogleSearchUsingPlainSeleniumTest {

    private WebDriver driver;
    private WebDriverCommon common;
    private String URL;
    private GoogleSearchTask googleSearchTask;

    @Before
    public void setUp() {
        driver = WebDriverFactory.getWebDriver();
        common = new WebDriverCommon(driver);
        URL = new PropertiesUtils().getProperty("url");
        googleSearchTask = new GoogleSearchTask();
        common.open(URL);
    }

    @Test
    public void validateGoogleSearchForCheeseTest() {
        googleSearchTask.acceptAllCookies();

        googleSearchTask.searchFor("cheese");

        boolean isCheeseIncluded = googleSearchTask.isSearchResultVisible("cheese");

        Assert.assertTrue("Cheese is not included in the search results.", isCheeseIncluded);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
