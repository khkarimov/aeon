package aeon.selenium;

/**
 * Created by josepe on 4/11/2017.
 */
import aeon.core.common.helpers.OsCheck;
import aeon.core.testabstraction.product.ParametersBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SeleniumParameters extends ParametersBase {
    Logger log = LogManager.getLogger(SeleniumParameters.class);

    public enum Keys implements IKeys{
        prompt_user_for_continue_on_exception_decision,
        enable_selenium_grid,
        language,
        move_mouse_to_origin,
        maximize_browser,
        use_mobile_user_agent,
        proxy_location,
        ensure_clean_environment,
        selenium_hub_url,
        chrome_directory,
        ie_directory,
        marionette_directory,
        edge_directory,
        chrome_binary,
        firefox_binary
    };
    public SeleniumParameters() {
        properties = new Properties();
        InputStream in = null;
        setBrowserDirectories();

        try{
            in = SeleniumParameters.class.getResourceAsStream("/selenium.properties");
            properties.load(in);
        } catch(IOException e){
            log.error("Selenium.properties not found");
            e.printStackTrace();
        } finally{
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        try{
            in = SeleniumParameters.class.getResourceAsStream("/config.properties");
            if(in != null)
                properties.load(in);
            else
                System.out.println("No config file in use");
        } catch(IOException e){
        } finally{
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        for(Keys key : Keys.values()){
            String environmentValue = System.getenv("aeon." + key.toString());
            if(environmentValue != null)
                properties.setProperty(key.toString(), environmentValue);
        }
    }

    private void setBrowserDirectories() {
        String output = System.getProperty("user.dir");
        properties.setProperty(Keys.ie_directory.toString() , output + "/lib/Windows/IEDriverServer.exe");
        properties.setProperty(Keys.edge_directory.toString() , output + "/lib/Windows/MicrosoftWebDriver.exe");
        switch (OsCheck.getOperatingSystemType()) {
            case Windows:
                properties.setProperty(Keys.marionette_directory.toString(), output + "/lib/Windows/geckodriver.exe");
                properties.setProperty(Keys.chrome_directory.toString(), output + "/lib/Windows/chromedriver.exe");
                break;
            case MacOS:
                properties.setProperty(Keys.marionette_directory.toString(), output + "/lib/MacOS/geckodriver");
                properties.setProperty(Keys.chrome_directory.toString(), output + "/lib/MacOS/chromedriver");
                break;
            case Linux:
                properties.setProperty(Keys.marionette_directory.toString(), output + "/lib/Linux/geckodriver");
                properties.setProperty(Keys.chrome_directory.toString(), output + "/lib/Linux/chromedriver");
                break;
            default:
                throw new IllegalStateException("Unsupported Operating System");
        }
    }
}
