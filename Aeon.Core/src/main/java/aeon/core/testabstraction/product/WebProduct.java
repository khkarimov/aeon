package aeon.core.testabstraction.product;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.WebCommandExecutionFacade;
import aeon.core.command.execution.consumers.DelegateRunnerFactory;
import aeon.core.common.Capability;
import aeon.core.common.helpers.AjaxWaiter;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import aeon.core.testabstraction.models.Browser;
import org.joda.time.Duration;

/**
 * Created By DionnyS on 4/21/2016.
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
        driver.configure(adapter);

        commandExecutionFacade = new WebCommandExecutionFacade(
                new DelegateRunnerFactory(Duration.millis(250), Duration.millis(10000)), new AjaxWaiter(driver, Duration.millis(20000)));

        this.automationInfo = new AutomationInfo(parameters, driver, adapter);
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
