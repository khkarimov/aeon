package aeon.core.command_execution;

import aeon.core.command_execution.commands.Command;
import aeon.core.command_execution.commands.CommandWithReturn;

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
    void Execute(AutomationInfo automationInfo, Command command);

    /**
     * Executes a command.
     *
     * @param automationInfo The automation info.
     * @param command        The command to execute.
     * @return The return value of the command.
     */
    Object Execute(AutomationInfo automationInfo, CommandWithReturn command);
}
