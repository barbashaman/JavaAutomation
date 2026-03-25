package testcases.web;

import framework.context.WebSession;
import framework.utils.config.PropertiesUtils;
import framework.utils.web.WebDriverCommon;
import framework.utils.web.WebDriverFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import tasks.GoogleSearchTask;

public class ExampleGoogleSearchUsingPlainSeleniumTest {

    private PropertiesUtils config;
    private WebDriver driver;
    private WebDriverCommon common;
    private GoogleSearchTask googleSearchTask;

    @Before
    public void setUp() {
        config = new PropertiesUtils();
        driver = WebDriverFactory.createDriver(config);
        int waitSeconds = config.getIntegerProperty("wait", 10);
        WebSession.bind(driver, waitSeconds);
        common = new WebDriverCommon(driver, config);
        googleSearchTask = new GoogleSearchTask();
        common.open(config.getProperty("url", "https://www.google.com"));
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
        WebSession.unbind();
    }
}
