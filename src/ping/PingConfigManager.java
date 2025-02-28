package ping;

import java.io.IOException;
import java.util.Properties;

public class PingConfigManager
{
    private static final Properties properties = new Properties();
    private static final String CONFIG_FILE_NAME = "resources/config.properties";

    static
    {
        try (var inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE_NAME))
        {
            if (inputStream != null)
            {
                properties.load(inputStream);
            }
        }
        catch (IOException e)
        {
            System.err.println("Error loading config: " + e.getMessage());
        }
    }

    public static String get(String key, String defaultValue)
    {
        return properties.getProperty(key, defaultValue);
    }
}
