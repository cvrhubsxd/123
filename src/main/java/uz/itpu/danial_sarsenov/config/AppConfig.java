package uz.itpu.danial_sarsenov.config;

import java.io.InputStream;
import java.util.Properties;

public class AppConfig {

    private final Properties properties = new Properties();

    public AppConfig(String fileName) {
        try (InputStream is = getClass().getClassLoader()
                .getResourceAsStream(fileName + ".properties")) {

            if (is == null) {
                throw new RuntimeException("Config file not found");
            }

            properties.load(is);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config", e);
        }
    }

    public int getPort() {
        return Integer.parseInt(properties.getProperty("server.port", "12345"));
    }
}