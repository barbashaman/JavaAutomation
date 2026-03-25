package framework.utils.config;

import org.junit.Assert;
import org.junit.Test;

public class PropertiesUtilsTest {

    @Test
    public void getIntegerProperty_returnsParsedValue() {
        PropertiesUtils utils = new PropertiesUtils("test-config.properties");
        Assert.assertEquals(42, utils.getIntegerProperty("sample.int", 0));
    }

    @Test
    public void getIntegerProperty_returnsDefaultWhenMissing() {
        PropertiesUtils utils = new PropertiesUtils("test-config.properties");
        Assert.assertEquals(99, utils.getIntegerProperty("no.such.key", 99));
    }

    @Test
    public void getBooleanProperty_parsesTrue() {
        PropertiesUtils utils = new PropertiesUtils("test-config.properties");
        Assert.assertTrue(utils.getBooleanProperty("sample.bool", false));
    }
}
