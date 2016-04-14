package echo.core.command_execution;

import echo.core.command_execution.commands.Command;
import echo.core.command_execution.commands.CommandWithReturn;
import echo.core.command_execution.delegate_runners.interfaces.IDelegateRunnerFactory;

/**
 * The facade for the Command Execution layer.
 */
public final class CommandExecutionFacade implements ICommandExecutionFacade {
    private IDelegateRunnerFactory delegateRunnerFactory;

    /**
     * Initializes a new instance of the <see cref="CommandExecutionFacade"/> class.
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

        command.getParameterObject().setAutomationInfo(automationInfo);
        delegateRunnerFactory.CreateInstance(command.getParameterObject()).Execute(command.GetCommandDelegate());
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

        command.getParameterObject().setAutomationInfo(automationInfo);
        return delegateRunnerFactory.CreateInstance(command.getParameterObject()).Execute(command.GetCommandDelegate());
    }
}
