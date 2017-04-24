package aeon.selenium;

/**
 * Created by josepe on 4/11/2017.
 */
import aeon.core.common.helpers.OsCheck;
import aeon.core.framework.abstraction.drivers.AeonWebDriver;
import aeon.core.testabstraction.product.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.Properties;

public class SeleniumConfiguration extends Configuration {
    Logger log = LogManager.getLogger(SeleniumConfiguration.class);

    static class Keys extends Configuration.Keys{
        static final String ajaxWaiter = "wait_for_ajax_response";
        static final String defaultTimeout = "default_timeout";
        static final String promptUserForContinueOnExceptionDecision = "prompt_user_for_continue_on_exception_decision";
        static final String ensureCleanEnvironment = "ensure_clean_environment";
        static final String browserType = "browser_type";
        static final String enableSeleniumGrid = "enable_selenium_grid";
        static final String language = "language";
        static final String moveMouseToOrigin = "move_mouse_to_origin";
        static final String maximizeBrowser = "maximize_browser";
        static final String useMobileUserAgent = "use_mobile_user_agent";
        static final String proxyLocation = "proxy_location";
        static final String EnsureCleanEnvironment = "ensure_clean_environment";
        static final String seleniumHubUrl = "selenium_hub_url";
        static final String chromeDirectory = "chrome_directory";
        static final String ieDirectory = "ie_directory";
        static final String marionetteDirectory = "marionette_directory";
        static final String edgeDirectory = "edge_directory";
        static final String chromeBinary = "chrome_binary";
        static final String firefoxBinary = "firefox_binary";
    };

    public SeleniumConfiguration() throws IOException, IllegalAccessException {
        super(AeonWebDriver.class, SeleniumAdapter.class);
    }

    @Override
    public void loadPluginSettings() throws IOException {
       try(InputStream  in = SeleniumConfiguration.class.getResourceAsStream("/selenium.properties")){
            properties.load(in);
       } catch (IOException e) {
           log.error("selenium.properties resource could not be read");
           throw e;
       }
        setBrowserDirectories();
    }

    @Override
    public void loadEnvValues() throws IllegalAccessException {
        Keys keysInstance = new SeleniumConfiguration.Keys();
        for(Field key : SeleniumConfiguration.Keys.class.getDeclaredFields()){
            key.setAccessible(true);
            String keyValue =  key.get(keysInstance).toString();
            String environmentValue = System.getenv("aeon." + keyValue);
            if(environmentValue != null)
                properties.setProperty(keyValue, environmentValue);
        }
    }

    private void setBrowserDirectories() {
        String output = System.getProperty("user.dir");
        properties.setProperty(Keys.ieDirectory , output + "/lib/Windows/IEDriverServer.exe");
        properties.setProperty(Keys.edgeDirectory , output + "/lib/Windows/MicrosoftWebDriver.exe");
        switch (OsCheck.getOperatingSystemType()) {
            case Windows:
                properties.setProperty(Keys.marionetteDirectory, output + "/lib/Windows/geckodriver.exe");
                properties.setProperty(Keys.chromeDirectory, output + "/lib/Windows/chromedriver.exe");
                break;
            case MacOS:
                properties.setProperty(Keys.marionetteDirectory, output + "/lib/MacOS/geckodriver");
                properties.setProperty(Keys.chromeDirectory, output + "/lib/MacOS/chromedriver");
                break;
            case Linux:
                properties.setProperty(Keys.marionetteDirectory, output + "/lib/Linux/geckodriver");
                properties.setProperty(Keys.chromeDirectory, output + "/lib/Linux/chromedriver");
                break;
            default:
                throw new IllegalStateException("Unsupported Operating System");
        }
    }
}
