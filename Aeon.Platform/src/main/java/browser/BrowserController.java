package browser;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.WebCommandExecutionFacade;
import aeon.core.command.execution.commands.CloseCommand;
import aeon.core.command.execution.commands.QuitCommand;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

@RestController("/")
public class BrowserController {

    private Configuration configuration;
    private AutomationInfo automationInfo;
    private WebCommandExecutionFacade commandExecutionFacade;
    private long timeout;
    private long throttle;
    private long ajaxTimeout;
    private DelegateRunnerFactory delegateRunnerFactory;
    private AjaxWaiter ajaxWaiter;

    private static <T extends Product> IAdapterExtension loadPlugins() throws RuntimeException {
        List<IAdapterExtension> extensions = Aeon.getExtensions(IAdapterExtension.class);
        for (IAdapterExtension extension : extensions) {
            if (extension.getProvidedCapability() == Capability.WEB) {
                return extension;
            }
        }

        throw new RuntimeException("No valid adapter found");
    }

    private IAdapter createAdapter(IAdapterExtension plugin) {
        return plugin.createAdapter(configuration);
    }

    @GetMapping("launch")
    public void launch() throws InstantiationException, IllegalAccessException, IOException {
        IAdapterExtension plugin = loadPlugins();

        IDriver driver;
        IAdapter adapter;

        configuration = plugin.getConfiguration();
        adapter = createAdapter(plugin);

        driver = (IDriver)configuration.getDriver().newInstance();
        driver.configure(adapter, configuration);

        this.automationInfo = new AutomationInfo(configuration, driver, adapter);

        System.out.println("launch");
    }

    @GetMapping("close")
    public void close() {
        timeout = (long)configuration.getDouble(Configuration.Keys.TIMEOUT, 10);
        throttle = (long)configuration.getDouble(Configuration.Keys.THROTTLE, 100);
        ajaxTimeout = (long)configuration.getDouble(WebConfiguration.Keys.AJAX_TIMEOUT, 20);

        delegateRunnerFactory = new DelegateRunnerFactory(Duration.ofMillis(throttle), Duration.ofSeconds(timeout));
        ajaxWaiter = new AjaxWaiter(this.automationInfo.getDriver(), Duration.ofSeconds(ajaxTimeout));

        commandExecutionFacade = new WebCommandExecutionFacade(delegateRunnerFactory, ajaxWaiter);

        automationInfo.setCommandExecutionFacade(commandExecutionFacade);
        commandExecutionFacade.execute(automationInfo, new QuitCommand());

        System.out.println("close");
    }
}
