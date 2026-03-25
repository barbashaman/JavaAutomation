package framework.context;

/**
 * High-level automation surface. Select via {@code automation.channel} in {@code config.properties}
 * or override with system property {@code automation.channel}.
 */
public enum AutomationChannel {
    /** Browser UI via Selenium WebDriver */
    WEB,
    /** HTTP APIs via REST Assured */
    API,
    /** Native or hybrid apps via Appium */
    MOBILE
}
