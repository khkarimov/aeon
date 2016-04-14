package echo.core.test_abstraction.driver;

import echo.core.test_abstraction.settings.ISettingsProvider;

import java.util.HashMap;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class SeleniumSettings implements ISettingsProvider {
    private HashMap<String, Object> settings = new HashMap<String, Object>() {{
        put("chromeDirectory", System.getProperty("user.dir"));
        put("ieDirectory", System.getProperty("user.dir"));
        put("enableSeleniumGrid", false);
        put("browserAcceptedLanguageCodes", "en-us");
        put("seleniumHubUri", "http://127.0.0.1:4444/wd/hub");
        put("bypassJQueryCheck", false);
        put("forceKillWebDriver", false);
        put("moveMouseToOrigin", true);
        put("mouseDragSpeed", 100);
        put("promptUserForContinueOnExceptionDecision", false);
        put("maximizeBrowser", true);
        put("useMobileUserAgent", false);
        put("proxyLocation", "");
        put("ensureCleanSession", false);
    }};

    public HashMap<String, Object> getSettings() {
        return settings;
    }
}