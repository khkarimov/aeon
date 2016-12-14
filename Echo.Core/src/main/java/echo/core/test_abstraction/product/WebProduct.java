package echo.core.test_abstraction.product;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.WebCommandExecutionFacade;
import echo.core.command_execution.consumers.DelegateRunnerFactory;
import echo.core.common.Capability;
import echo.core.common.helpers.AjaxWaiter;
import echo.core.framework_abstraction.adapters.IAdapter;
import echo.core.framework_abstraction.adapters.IAdapterExtension;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;
import echo.core.test_abstraction.models.Browser;
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
    protected void launch(IAdapterExtension plugin) {
        IWebDriver driver;
        IAdapter adapter;

        try {
            adapter = createAdapter(plugin);

            driver = (IWebDriver) configuration.getDriver().newInstance();
            driver.Configure(adapter);

            commandExecutionFacade = new WebCommandExecutionFacade(
                    new DelegateRunnerFactory(Duration.millis(250), Duration.millis(10000)), new AjaxWaiter(driver, Duration.millis(20000)));

            this.automationInfo = new AutomationInfo(parameters, driver, adapter, configuration.getLog());
            automationInfo.setCommandExecutionFacade(commandExecutionFacade);

            afterLaunch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected WebProduct(AutomationInfo automationInfo) {
        browser = new Browser(automationInfo);
    }

    @Override
    protected void afterLaunch() {
        browser = new Browser(getAutomationInfo());
    }
}
