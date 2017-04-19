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

public class SeleniumConfiguration extends Configuration {
    Logger log = LogManager.getLogger(SeleniumConfiguration.class);

    static class Keys extends Configuration.Keys{
        public static final String ajaxWaiter = "wait_for_ajax_response";
        public static final String defaultTimeout = "default_timeout";
        public static final String promptUserForContinueOnExceptionDecision = "prompt_user_for_continue_on_exception_decision";
        public static final String ensureCleanEnvironment = "ensure_clean_environment";
        public static final String browserType = "browser_type";
        public static final String PromptUserForContinueOnExceptionDecision = "prompt_user_for_continue_on_exception_decision";
        public static final String enableSeleniumGrid = "enable_selenium_grid";
        public static final String language = "language";
        public static final String moveMouseToOrigin = "move_mouse_to_origin";
        public static final String maximizeBrowser = "maximize_browser";
        public static final String useMobileUserAgent = "use_mobile_user_agent";
        public static final String proxyLocation = "proxy_location";
        public static final String EnsureCleanEnvironment = "ensure_clean_environment";
        public static final String seleniumHubUrl = "selenium_hub_url";
        public static final String chromeDirectory = "chrome_directory";
        public static final String ieDirectory = "ie_directory";
        public static final String marionetteDirectory = "marionette_directory";
        public static final String edgeDirectory = "edge_directory";
        public static final String chromeBinary = "chrome_binary";
        public static final String firefoxBinary = "firefox_binary";
    };

    public SeleniumConfiguration() throws IOException, IllegalAccessException {
        super(AeonWebDriver.class, SeleniumAdapter.class, SeleniumConfiguration.Keys.class.getDeclaredFields());
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
