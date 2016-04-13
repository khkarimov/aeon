package echo.core.command_execution;

import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.Driver;

/**
 * Provides access to data required for automation.
 */
public class AutomationInfo {
    private ICommandExecutionFacade CommandExecutionFacade;
    private Driver driver;
    private ILog log;

    public AutomationInfo(Driver driver, ILog log) {
        this.driver = driver;
        this.log = log;
    }

    public AutomationInfo(ICommandExecutionFacade commandExecutionFacade, ILog log) {
        setCommandExecutionFacade(commandExecutionFacade);
        this.log = log;
    }

    /**
     * Gets the log.
     */
    public final ILog getLog() {
        return log;
    }

    public final Driver getDriverAdapter() {
        return this.driver;
    }

    public final ICommandExecutionFacade getCommandExecutionFacade() {
        return CommandExecutionFacade;
    }

    public final void setCommandExecutionFacade(ICommandExecutionFacade value) {
        CommandExecutionFacade = value;
    }
}
