package helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestProperties {

    private static Properties testProperties;
    private static String PROPERTY_FILE_NAME = "test.properties";

    public TestProperties() {
    }

    public static String getAsString(String key) {
        if (testProperties == null) {
            init();
        }

        String value = testProperties.getProperty(key);
        if (value == null) {
            throw new RuntimeException(
                    String.format("Could not find property %s in %s", new Object[]{key, PROPERTY_FILE_NAME}));
        } else {
            return value;
        }
    }

    public static int getAsInt(String key) {
        return Integer.parseInt(getAsString(key));
    }

    public static Boolean getAsBool(String key) {
        return Boolean.valueOf(Boolean.parseBoolean(getAsString(key)));
    }

    private static void init() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        testProperties = new Properties();

        try {
            InputStream resourceStream = loader.getResourceAsStream(PROPERTY_FILE_NAME);
            Throwable error = null;

            try {
                testProperties.load(resourceStream);
            } catch (Throwable testPropertiesError) {
                error = testPropertiesError;
                try {
                    throw testPropertiesError;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            } finally {
                if (resourceStream != null) {
                    if (error != null) {
                        try {
                            resourceStream.close();
                        } catch (Throwable resourceStreamError) {
                            error.addSuppressed(resourceStreamError);
                        }
                    } else {
                        resourceStream.close();
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
