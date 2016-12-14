package echo.core.test_abstraction.product;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.CommandExecutionFacade;
import echo.core.command_execution.WebCommandExecutionFacade;
import echo.core.command_execution.consumers.DelegateRunnerFactory;
import echo.core.common.Capability;
import echo.core.common.helpers.AjaxWaiter;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.adapters.IAdapter;
import echo.core.framework_abstraction.adapters.IAdapterExtension;
import echo.core.framework_abstraction.drivers.IDriver;
import org.joda.time.Duration;

/**
 * Created by DionnyS on 4/12/2016.
 */
public abstract class Product {
    protected AutomationInfo automationInfo;
    protected Parameters parameters;
    protected ILog log;
    protected Configuration configuration;
    protected WebCommandExecutionFacade commandExecutionFacade;

    public Product() {

    }

    protected Product(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
    }

    public abstract Capability getRequestedCapability();

    protected AutomationInfo getAutomationInfo() {
        return automationInfo;
    }

    protected void setAutomationInfo(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
    }

    protected abstract void launch(IAdapterExtension plugin);

    protected IAdapter createAdapter(IAdapterExtension plugin) {
        return plugin.createAdapter(configuration);
    }

    protected Configuration getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
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
