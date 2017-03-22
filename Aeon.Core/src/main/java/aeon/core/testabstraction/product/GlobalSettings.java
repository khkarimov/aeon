package aeon.core.testabstraction.product;

import java.util.HashMap;

/**
 * Created By DionnyS on 4/13/2016.
 */
public class GlobalSettings implements ISettingsProvider {
    private HashMap<String, Object> settings = new HashMap<String, Object>() {{
        put("defaultTimeout", 15.0);
        put("promptUserForContinueOnExceptionDecision", false);
        put("ensureCleanEnvironment", false);
    }};

    public HashMap<String, Object> getSettings() {
        return settings;
    }
}
