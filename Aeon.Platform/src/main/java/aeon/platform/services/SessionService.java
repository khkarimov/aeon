package aeon.platform.services;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.WebCommandExecutionFacade;
import aeon.core.command.execution.consumers.DelegateRunnerFactory;
import aeon.core.common.Capability;
import aeon.core.common.helpers.AjaxWaiter;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.testabstraction.product.Configuration;
import aeon.core.testabstraction.product.Product;
import aeon.core.testabstraction.product.WebConfiguration;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;

/**
 * Service for sessions.
 */
@Service
public class SessionService {

    private Supplier<List<IAdapterExtension>> supplier;

    /**
     * Constructs a Session Service.
     * @param adapterExtensionsSupplier Adapter extensions supplier
     */
    @Autowired
    public SessionService(Supplier<List<IAdapterExtension>> adapterExtensionsSupplier) {
        this.supplier = adapterExtensionsSupplier;
    }

    private <T extends Product> IAdapterExtension loadPlugins() throws RuntimeException {
        List<IAdapterExtension> extensions = supplier.get();

        for (int i = 0; i < extensions.size(); i++) {
        //for (IAdapterExtension extension : extensions) {
            IAdapterExtension extension = extensions.get(i);

            if (extension.getProvidedCapability() == Capability.WEB) {
                return extension;
            }
        }

        throw new RuntimeException("No valid adapter found");
    }

    private IAdapter createAdapter(IAdapterExtension plugin, Configuration configuration) {
        return plugin.createAdapter(configuration);
    }

    /**
     * Creates a new session ID.
     * @return New ObjectID
     */
    public ObjectId createSessionId() {
        return new ObjectId();
    }

    /**
     * Sets up the automation info.
     * @param settings Session settings
     * @return Automation info
     * @throws Exception Throws an exception if an error occurs
     */
    public AutomationInfo setUpAutomationInfo(Properties settings) throws Exception {
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

    /**
     * Sets up the command execution facade.
     * @param automationInfo Automation info
     * @return Command execution facade
     */
    public WebCommandExecutionFacade setUpCommandExecutionFacade(AutomationInfo automationInfo) {
        Configuration configuration = automationInfo.getConfiguration();

        long timeout = (long) configuration.getDouble(Configuration.Keys.TIMEOUT, 10);
        long throttle = (long) configuration.getDouble(Configuration.Keys.THROTTLE, 100);
        long ajaxTimeout = (long) configuration.getDouble(WebConfiguration.Keys.AJAX_TIMEOUT, 20);

        DelegateRunnerFactory delegateRunnerFactory = new DelegateRunnerFactory(Duration.ofMillis(throttle), Duration.ofSeconds(timeout));
        AjaxWaiter ajaxWaiter = new AjaxWaiter(automationInfo.getDriver(), Duration.ofSeconds(ajaxTimeout));

        WebCommandExecutionFacade commandExecutionFacade = new WebCommandExecutionFacade(delegateRunnerFactory, ajaxWaiter);
        automationInfo.setCommandExecutionFacade(commandExecutionFacade);

        return commandExecutionFacade;
    }
}
