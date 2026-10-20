package utilities;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class ConfigReader {
    private static final Properties PROPERTIES = new Properties();
    static {
        try (InputStream input = Files.newInputStream(Path.of("configuration.properties"))) {
            PROPERTIES.load(input);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot load configuration.properties", e);
        }
    }
    private ConfigReader() { }
    public static String getProperty(String key) {
        String envKey = switch (key) {
            case "username" -> "N11_USERNAME";
            case "password" -> "N11_PASSWORD";
            default -> key.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase(java.util.Locale.ROOT);
        };
        String value = System.getProperty(key);
        if (value == null) value = System.getenv(envKey);
        if (value == null) value = PROPERTIES.getProperty(key);
        if (value == null || value.isBlank())
            throw new IllegalStateException("Missing configuration: " + envKey);
        return value;
    }
}
