package utilityfactories;

import myexceptions.WrongArgumentException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public interface AbstractFactory {

     static Properties getProperties(String configurationFileName) {
        try (FileReader fileReader = new FileReader(configurationFileName)) {
            Properties properties = new Properties();
            properties.load(fileReader);
            return properties;
        } catch (IOException e) {
            throw new WrongArgumentException("Can not find this path to properties"
                    , configurationFileName, e);
        }
    }

    static String getIfPropertyExists(String configurationFileName, String propertyName) {
        Properties properties = getProperties(configurationFileName);
        String property = properties.getProperty(propertyName);
        if (property == null) {
            throw new WrongArgumentException("Property with this name doesn't exist", propertyName);
        }
        return property;
    }
}
