package testcases.mobile;

import framework.mobile.MobileDriverFactory;
import framework.mobile.MobileSession;
import framework.utils.config.PropertiesUtils;
import io.appium.java_client.AppiumDriver;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

/**
 * Mobile E2E placeholder: runs only when {@code RUN_MOBILE_E2E=true} and Appium server + device match config.
 */
public class ExampleMobileSessionTest {

    private static boolean mobileE2eEnabled() {
        String v = System.getenv("RUN_MOBILE_E2E");
        return v != null && Boolean.parseBoolean(v.trim());
    }

    @Before
    public void setUp() throws Exception {
        Assume.assumeTrue("Set RUN_MOBILE_E2E=true with Appium running to execute mobile E2E",
                mobileE2eEnabled());
        PropertiesUtils config = new PropertiesUtils();
        AppiumDriver driver = MobileDriverFactory.createDriver(config);
        MobileSession.bind(driver);
    }

    @After
    public void tearDown() {
        MobileSession.unbind();
    }

    @Test
    public void mobileSession_isBound() {
        MobileSession.driver().getPageSource();
    }
}
