package aeon.core.command.execution;

import aeon.core.common.logging.ILog;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.testabstraction.product.Parameters;

/**
 * Provides access to data required for automation.
 */
public class AutomationInfo {
    private Parameters parameters;
    private ICommandExecutionFacade commandExecutionFacade;
    private IAdapter adapter;
    private IDriver driver;
    private ILog log;

    /**
     * Creates a new {@link AutomationInfo} instance.
     *
     * @param parameters
     * @param driver
     * @param adapter
     * @param log
     */
    public AutomationInfo(Parameters parameters, IDriver driver, IAdapter adapter, ILog log) {
        this.driver = driver;
        this.adapter = adapter;
        this.log = log;
        this.parameters = parameters;
    }

    /**
     * Gets the logger.
     *
     * @return The logger
     */
    public final ILog getLog() {
        return log;
    }

    public final IAdapter getAdapter() {
        return this.adapter;
    }

    public final void setAdapter(IAdapter adapter) {
        this.adapter = adapter;
    }

    public final ICommandExecutionFacade getCommandExecutionFacade() {
        return commandExecutionFacade;
    }

    public final void setCommandExecutionFacade(ICommandExecutionFacade value) {
        commandExecutionFacade = value;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public IDriver getDriver() {
        return driver;
    }

    public void setDriver(IDriver driver) {
        this.driver = driver;
    }
}
