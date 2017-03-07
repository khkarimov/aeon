package aeon.core.command_execution;

import aeon.core.command_execution.consumers.interfaces.IDelegateRunnerFactory;
import aeon.core.command_execution.commands.Command;
import aeon.core.command_execution.commands.CommandWithReturn;

/**
 * The facade for the Command Execution layer.
 */
public final class CommandExecutionFacade implements ICommandExecutionFacade {
    private IDelegateRunnerFactory delegateRunnerFactory;

    /**
     * Initializes a new instance of the {@link CommandExecutionFacade} class.
     *
     * @param delegateRunnerFactory The delegate runner factory.
     */
    public CommandExecutionFacade(IDelegateRunnerFactory delegateRunnerFactory) {
        this.delegateRunnerFactory = delegateRunnerFactory;
    }

    /**
     * Executes a command.
     *
     * @param automationInfo The automation info.
     * @param command        The command to execute.
     */
    public void Execute(AutomationInfo automationInfo, Command command) {
        if (command == null) {
            throw new IllegalArgumentException("command");
        }

        delegateRunnerFactory.CreateInstance(command.getGuid(), automationInfo).Execute(command.GetCommandDelegate());
    }

    /**
     * Executes a command.
     *
     * @param automationInfo The automation info.
     * @param command        The command to execute.
     * @return The return value of the command.
     */
    public Object Execute(AutomationInfo automationInfo, CommandWithReturn command) {
        if (command == null) {
            throw new IllegalArgumentException("command");
        }

        return delegateRunnerFactory.CreateInstance(command.getGuid(), automationInfo).Execute(command.GetCommandDelegate());
    }
}
