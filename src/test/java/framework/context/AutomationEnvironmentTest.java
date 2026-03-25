package framework.context;

import framework.utils.config.PropertiesUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AutomationEnvironmentTest {

    @Before
    @After
    public void clearAutomationChannelSystemProperty() {
        System.clearProperty("automation.channel");
    }

    @Test
    public void currentChannel_readsFromConfigWhenSystemPropertyUnset() {
        Assert.assertEquals(
                AutomationChannel.API,
                AutomationEnvironment.currentChannel(new PropertiesUtils("test-config.properties")));
    }

    @Test
    public void currentChannel_systemPropertyOverridesConfig() {
        System.setProperty("automation.channel", "web");
        Assert.assertEquals(
                AutomationChannel.WEB,
                AutomationEnvironment.currentChannel(new PropertiesUtils("test-config.properties")));
    }
}
