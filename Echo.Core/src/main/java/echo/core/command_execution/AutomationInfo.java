package echo.core.command_execution;

import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.IDriver;
import echo.core.test_abstraction.webenvironment.Parameters;

/**
 * Provides access to data required for automation.
 */
public class AutomationInfo {
    private Parameters parameters;
    private ICommandExecutionFacade commandExecutionFacade;
    private IDriver driver;
    private ILog log;

    public AutomationInfo(IDriver driver, ILog log) {
        this.driver = driver;
        this.log = log;
    }

    public AutomationInfo(ICommandExecutionFacade commandExecutionFacade, ILog log) {
        this.commandExecutionFacade = commandExecutionFacade;
        this.log = log;
    }

    public AutomationInfo(Parameters parameters, IDriver driver, ILog log) {
        this(driver, log);
        this.parameters = parameters;
    }

    /**
     * Gets the log.
     */
    public final ILog getLog() {
        return log;
    }

    public final IDriver getDriverAdapter() {
        return this.driver;
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
