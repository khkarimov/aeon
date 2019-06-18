package com.ultimatesoftware.aeon.core.testabstraction.product;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.Capabilities;
import com.ultimatesoftware.aeon.core.common.Capability;
import com.ultimatesoftware.aeon.core.common.exceptions.AeonLaunchException;
import com.ultimatesoftware.aeon.core.common.exceptions.AeonSinglePluginRequestedException;
import com.ultimatesoftware.aeon.core.common.helpers.StringUtils;
import com.ultimatesoftware.aeon.core.extensions.AeonPluginManager;
import com.ultimatesoftware.aeon.core.extensions.DefaultSessionIdProvider;
import com.ultimatesoftware.aeon.core.extensions.ISessionIdProvider;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapter;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapterExtension;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
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

        Capability capabilityAnnotation = productClass.getAnnotation(Capability.class);
        if (capabilityAnnotation == null) {
            throw new AeonLaunchException("Product class is not requesting a capability by carrying a 'Capability' annotation");
        }

        Capabilities requestedCapability = capabilityAnnotation.value();
        IAdapterExtension plugin = findAdapterPlugin(requestedCapability);

        T product = null;
        try {
            Configuration configuration = plugin.getConfiguration();
            if (settings != null) {
                configuration.setProperties(settings);
            }

            AeonTestExecution.beforeLaunch(configuration);

            log.info("Launching product");

            IDriver driver;
            IAdapter adapter;

            adapter = plugin.createAdapter(configuration);

            driver = (IDriver) configuration.getDriver().newInstance();
            driver.configure(adapter, configuration);


            AutomationInfo automationInfo = new AutomationInfo(configuration, driver, adapter);

            product = productClass
                    .getDeclaredConstructor(AutomationInfo.class)
                    .newInstance(automationInfo);

            product.afterLaunch();

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
     * Expects that exactly one extension of the requested type is available
     * and returns it.
     * <p>
     * Throws an instance of {@link AeonSinglePluginRequestedException}
     * if zero or multiple extensions of the requested type are available.
     *
     * @param type The type of the extension class.
     * @param <T>  The class object of the extension class.
     * @return The requested extension.
     */
    public static <T> T getExtension(Class<T> type) {
        List<T> extensions = getExtensions(type);

        int foundExtensions = extensions.size();
        if (foundExtensions != 1) {
            throw new AeonSinglePluginRequestedException(type.getSimpleName(), foundExtensions);
        }

        return extensions.get(0);
    }

    /**
     * Setter for the plugin manager.
     *
     * @param pluginManager The plugin manager to use.
     */
    public static void setPluginManager(PluginManager pluginManager) {
        Aeon.pluginManager = pluginManager;
    }

    /**
     * May be called at the end of all test executions.
     * <p>
     * This method allows plugins do tear down and clean up.
     */
    public static void done() {
        AeonTestExecution.done();
    }

    private static IAdapterExtension findAdapterPlugin(Capabilities requestedCapability) {

        List<IAdapterExtension> extensions = getExtensions(IAdapterExtension.class);
        for (IAdapterExtension extension : extensions) {
            if (extension.getProvidedCapability() == requestedCapability) {
                return extension;
            }
        }

        throw new AeonLaunchException("No valid adapter found. Please check " +
                "whether at least one matching adapter plugin is installed.");
    }

    /**
     * Returns a PluginManager which controls which plugins can be used.
     * <p>
     * Plugins can be disabled with an environment variable.
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
}
