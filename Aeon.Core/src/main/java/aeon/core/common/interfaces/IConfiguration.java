package aeon.core.common.interfaces;

import aeon.core.common.BaseConfiguration;

import java.io.IOException;

/**
 * Interface for configuration classes.
 */
public interface IConfiguration {

    /**
     * Get the boolean value of a key and value pair.
     * @param key A key from {@link BaseConfiguration.Keys} or any other configuration key.
     * @param defaultValue True or false.
     * @return True or False representation of key and value pair.
     */
    boolean getBoolean(String key, boolean defaultValue);

    /**
     * Get the double value of a key and value pair.
     * @param key A key from {@link BaseConfiguration.Keys} or any other configuration key.
     * @param defaultValue True or false.
     * @return Double representation of key and value pair.
     */
    double getDouble(String key, double defaultValue);

    /**
     * Get the string value of a key and value pair.
     * @param key A key from {@link BaseConfiguration.Keys} or any other configuration key.
     * @param defaultValue True or false.
     * @return String representation of key and value pair.
     */
    String getString(String key, String defaultValue);

    /**
     *  Loads configuration from properties files.
     *
     * @throws IOException If properties are not defined.
     * @throws IllegalAccessException If issue obtaining keys.
     */
    public void loadConfiguration() throws IOException, IllegalAccessException;
}
