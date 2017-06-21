package aeon.core.testabstraction.product;

import aeon.core.common.web.BrowserType;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by josepe on 4/12/2017.
 */
public class Configuration {

    private static Logger log = LogManager.getLogger(Configuration.class);
    public Properties properties;
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
     * @throws IOException If properties are not defined.
     * @throws IllegalAccessException If issue obataining keys.
     */
    public <D extends IWebDriver, A extends IAdapter> Configuration(Class<D> driver, Class<A> adapter) throws IOException, IllegalAccessException {
        this.driver = driver;
        this.adapter = adapter;
        properties = new Properties();

        try (
                InputStream inAeon = Configuration.class.getResourceAsStream("/aeon.properties");
                InputStream inConfig = Configuration.class.getResourceAsStream("/config.properties")
        ) {
            properties.load(inAeon);
            loadPluginSettings();
            if (inConfig != null) {
                properties.load(inConfig);
            } else {
                log.info("No config file in use, using default values.");
            }
        } catch (IOException e) {
            log.error("No aeon.properties found");
            throw e;
        }

        List<Field> keys = getConfigurationFields();
        keys.addAll(Arrays.asList(Keys.class.getDeclaredFields()));
        for (Field key : keys) {
            key.setAccessible(true);
            String keyValue = key.get(null).toString();
            String environmentValue = System.getenv(keyValue);
            if (environmentValue != null) {
                properties.setProperty(keyValue, environmentValue);
            }
        }
    }

    /**
     * Get a empty list contain fields.
     *
     * @return List containing fields.
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
     * Set the class for the adpater.
     *
     * @param adapter The class of the adapter.
     */
    public void setAdapter(Class adapter) {
        this.adapter = adapter;
    }

    /**
     *
     * @throws IOException If inputs are incorrect.
     */
    public void loadPluginSettings() throws IOException {
    }

    /**
     * Get the type of browser.
     *
     * @return The {@link BrowserType} for the the configuration.
     */
    public BrowserType getBrowserType() {
        return browserType;
    }

    /**
     * Set the type of browser.
     *
     * @param browserType The {@link BrowserType} for the configuration.
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

    public void setString(String key, String value) {
        set(key, value);
    }

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

    private String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static class Keys {

        public static final String WAIT_FOR_AJAX_RESPONSES = "aeon.wait_for_ajax_responses";
        public static final String BROWSER = "aeon.browser";
        public static final String ENVIRONMENT = "aeon.environment";
        public static final String PROTOCOL = "aeon.protocol";
        public static final String TIMEOUT = "aeon.timeout";
        public static final String AJAX_TIMEOUT = "aeon.timeout.ajax";
    }
}
