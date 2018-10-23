package aeon.platform.factories;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.common.Capability;
import aeon.core.common.exceptions.UnableToCreateDriverException;
import aeon.core.extensions.IProductTypeExtension;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.testabstraction.product.Configuration;
import aeon.core.testabstraction.product.Product;
import aeon.platform.session.ISession;
import aeon.platform.session.Session;
import org.pf4j.Extension;

import javax.inject.Inject;

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
     * @param adapterExtensionsSupplier Adapter extensions supplier
     * @param productTypeExtensionsSupplier Product type extensions supplier
     */
    @Inject
    public SessionFactory(Supplier<List<IAdapterExtension>> adapterExtensionsSupplier, Supplier<List<IProductTypeExtension>> productTypeExtensionsSupplier) {
        this.adapterExtensionsSupplier = adapterExtensionsSupplier;
        this.productTypeExtensionsSupplier = productTypeExtensionsSupplier;
    }

    @Override
    public ISession getSession(Map settings) throws Exception {
        Properties properties = null;

        if (settings != null) {
            properties = new Properties();
            properties.putAll(settings);
        }

        AutomationInfo automationInfo = setUpAutomationInfo(properties);
        ICommandExecutionFacade commandExecutionFacade = setUpCommandExecutionFacade(automationInfo);

        return new Session(automationInfo, commandExecutionFacade, productTypeExtensionsSupplier);
    }

    private <T extends Product> IAdapterExtension loadPlugins() throws RuntimeException {
        List<IAdapterExtension> extensions = adapterExtensionsSupplier.get();

        for (IAdapterExtension extension : extensions) {
            if (extension.getProvidedCapability() == Capability.WEB) {
                return extension;
            }
        }

        throw new RuntimeException("No valid adapter found");
    }

    private IAdapter createAdapter(IAdapterExtension plugin, Configuration configuration) {
        return plugin.createAdapter(configuration);
    }

    private AutomationInfo setUpAutomationInfo(Properties settings) throws Exception {
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

    private ICommandExecutionFacade setUpCommandExecutionFacade(AutomationInfo automationInfo) throws Exception {
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
