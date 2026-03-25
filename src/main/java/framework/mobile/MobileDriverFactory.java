package framework.mobile;

import framework.utils.config.PropertiesUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Builds {@link io.appium.java_client.AppiumDriver} instances from {@code config.properties} for local or remote grids.
 */
public final class MobileDriverFactory {

    private MobileDriverFactory() {
    }

    public static AppiumDriver createDriver(PropertiesUtils config) throws MalformedURLException {
        String hub = config.getProperty("mobile.hubUrl", "http://127.0.0.1:4723/");
        URL serverUrl = URI.create(hub).toURL();
        String platform = config.getProperty("mobile.platform", "android").trim().toLowerCase();
        if ("ios".equals(platform)) {
            return new IOSDriver(serverUrl, buildIosOptions(config));
        }
        return new AndroidDriver(serverUrl, buildAndroidOptions(config));
    }

    private static UiAutomator2Options buildAndroidOptions(PropertiesUtils config) {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(config.getProperty("mobile.deviceName", "Android Emulator"));
        String platformVersion = config.getProperty("mobile.platformVersion", "");
        if (!platformVersion.isBlank()) {
            options.setPlatformVersion(platformVersion);
        }
        String app = config.getProperty("mobile.app", "");
        if (!app.isBlank()) {
            options.setApp(app);
        }
        String pkg = config.getProperty("mobile.appPackage", "");
        String activity = config.getProperty("mobile.appActivity", "");
        if (!pkg.isBlank()) {
            options.setAppPackage(pkg);
        }
        if (!activity.isBlank()) {
            options.setAppActivity(activity);
        }
        return options;
    }

    private static XCUITestOptions buildIosOptions(PropertiesUtils config) {
        XCUITestOptions options = new XCUITestOptions();
        options.setDeviceName(config.getProperty("mobile.deviceName", "iPhone 15"));
        String platformVersion = config.getProperty("mobile.platformVersion", "17.0");
        if (!platformVersion.isBlank()) {
            options.setPlatformVersion(platformVersion);
        }
        String app = config.getProperty("mobile.app", "");
        if (!app.isBlank()) {
            options.setApp(app);
        }
        String bundleId = config.getProperty("mobile.bundleId", "");
        if (!bundleId.isBlank()) {
            options.setBundleId(bundleId);
        }
        return options;
    }
}
