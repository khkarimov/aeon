package echo.core.command_execution;

import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.IAdapter;
import echo.core.framework_abstraction.IDriver;
import echo.core.test_abstraction.webenvironment.Parameters;

/**
 * Provides access to data required for automation.
 */
public class AutomationInfo {
    private Parameters parameters;
    private ICommandExecutionFacade commandExecutionFacade;
    private IAdapter adapter;
    private IDriver driver;
    private ILog log;

    public AutomationInfo(Parameters parameters, IDriver driver, IAdapter adapter, ILog log) {
        this.driver = driver;
        this.adapter = adapter;
        this.log = log;
        this.parameters = parameters;
    }

    /**
     * Gets the log.
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
}
