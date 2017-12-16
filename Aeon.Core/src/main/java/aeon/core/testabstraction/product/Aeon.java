package aeon.core.testabstraction.product;

import aeon.core.common.web.BrowserType;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;
import java.util.List;

/**
 * Main launch class.
 **/
public class Aeon {

    private static Logger log = LogManager.getLogger(Aeon.class);

    /**
     * Launches an environment of the desired class and browser type.
     * @param productClass The new environment's class.
     * @param browserType The new environment's browser.
     * @param <T> The launch type.
     * @return A type T launch.
     * @deprecated Please use Aeon.launch(Class productClass) instead and provide the browser type via the configuration}
     */
    public static <T extends Product> T launch(Class<T> productClass, BrowserType browserType) {
        T product = null;
        try {
            product = productClass.newInstance();
            IAdapterExtension plugin = loadPlugins(product);
            product.setConfiguration(plugin.getConfiguration());
            if (browserType == null) {
                browserType = BrowserType.valueOf(product.getConfig(Configuration.Keys.BROWSER, "Chrome"));
            }
            product.getConfiguration().setBrowserType(browserType);

            log.info("Launching product on browser: " + browserType);

            product.launch(plugin);

            return product;
        } catch (Exception e) {
            if (product != null) {
                product.onLaunchFailure(e);
            }

            throw new RuntimeException(e);
        }
    }

    /**
     * Launches an environment of the desired class.
     * @param productClass The new environment's class
     * @param <T> The launch type.
     * @return A type T launch.
     */
    public static <T extends Product> T launch(Class<T> productClass) {
        return launch(productClass, null);
    }

    private static <T extends Product> IAdapterExtension loadPlugins(T product) throws RuntimeException {
        PluginManager pluginManager = new DefaultPluginManager();
        pluginManager.loadPlugins();
        pluginManager.startPlugins();

        List<IAdapterExtension> extensions = pluginManager.getExtensions(IAdapterExtension.class);
        for (IAdapterExtension extension : extensions) {
            if (extension.getProvidedCapability() == product.getRequestedCapability()) {
                return extension;
            }
        }

        throw new RuntimeException("No valid adapter found");
    }
}
