package echo.core.test_abstraction.settings;

import java.util.HashMap;

/**
 * Created by DionnyS on 4/13/2016.
 */
public interface ISettingsProvider {
    HashMap<String, Object> getSettings();
}
