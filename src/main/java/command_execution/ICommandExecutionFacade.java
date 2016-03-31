package command_execution;

import command_execution.AutomationInfo;
import command_execution.commands.Command;

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

//    /**
//     * Executes a command.
//     *
//     * @param automationInfo The automation info.
//     * @param command        The command to execute.
//     * @return The return value of the command.
//     */
//    Object Execute(AutomationInfo automationInfo, CommandWithReturn command);
}
