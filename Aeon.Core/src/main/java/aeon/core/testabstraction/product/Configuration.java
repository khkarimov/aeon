package aeon.core.testabstraction.product;

import aeon.core.common.web.BrowserType;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Created by josepe on 4/12/2017.
 */
public class Configuration {
    private Class driver;
    private Class adapter;
    private BrowserType browserType;
    private static Logger log = LogManager.getLogger(Configuration.class);

    public static class Keys {
        public static final String ajaxWaiter = "wait_for_ajax_response";
        public static final String defaultTimeout = "default_timeout";
        public static final String promptUserForContinueOnExceptionDecision = "prompt_user_for_continue_on_exception_decision";
        public static final String ensureCleanEnvironment = "ensure_clean_environment";
        public static final String browserType = "browser_type";
    }
    public Properties properties;

    public <D extends IWebDriver, A extends IAdapter> Configuration(Class<D> driver, Class<A> adapter) throws IOException {
        this.driver = driver;
        this.adapter = adapter;
        properties = new Properties();

        try(
                InputStream inAeon = Configuration.class.getResourceAsStream("/aeon.properties");
                InputStream inConfig = Configuration.class.getResourceAsStream("/config.properties")
        ){
            properties.load(inAeon);
            loadPluginSettings();
            if(inConfig != null) {
                properties.load(inConfig);
            } else {
                log.info("No config file in use, using default values.");
            }
        } catch(IOException e){
            log.error("No aeon.properties found");
            throw e;
        }

        for(Field key : Configuration.Keys.class.getDeclaredFields()){
            String environmentValue = System.getenv("aeon." + key);
            if(environmentValue != null)
                properties.setProperty(key.toString(), environmentValue);
        }
    }


    public Class getDriver() {
        return driver;
    }

    public void setDriver(Class driver) {
        this.driver = driver;
    }

    public Class getAdapter() {
        return adapter;
    }

    public void setAdapter(Class adapter) {
        this.adapter = adapter;
    }

    public void loadPluginSettings() throws IOException {
    }

    public BrowserType getBrowserType() {
        return browserType;
    }

    public void setBrowserType(BrowserType browserType) {
        this.browserType = browserType;
    }

    public void setBoolean(String key, boolean value){
        set(key, Boolean.toString(value));
    }

    public void setString(String key, String value){
        set(key, value);
    }

    public void setDouble(String key, double value){
        set(key, Double.toString(value));
    }

    private void set(String key, String value){
        properties.setProperty(key, value);
    }

    public boolean getBoolean(String key, boolean defaultValue){
        return Boolean.valueOf(get(key, Boolean.toString(defaultValue)));
    }

    public double getDouble(String key, double defaultValue){
        return Double.parseDouble(get(key, Double.toString(defaultValue)));
    }

    public String getString(String key, String defaultValue){
        return (get(key, defaultValue));
    }

    private String get(String key, String defaultValue){
        return properties.getProperty(key, defaultValue);
    }
}
