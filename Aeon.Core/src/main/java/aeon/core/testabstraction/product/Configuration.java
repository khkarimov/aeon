package aeon.core.testabstraction.product;

import aeon.core.common.BaseConfiguration;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.drivers.IDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Base configuration class for aeon.
 * Loads the Properties table with the values from: Environment variables,
 * project test.properties files, and otherwise uses the default properties from aeon.
 */
public class Configuration extends BaseConfiguration {

    static Logger log = LogManager.getLogger(Configuration.class);
    private Class driver;
    private Class adapter;

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
     * Add given properties to the configuration, overriding properties with the same name.
     *
     * @param properties The properties to set.
     */
    void setProperties(Properties properties) {
        this.properties.putAll(properties);
    }

    @Override
    protected List<Field> getConfigurationFields() {
        List<Field> keys = super.getConfigurationFields();
        keys.addAll(Arrays.asList(Keys.class.getDeclaredFields()));
        return keys;
    }

    /**
     * Static class for the Configuration keys.
     */
    public static class Keys {

        public static final String TIMEOUT = "aeon.timeout";
        public static final String THROTTLE = "aeon.throttle";
        public static final String REPORTING = "aeon.reporting";
    }
}
