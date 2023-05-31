package framework.utils.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

    public String getProperty(String propertyKey) {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new FileNotFoundException("config.properties file not found");
            }
            properties.load(input);
            return properties.getProperty(propertyKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Return null if the property is not found or there is an error
    }

    public int getIntegerProperty(String propertyKey) {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new FileNotFoundException("config.properties file not found");
            }
            properties.load(input);
            return Integer.parseInt(properties.getProperty(propertyKey));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if the property is not found or there is an error
    }
}
