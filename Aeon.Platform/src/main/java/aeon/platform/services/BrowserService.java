package aeon.platform.services;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.WebCommandExecutionFacade;
import aeon.core.command.execution.consumers.DelegateRunnerFactory;
import aeon.core.common.Capability;
import aeon.core.common.helpers.AjaxWaiter;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.testabstraction.product.Aeon;
import aeon.core.testabstraction.product.Configuration;
import aeon.core.testabstraction.product.Product;
import aeon.core.testabstraction.product.WebConfiguration;
import aeon.platform.models.CreateSessionBody;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

/**
 * Browser helper class.
 */
@Service
public class BrowserService {

    private <T extends Product> IAdapterExtension loadPlugins() throws RuntimeException {
        List<IAdapterExtension> extensions = Aeon.getExtensions(IAdapterExtension.class);

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

    /**
     * Creates a new session ID.
     * @return New ObjectID
     */
    public ObjectId createSessionId() {
        return new ObjectId();
    }

    /**
     * Sets up the automation info.
     * @param body Session settings
     * @return Automation info
     * @throws Exception Throws an exception if an error occurs
     */
    public AutomationInfo setUpAutomationInfo(CreateSessionBody body) throws Exception {
        IAdapterExtension plugin = loadPlugins();

        IDriver driver;
        IAdapter adapter;

        Configuration configuration = plugin.getConfiguration();

        if (body.getSettings() != null) {
            configuration.setProperties(body.getSettings());
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
