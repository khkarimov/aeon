package com.ultimatesoftware.aeon.core.common.interfaces;

import com.ultimatesoftware.aeon.core.common.AeonConfigKey;

import java.io.IOException;

/**
 * Interface for configuration classes.
 */
public interface IConfiguration {

    /**
     * Get the boolean value of a key and value pair.
     *
     * @param key          A key from {@link AeonConfigKey} or any other configuration key.
     * @param defaultValue True or false.
     * @return True or False representation of key and value pair.
     */
    boolean getBoolean(AeonConfigKey key, boolean defaultValue);

    /**
     * Get the boolean value of a key and value pair.
     *
     * @param key          A key value string.
     * @param defaultValue True or false.
     * @return True or False representation of key and value pair.
     */
    boolean getBoolean(String key, boolean defaultValue);

    /**
     * Get the double value of a key and value pair.
     *
     * @param key          A key from {@link AeonConfigKey} or any other configuration key.
     * @param defaultValue True or false.
     * @return Double representation of key and value pair.
     */
    double getDouble(AeonConfigKey key, double defaultValue);

    /**
     * Get the double value of a key and value pair.
     *
     * @param key          A key value string.
     * @param defaultValue True or false.
     * @return Double representation of key and value pair.
     */
    double getDouble(String key, double defaultValue);

    /**
     * Get the string value of a key and value pair.
     *
     * @param key          A key from {@link AeonConfigKey} or any other configuration key.
     * @param defaultValue True or false.
     * @return String representation of key and value pair.
     */
    String getString(AeonConfigKey key, String defaultValue);

    /**
     * Get the string value of a key and value pair.
     *
     * @param key          A key value string.
     * @param defaultValue True or false.
     * @return String representation of key and value pair.
     */
    String getString(String key, String defaultValue);

    /**
     * Loads configuration from properties files.
     *
     * @throws IOException            If properties are not defined.
     * @throws IllegalAccessException If issue obtaining keys.
     */
    void loadConfiguration() throws IOException, IllegalAccessException;
}
