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
 * Created by DionnyS on 4/21/2016.
 */
public class WebProduct extends Product {

    public Browser browser;

    public WebProduct() {

    }

    protected WebProduct(AutomationInfo automationInfo) {
        browser = new Browser(automationInfo);
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
        long timeout = (long) configuration.getDouble(Configuration.Keys.timeout, 10);
        long ajaxTimeout = (long) configuration.getDouble(Configuration.Keys.ajaxTimeout, 20);


        commandExecutionFacade = new WebCommandExecutionFacade(
                new DelegateRunnerFactory(Duration.millis(250), Duration.standardSeconds(timeout)), new AjaxWaiter(driver, Duration.standardSeconds(ajaxTimeout)));

        this.automationInfo = new AutomationInfo(configuration, driver, adapter);
        automationInfo.setCommandExecutionFacade(commandExecutionFacade);

        afterLaunch();
    }

    @Override
    protected void afterLaunch() {
        browser = new Browser(getAutomationInfo());
    }

    /**
     * Disables the ajax waiter.
     */
    public void disableAjaxWaiting() {
        configuration.setBoolean(Configuration.Keys.waitForAjaxResponses, false);
    }

    /**
     * Enables the ajax waiter.
     */
    public void enableAjaxWaiting() {
        configuration.setBoolean(Configuration.Keys.waitForAjaxResponses, true);
    }
}
