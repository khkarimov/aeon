package aeon.core.test_abstraction.product;

import aeon.core.command_execution.AutomationInfo;
import aeon.core.command_execution.WebCommandExecutionFacade;
import aeon.core.command_execution.consumers.DelegateRunnerFactory;
import aeon.core.common.Capability;
import aeon.core.common.helpers.AjaxWaiter;
import aeon.core.framework_abstraction.adapters.IAdapter;
import aeon.core.framework_abstraction.adapters.IAdapterExtension;
import aeon.core.framework_abstraction.drivers.IWebDriver;
import aeon.core.test_abstraction.models.Browser;
import org.joda.time.Duration;

/**
 * Created by DionnyS on 4/21/2016.
 */
public class WebProduct extends Product {
    public Browser browser;

    public WebProduct() {

    }

    @Override
    public Capability getRequestedCapability() {
        return Capability.WEB;
    }

    @Override
    protected void launch(IAdapterExtension plugin) throws InstantiationException, IllegalAccessException {
        IWebDriver driver;
        IAdapter adapter;

        adapter = createAdapter(plugin);

        driver = (IWebDriver) configuration.getDriver().newInstance();
        driver.Configure(adapter);

        commandExecutionFacade = new WebCommandExecutionFacade(
                new DelegateRunnerFactory(Duration.millis(250), Duration.millis(10000)), new AjaxWaiter(driver, Duration.millis(20000)));

        this.automationInfo = new AutomationInfo(parameters, driver, adapter, configuration.getLog());
        automationInfo.setCommandExecutionFacade(commandExecutionFacade);

        afterLaunch();
    }

    protected WebProduct(AutomationInfo automationInfo) {
        browser = new Browser(automationInfo);
    }

    @Override
    protected void afterLaunch() {
        browser = new Browser(getAutomationInfo());
    }
}
