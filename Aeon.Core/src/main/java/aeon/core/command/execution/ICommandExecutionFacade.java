package aeon.core.command.execution;

import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.commands.CommandWithReturn;

/**
 * The facade for the Command Execution layer.
 */
public interface ICommandExecutionFacade {
    /**
     * Executes a command.
     *
     * @param automationInfo The automation info.
     * @param command        The command to execute.
     */
    void execute(AutomationInfo automationInfo, Command command);

    /**
     * Executes a command.
     *
     * @param automationInfo The automation info.
     * @param command        The command to execute.
     * @return The return value of the command.
     */
    Object execute(AutomationInfo automationInfo, CommandWithReturn command);
}
