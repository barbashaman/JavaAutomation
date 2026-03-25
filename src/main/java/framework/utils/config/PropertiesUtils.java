package framework.utils.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

    private final String resourceName;

    public PropertiesUtils() {
        this("config.properties");
    }

    /**
     * @param resourceName classpath resource, e.g. {@code test-config.properties} for unit tests
     */
    public PropertiesUtils(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getProperty(String propertyKey) {
        return getProperty(propertyKey, null);
    }

    public String getProperty(String propertyKey, String defaultValue) {
        Properties properties = loadProperties();
        if (properties == null) {
            return defaultValue;
        }
        return properties.getProperty(propertyKey, defaultValue);
    }

    public int getIntegerProperty(String propertyKey) {
        return getIntegerProperty(propertyKey, -1);
    }

    public int getIntegerProperty(String propertyKey, int defaultValue) {
        Properties properties = loadProperties();
        if (properties == null) {
            return defaultValue;
        }
        String raw = properties.getProperty(propertyKey);
        if (raw == null || raw.isBlank()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(raw.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public boolean getBooleanProperty(String propertyKey, boolean defaultValue) {
        String v = getProperty(propertyKey);
        if (v == null || v.isBlank()) {
            return defaultValue;
        }
        return Boolean.parseBoolean(v.trim());
    }

    private Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            if (input == null) {
                throw new FileNotFoundException(resourceName + " file not found");
            }
            properties.load(input);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
