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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SeleniumConfiguration extends Configuration {
    Logger log = LogManager.getLogger(SeleniumConfiguration.class);

    static class Keys extends Configuration.Keys{
        public static final String enableSeleniumGrid = "aeon.enable_selenium_grid";
        public static final String language = "aeon.language";
        public static final String moveMouseToOrigin = "aeon.move_mouse_to_origin";
        public static final String maximizeBrowser = "aeon.maximize_browser";
        public static final String useMobileUserAgent = "aeon.use_mobile_user_agent";
        public static final String proxyLocation = "aeon.proxy_location";
        public static final String seleniumHubUrl = "aeon.selenium_hub_url";
        public static final String chromeDirectory = "aeon.chrome_directory";
        public static final String ieDirectory = "aeon.ie_directory";
        public static final String marionetteDirectory = "aeon.marionette_directory";
        public static final String edgeDirectory = "aeon.edge_directory";
        public static final String chromeBinary = "aeon.chrome_binary";
        public static final String firefoxBinary = "aeon.firefox_binary";
    };

    @Override
    protected List<Field> getConfigurationFields(){
        List<Field> keys = new ArrayList<>();
        keys.addAll(Arrays.asList(SeleniumConfiguration.Keys.class.getDeclaredFields()));
        return keys;
    }

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
