package aeon.core.testabstraction.product;

import aeon.core.common.web.BrowserType;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import org.apache.commons.lang3.StringUtils;
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
            String environment;
            String protocol;
            T product = productClass.newInstance();
            IAdapterExtension plugin = loadPlugins(product);
            product.setConfiguration(plugin.getConfiguration());
            if (browserType == null) {
                browserType = BrowserType.valueOf(product.getConfig(Configuration.Keys.BROWSER_TYPE, "Chrome"));
            }
            product.getConfiguration().setBrowserType(browserType);

            log.info("Launching product on browser: " + browserType);

            product.launch(plugin);

            environment = product.getConfig(Configuration.Keys.ENVIRONMENT, "");
            if (StringUtils.isNotBlank(environment)) {
                protocol = product.getConfig(Configuration.Keys.PROTOCOL, "https");
                if (StringUtils.isBlank(protocol)) {
                    protocol = "https";
                }
                ((WebProduct) product).browser.goToUrl(protocol + "://" + environment);
            }
            return product;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends Product> T launch(Class<T> productClass) {
        return launch(productClass, null);
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
