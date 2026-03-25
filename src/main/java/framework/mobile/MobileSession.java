package framework.mobile;

import io.appium.java_client.AppiumDriver;

/**
 * Thread-local Appium driver binding (parallel-safe pattern aligned with {@link framework.context.WebSession}).
 */
public final class MobileSession {

    private static final ThreadLocal<AppiumDriver> DRIVER = new ThreadLocal<>();

    private MobileSession() {
    }

    public static void bind(AppiumDriver driver) {
        DRIVER.set(driver);
    }

    public static AppiumDriver driver() {
        AppiumDriver d = DRIVER.get();
        if (d == null) {
            throw new IllegalStateException("No Appium driver bound; call MobileSession.bind() in @Before");
        }
        return d;
    }

    public static void unbind() {
        AppiumDriver d = DRIVER.get();
        if (d != null) {
            try {
                d.quit();
            } finally {
                DRIVER.remove();
            }
        } else {
            DRIVER.remove();
        }
    }
}
