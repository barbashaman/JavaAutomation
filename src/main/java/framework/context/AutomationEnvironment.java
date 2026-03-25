package framework.context;

import framework.utils.config.PropertiesUtils;

/**
 * Resolves which automation channel is active. Override at runtime with {@code -Dautomation.channel=API}.
 */
public final class AutomationEnvironment {

    private AutomationEnvironment() {
    }

    public static AutomationChannel currentChannel(PropertiesUtils config) {
        String fromSystem = System.getProperty("automation.channel");
        String raw = (fromSystem != null && !fromSystem.isBlank())
                ? fromSystem
                : config.getProperty("automation.channel", "WEB");
        return AutomationChannel.valueOf(raw.trim().toUpperCase());
    }
}
