package aeon.core.testabstraction.product;

import aeon.core.common.web.BrowserType;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.drivers.IDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * Base configuration class for aeon.
 * Loads the Properties table with the values from: Environment variables,
 * project test.properties files, and otherwise uses the default properties from aeon.
 */
public class Configuration {

    static Logger log = LogManager.getLogger(Configuration.class);
    protected Properties properties;
    private Class driver;
    private Class adapter;
    private BrowserType browserType;

    /**
     * Initializes a new instance of the {@link Configuration} class.
     *
     * @param driver AeonWebDriver.class.
     * @param adapter SeleniumAdapter.class.
     * @param <D> AeonWebDriver.class.
     * @param <A> SeleniumAdapter.class.
     */
    public <D extends IDriver, A extends IAdapter> Configuration(Class<D> driver, Class<A> adapter) {
        this.driver = driver;
        this.adapter = adapter;
        properties = new Properties();
    }

    /**
     *  Loads configuration from properties files.
     *
     * @throws IOException If properties are not defined.
     * @throws IllegalAccessException If issue obtaining keys.
     */
    public void loadConfiguration() throws IOException, IllegalAccessException {
        try (
                InputStream inAeon = getAeonInputStream();
                InputStream inConfig = getConfigurationProperties()
        ) {
            if (inAeon == null) {
                throw new IOException("No aeon.properties file was found.");
            }
            properties.load(inAeon);
            loadModuleSettings();
            loadPluginSettings();
            if (inConfig != null) {
                properties.load(inConfig);
            } else {
                log.info("No config file in use, using default values.");
            }
        } catch (FileNotFoundException fe){
            log.error("The specified config file was not found");
            throw fe;
        } catch (IOException e) {
            log.error("There was a problem reading aeon.properties or test.properties.");
            throw e;
        }

        setProperties();
    }

    /**
     * Loads settings specific to a module.
     *
     * @throws IOException Is thrown if a settings file could not be found or read.
     */
    protected void loadModuleSettings() throws IOException {
    }

    /**
     *  Gets InputStream from the file specified in the env variable AEON_CONFIG or test.properties
     *  when the variable is not specified.
     *
     * @return InputStream of loaded properties.
     * @throws FileNotFoundException If issue finding config file.
     */
    public InputStream getConfigurationProperties() throws FileNotFoundException {
        String envValue = getEnvironmentValue("AEON_CONFIG");
        if (envValue == null) {
            return getDefaultConfigInputStream();
        }
        Path configPath = Paths.get(envValue);
        if (configPath.isAbsolute()){
            return new FileInputStream(configPath.toFile());
        } else {
            return getRelativeAeonConfigProperties(configPath);
        }
    }

    /**
     *  Gathers the properties found in the config file specified in the env variable AEON_CONFIG.
     *
     * @param configPath The path to the config file.
     * @return InputStream of config file.
     * @throws FileNotFoundException If issue finding config file.
     */
    public InputStream getRelativeAeonConfigProperties(Path configPath) throws FileNotFoundException {
        Path absolute = Paths.get(".").toAbsolutePath().getParent();
        configPath = Paths.get(absolute.toString(), configPath.toString());
        return new FileInputStream(configPath.toFile());
    }

    /**
     *  Loads properties from environment variables, overriding settings from properties files.
     *
     * @throws IllegalAccessException If issue obtaining keys.
     */
    private void setProperties() throws IllegalAccessException {
        List<Field> keys = getConfigurationFields();
        keys.addAll(Arrays.asList(Keys.class.getDeclaredFields()));
        for (Field key : keys) {
            key.setAccessible(true);
            String keyValue = key.get(null).toString();
            String environmentValue = getEnvironmentValue(keyValue);
            if (environmentValue != null) {
                properties.setProperty(keyValue, environmentValue);
            }
        }

        Enumeration e = properties.propertyNames();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("These are the properties values currently in use:\n");
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            stringBuilder.append(String.format("%1$s = %2$s\n", key, properties.getProperty(key)));
        }
        log.info(stringBuilder.toString());
    }

    /**
     * Gets InputStream of test.properties.
     *
     * @return getResourceAsStream of "/test.properties" file
     */
     InputStream getDefaultConfigInputStream() {
         return Configuration.class.getResourceAsStream("/test.properties");
     }

    /**
     * Gets InputStream of aeon.properties.
     *
     * @return getResourceAsStream of "/aeon.properties" file
     */
     InputStream getAeonInputStream() {
        return Configuration.class.getResourceAsStream("/aeon.properties");
    }

    /**
     * Gets String returned by System.getenv call.
     *
     * @param keyValue String value of key
     * @return the environment value associated with the key
     */
    String getEnvironmentValue(String keyValue) {
        return System.getenv(keyValue);
    }


    /**
     * Get a empty list contain fields.
     *
     * @return List containing fields
     */
    protected List<Field> getConfigurationFields() {
        return new ArrayList<>();
    }

    /**
     * Get the driver class.
     *
     * @return The class of the driver.
     */
    public Class getDriver() {
        return driver;
    }

    /**
     * Set the driver class.
     *
     * @param driver The class of the driver.
     */
    public void setDriver(Class driver) {
        this.driver = driver;
    }

    /**
     * Get the adapter class.
     *
     * @return The class of the adapter.
     */
    public Class getAdapter() {
        return adapter;
    }

    /**
     * Set the class for the adapter.
     *
     * @param adapter The class of the adapter.
     */
    public void setAdapter(Class adapter) {
        this.adapter = adapter;
    }

    /**
     * Loads plugin specific settings.
     * Is implemented in child classes.
     *
     * @throws IOException If inputs are incorrect.
     */
    protected void loadPluginSettings() throws IOException {
    }

    /**
     * Get the type of browser.
     *
     * @return The {@link BrowserType} for the the configuration.
     * @deprecated Use WebConfiguration.getBrowserType() instead.
     */
    public BrowserType getBrowserType() {
        return browserType;
    }

    /**
     * Set the type of browser.
     *
     * @param browserType The {@link BrowserType} for the configuration.
     * @deprecated Use WebConfiguration.setBrowserType instead.
     */
    public void setBrowserType(BrowserType browserType) {
        this.browserType = browserType;
    }

    /**
     * Set boolean for a key and value pair.
     *
     * @param key A key from {@link Keys}.
     * @param value True or false.
     */
    public void setBoolean(String key, boolean value) {
        set(key, Boolean.toString(value));
    }

    /**
     * Sets a key to a specific value.
     * @param key the string of the key.
     * @param value the string value to set it to.
     */
    public void setString(String key, String value) {
        set(key, value);
    }

    /**
     * Sets the double given a string key and a double value.
     * @param key the string of the key to be set.
     * @param value the value to set.
     */
    public void setDouble(String key, double value) {
        set(key, Double.toString(value));
    }

    /**
     * Set the properties for the {@link Properties} command.
     * @param key A key from {@link Keys}
     * @param value True or false.
     */
    private void set(String key, String value) {
        properties.setProperty(key, value);
    }

    /**
     * Get the boolean value of a key and value pair.
     * @param key A key from {@link Keys}.
     * @param defaultValue True or false.
     * @return True or False representation of key and value pair.
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.valueOf(get(key, Boolean.toString(defaultValue)));
    }

    /**
     * Get the double value of a key and value pair.
     * @param key A key from {@link Keys}.
     * @param defaultValue True or false.
     * @return Double representation of key and value pair.
     */
    public double getDouble(String key, double defaultValue) {
        return Double.parseDouble(get(key, Double.toString(defaultValue)));
    }

    /**
     * Get the string value of a key and value pair.
     * @param key A key from {@link Keys}.
     * @param defaultValue True or false.
     * @return String representation of key and value pair.
     */
    public String getString(String key, String defaultValue) {
        return (get(key, defaultValue));
    }

    /**
     * Gets property associated with key and its default value.
     * @param key a property key
     * @param defaultValue a default property value
     * @return String value of property
     */
    private String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Static class for the Configuration keys.
     */
    public static class Keys {

        /**
         * @deprecated Use WebConfiguration.Keys.BROWSER instead
         */
        public static final String BROWSER = "aeon.browser";
        public static final String TIMEOUT = "aeon.timeout";
    }
}
