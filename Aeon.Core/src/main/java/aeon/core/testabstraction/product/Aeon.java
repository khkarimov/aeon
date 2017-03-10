package aeon.core.testabstraction.product;

import aeon.core.common.web.BrowserType;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.fortsoft.pf4j.DefaultPluginManager;
import ro.fortsoft.pf4j.PluginManager;

import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class Aeon {

    private static Logger log = LogManager.getLogger(Aeon.class);

    public static <T extends Product> T Launch(Class<T> productClass, BrowserType browserType) {
        try {
            T product = productClass.newInstance();
            Parameters parameters = new Parameters(); //loadParameters(product.getSettingsProvider());

            IAdapterExtension plugin = loadPlugins(product);
            product.setConfiguration(plugin.getConfiguration());

            parameters.put("browserType", browserType);
            product.getConfiguration().setBrowserType(browserType);
            product.setParameters(parameters);

            log.info("Launching product on browser: " + browserType);

            product.launch(plugin);

            return product;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T extends Product> IAdapterExtension loadPlugins(T product) throws RuntimeException {
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

        throw new RuntimeException("No valid adapter found");
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
}
