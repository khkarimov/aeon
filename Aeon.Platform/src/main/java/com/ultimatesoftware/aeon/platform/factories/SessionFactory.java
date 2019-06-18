package com.ultimatesoftware.aeon.platform.factories;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.command.execution.ICommandExecutionFacade;
import com.ultimatesoftware.aeon.core.common.Capabilities;
import com.ultimatesoftware.aeon.core.common.exceptions.AeonLaunchException;
import com.ultimatesoftware.aeon.core.common.exceptions.UnableToCreateDriverException;
import com.ultimatesoftware.aeon.core.extensions.IProductTypeExtension;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapter;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapterExtension;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import com.ultimatesoftware.aeon.platform.session.ISession;
import com.ultimatesoftware.aeon.platform.session.Session;
import org.pf4j.Extension;

import javax.inject.Inject;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

/**
 * Factory for session.
 */
@Extension
public class SessionFactory implements ISessionFactory {

    private Supplier<List<IAdapterExtension>> adapterExtensionsSupplier;
    private Supplier<List<IProductTypeExtension>> productTypeExtensionsSupplier;

    /**
     * Constructs a Session Factory.
     *
     * @param adapterExtensionsSupplier     Adapter extensions supplier
     * @param productTypeExtensionsSupplier Product type extensions supplier
     */
    @Inject
    public SessionFactory(Supplier<List<IAdapterExtension>> adapterExtensionsSupplier, Supplier<List<IProductTypeExtension>> productTypeExtensionsSupplier) {
        this.adapterExtensionsSupplier = adapterExtensionsSupplier;
        this.productTypeExtensionsSupplier = productTypeExtensionsSupplier;
    }

    @Override
    public ISession getSession(Map settings) throws IllegalAccessException, IOException, InstantiationException {
        Properties properties = null;

        if (settings != null) {
            properties = new Properties();
            properties.putAll(settings);
        }

        AutomationInfo automationInfo = setUpAutomationInfo(properties);
        ICommandExecutionFacade commandExecutionFacade = setUpCommandExecutionFacade(automationInfo);

        return new Session(automationInfo, commandExecutionFacade, productTypeExtensionsSupplier);
    }

    private IAdapterExtension loadPlugins() {
        List<IAdapterExtension> extensions = adapterExtensionsSupplier.get();

        for (IAdapterExtension extension : extensions) {
            if (extension.getProvidedCapability() == Capabilities.WEB) {
                return extension;
            }
        }

        throw new AeonLaunchException("No valid adapter found. Please check " +
                "whether at least one matching adapter plugin is installed.");
    }

    private IAdapter createAdapter(IAdapterExtension plugin, Configuration configuration) {
        return plugin.createAdapter(configuration);
    }

    private AutomationInfo setUpAutomationInfo(Properties settings) throws IOException, IllegalAccessException, InstantiationException {
        IAdapterExtension plugin = loadPlugins();

        IDriver driver;
        IAdapter adapter;

        Configuration configuration = plugin.getConfiguration();

        if (settings != null) {
            configuration.setProperties(settings);
        }

        adapter = createAdapter(plugin, configuration);

        driver = (IDriver) configuration.getDriver().newInstance();
        driver.configure(adapter, configuration);

        return new AutomationInfo(configuration, driver, adapter);
    }

    private ICommandExecutionFacade setUpCommandExecutionFacade(AutomationInfo automationInfo) {
        ICommandExecutionFacade commandExecutionFacade;
        List<IProductTypeExtension> extensions = productTypeExtensionsSupplier.get();

        for (IProductTypeExtension extension : extensions) {
            commandExecutionFacade = extension.createCommandExecutionFacade(automationInfo);

            if (commandExecutionFacade != null) {
                return commandExecutionFacade;
            }
        }

        throw new UnableToCreateDriverException("Could not create CommandExecutionFacade.");
    }
}
