package aeon.core.testabstraction.product;

import aeon.core.common.web.BrowserType;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.fortsoft.pf4j.DefaultPluginManager;
import ro.fortsoft.pf4j.PluginManager;

import java.util.List;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class Aeon {

    private static Logger log = LogManager.getLogger(Aeon.class);

    public static <T extends Product> T launch(Class<T> productClass, BrowserType browserType) {
        try {
            T product = productClass.newInstance();
            IAdapterExtension plugin = loadPlugins(product);
            product.setConfiguration(plugin.getConfiguration());
            product.getConfiguration().setBrowserType(browserType);

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
}
