package echo.core.test_abstraction.context;

import echo.core.common.BrowserType;
import echo.core.common.logging.ILog;
import echo.core.common.logging.Log;
import echo.core.test_abstraction.settings.GlobalSettings;
import echo.core.test_abstraction.settings.ISettingsProvider;
import echo.core.test_abstraction.webenvironment.Parameters;

import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class Echo {
    public static <T extends Product> T Launch(Class<T> productClass, BrowserType browserType) {
        try {
            T product = productClass.newInstance();
            Parameters parameters = new Parameters(); //loadParameters(product.getSettingsProvider());

            parameters.put("browserType", browserType);
            product.getConfiguration().setBrowserType(browserType);
            product.getConfiguration().setLog(createLogger());
            product.setParameters(parameters);

            product.getConfiguration().getLog().Info(UUID.randomUUID(), "Launching product...");

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