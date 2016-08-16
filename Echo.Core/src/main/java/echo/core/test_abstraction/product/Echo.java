package echo.core.test_abstraction.product;

import echo.core.common.logging.ILog;
import echo.core.common.logging.Log;
import echo.core.common.web.BrowserType;
import echo.core.framework_abstraction.adapters.IAdapterExtension;
import ro.fortsoft.pf4j.DefaultPluginManager;
import ro.fortsoft.pf4j.PluginManager;

import java.util.List;
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

            IAdapterExtension plugin = loadPlugins(product);
            product.setConfiguration(plugin.getConfiguration());

            parameters.put("browserType", browserType);
            product.getConfiguration().setBrowserType(browserType);
            product.getConfiguration().setLog(createLogger());
            product.setParameters(parameters);

            product.getConfiguration().getLog().Info(UUID.randomUUID(), "Launching product on Browser: " + browserType);

            product.launch(plugin);

            return product;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T extends Product> IAdapterExtension loadPlugins(T product) throws Exception {
        PluginManager pluginManager = new DefaultPluginManager();
        pluginManager.loadPlugins();
        pluginManager.startPlugins();
        System.out.println(pluginManager.getExtensions(IAdapterExtension.class));

        System.out.println("plugins dir: " + System.getProperty("pf4j.pluginsDir", "plugins"));

        List<IAdapterExtension> extensions = pluginManager.getExtensions(IAdapterExtension.class);
        for (IAdapterExtension extension : extensions) {
            if (extension.getProvidedCapability() == product.getRequestedCapability()) {
                return extension;
            }
        }

        throw new Exception("No valid adapter found");
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