package readerswritersfactories;

import myexceptions.WrongArgumentException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static constants.ExceptionsMessages.*;

public class PropertiesUtilClass {

    static Properties getProperties(String configurationFileName) {
        try (FileReader fileReader = new FileReader(configurationFileName)) {
            Properties properties = new Properties();
            properties.load(fileReader);
            return properties;
        } catch (IOException e) {
            throw new WrongArgumentException(INCORRECT_PATH_TO_PROPERTIES,
                    configurationFileName, e);
        }
    }

    public static String getIfPropertyExists(String configurationFileName, String propertyName) {
        Properties properties = getProperties(configurationFileName);
        String property = properties.getProperty(propertyName);
        if (property == null) {
            throw new WrongArgumentException(PROPERTY_DOES_NOT_EXIST, propertyName);
        }
        return property;
    }
}
