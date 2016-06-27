package echo.core.test_abstraction.product;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.CommandExecutionFacade;
import echo.core.command_execution.consumers.DelegateRunnerFactory;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.adapters.IAdapter;
import echo.core.framework_abstraction.drivers.IDriver;
import org.joda.time.Duration;

/**
 * Created by DionnyS on 4/12/2016.
 */
public class Product {
    private AutomationInfo automationInfo;
    private Parameters parameters;
    private ILog log;
    private Configuration configuration;
    private CommandExecutionFacade commandExecutionFacade;

    public Product(Configuration configuration) {
        this.configuration = configuration;
    }

    protected Product(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
    }

    protected AutomationInfo getAutomationInfo() {
        return automationInfo;
    }

    protected void setAutomationInfo(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
    }

    protected void launch() {
        IDriver driver;
        IAdapter adapter;

        try {
            adapter = (IAdapter) configuration.getAdapter().newInstance();
            adapter = adapter.Configure(configuration);

            driver = (IDriver) configuration.getDriver().newInstance();
            driver.Configure(adapter);

            commandExecutionFacade = new CommandExecutionFacade(
                    new DelegateRunnerFactory(Duration.millis(1000), Duration.standardSeconds(10)));

            this.automationInfo = new AutomationInfo(parameters, driver, adapter, configuration.getLog());
            automationInfo.setCommandExecutionFacade(commandExecutionFacade);

            afterLaunch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Configuration getConfiguration() {
        return this.configuration;
    }

    protected void switchDriver(IAdapter adapter) {
        this.automationInfo.setAdapter(adapter);
    }

    protected void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    protected void setLog(ILog log) {
        this.log = log;
    }

    protected void afterLaunch() {
    }
}
