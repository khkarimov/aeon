package aeon.core.testabstraction.product;

import aeon.core.common.exceptions.AeonLaunchException;
import aeon.core.common.helpers.StringUtils;
import aeon.core.extensions.AeonPluginManager;
import aeon.core.extensions.DefaultSessionIdProvider;
import aeon.core.extensions.ISessionIdProvider;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import org.pf4j.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

/**
 * Main launch class.
 **/
public class Aeon {

    private static Logger log = LoggerFactory.getLogger(Aeon.class);
    private static PluginManager pluginManager;
    private static ISessionIdProvider sessionIdProvider = new DefaultSessionIdProvider();

    private Aeon() {
        // Static classes should not be instantiated.
    }

    /**
     * Launches an environment of the desired class and with the provided properties.
     *
     * @param productClass The new environment's class.
     * @param settings     Settings to use, will override properties with the same name provided by other means.
     * @param <T>          The launch type.
     * @return A type T launch.
     */
    public static <T extends Product> T launch(Class<T> productClass, Properties settings) {
        T product = null;
        try {
            product = productClass.newInstance();
            IAdapterExtension plugin = loadPlugins(product);
            product.setConfiguration(plugin.getConfiguration());
            if (settings != null) {
                product.getConfiguration().setProperties(settings);
            }

            AeonTestExecution.beforeLaunch(product.getConfiguration());

            log.info("Launching product");

            product.launch(plugin);

            return product;
        } catch (Exception e) {
            if (product != null) {
                product.onLaunchFailure(e);
            }

            throw new AeonLaunchException(e);
        }
    }

    /**
     * Launches an environment of the desired class.
     *
     * @param productClass The new environment's class
     * @param <T>          The launch type.
     * @return A type T launch.
     */
    public static <T extends Product> T launch(Class<T> productClass) {
        return launch(productClass, null);
    }

    private static <T extends Product> IAdapterExtension loadPlugins(T product) {

        List<IAdapterExtension> extensions = getExtensions(IAdapterExtension.class);
        for (IAdapterExtension extension : extensions) {
            if (extension.getProvidedCapability() == product.getRequestedCapability()) {
                return extension;
            }
        }

        throw new AeonLaunchException("No valid adapter found. Please check " +
                "whether at least one matching adapter plugin is installed.");
    }

    /**
     * Returns the current version number of Aeon.
     *
     * @return The current version number of Aeon.
     */
    public static String getVersion() {
        return StringUtils.class.getPackage().getImplementationVersion();
    }

    /**
     * Setter for the session ID provider.
     * <p>
     * This has to be set before any plugins are called.
     *
     * @param sessionIdProvider The session ID provider to use.
     */
    public static void setSessionIdProvider(ISessionIdProvider sessionIdProvider) {
        Aeon.sessionIdProvider = sessionIdProvider;
    }

    /**
     * Returns the session ID provider.
     * <p>
     * Provides access to the session ID provider.
     *
     * @return The session ID provider in use.
     */
    public static ISessionIdProvider getSessionIdProvider() {
        return sessionIdProvider;
    }

    /**
     * Returns a PluginManager, control which plugins to be used, can be disabled with environment variable.
     *
     * @return A plugin manager with plugins to be used.
     */
    private static PluginManager getPluginManager() {
        if (pluginManager == null) {
            pluginManager = new AeonPluginManager(sessionIdProvider);

            pluginManager.loadPlugins();

            String disabledPlugins = System.getenv("AEON_DISABLED_PLUGINS");
            if (StringUtils.isNotBlank(disabledPlugins)) {
                String[] disabledPluginsArray = disabledPlugins.split(",");
                for (String disabledPlugin : disabledPluginsArray) {
                    try {
                        pluginManager.disablePlugin(disabledPlugin.trim());
                    } catch (IllegalArgumentException e) {
                        log.warn(e.getMessage());
                    }
                }
            }

            pluginManager.startPlugins();
        }

        return pluginManager;
    }

    /**
     * Retrieves a list of available extensions of an extension class.
     *
     * @param type The type of the extension class.
     * @param <T>  The class object of the extension class.
     * @return A list of available extensions of the extension class.
     */
    public static <T> List<T> getExtensions(Class<T> type) {
        return getPluginManager().getExtensions(type);
    }

    /**
     * May be called at the end of all test executions.
     * <p>
     * This method allows plugins do tear down and clean up.
     */
    public static void done() {
        AeonTestExecution.done();
    }
}
