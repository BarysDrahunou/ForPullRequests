package utilityfactories;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public interface AbstractFactory {

    static String getProperty(Properties properties, String nameOfProperty) {
        return properties.getProperty(nameOfProperty);
    }

    static Properties getProperties(String configurationFileName) throws IOException {
        Properties properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream(configurationFileName);
        properties.load(stream);
        return properties;
    }
}
