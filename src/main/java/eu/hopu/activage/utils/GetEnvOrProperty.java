package eu.hopu.activage.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetEnvOrProperty {

    private static Properties properties = new Properties();

    private static GetEnvOrProperty instance;

    private GetEnvOrProperty() {
        loadProperties();
    }

    public static GetEnvOrProperty getInstance() {
        if (instance == null)
            instance = new GetEnvOrProperty();
        return instance;
    }

    public void loadProperties() {
        String propertiesPath = System.getenv("PROPERTIES_PATH") != null ?
                System.getenv("PROPERTIES_PATH") : "src/main/resources/config.properties";
        InputStream file;
        try {
            file = new FileInputStream(propertiesPath);
            properties.load(file);
            file.close();
        } catch (IOException e) {
            System.out.println("[LWM2M-SERVER] Cannot find properties file");
        }
    }

    public String get(String property) {
        if (System.getenv(property) != null)
            return System.getenv(property);
        else if (properties.getProperty(property) != null)
            return properties.getProperty(property);
        else
            return "";
    }

    public void set(String property, String value) {
        properties.setProperty(property, value);
    }

}
