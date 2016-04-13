package echo.core.test_abstraction.context;

import echo.core.common.BrowserType;
import echo.core.common.logging.ILog;
import echo.core.common.logging.Log;
import echo.core.test_abstraction.settings.GlobalSettings;
import echo.core.test_abstraction.settings.ISettingsProvider;
import echo.core.test_abstraction.webenvironment.Parameters;

import java.util.ResourceBundle;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class Echo {
    public static <T extends Product> T Launch(Class<T> tClass, BrowserType browserType) {
        try {
            T product = tClass.newInstance();
            Parameters parameters = loadParameters(product.getSettingsProvider());
            parameters.put("browserType", browserType);

            product.setParameters(parameters);
            product.setLog(createLogger());
            product.launch();

            return product;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Parameters loadParameters(ISettingsProvider settingsProvider) {
        Parameters parameters = new Parameters();

        // Load global settings.
        parameters.load(new GlobalSettings().getSettings());

        // Load driver-specific settings.
        parameters.load(settingsProvider.getSettings());

        // Load user settings.
        parameters.load(ResourceBundle.getBundle("config"));

        return parameters;
    }

    private static ILog createLogger() {
        return new Log();
    }
}