package echo.core.command_execution;


import echo.core.command_execution.commands.Command;
import echo.core.command_execution.commands.CommandWithReturn;
import echo.core.command_execution.commands.QuitCommand;
import echo.core.command_execution.consumers.interfaces.IDelegateRunnerFactory;

import echo.core.common.helpers.AjaxWaiter;
import echo.core.common.helpers.Sleep;
/**
 * Created by SebastianR on 11/8/2016.
 */
public class WebCommandExecutionFacade implements ICommandExecutionFacade {
    private IDelegateRunnerFactory delegateRunnerFactory;
    private AjaxWaiter ajaxWaiter;

    /**
     * Initializes a new instance of the {@link CommandExecutionFacade} class.
     *
     * @param delegateRunnerFactory The delegate runner factory.
     */
    public WebCommandExecutionFacade(IDelegateRunnerFactory delegateRunnerFactory, AjaxWaiter ajaxWaiter) {
        this.delegateRunnerFactory = delegateRunnerFactory;
        this.ajaxWaiter = ajaxWaiter;
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
        if(!(command instanceof QuitCommand)){
            ajaxWaiter.WaitForAsync(command.getGuid());
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
        ajaxWaiter.WaitForAsync(command.getGuid());
        return delegateRunnerFactory.CreateInstance(command.getGuid(), automationInfo).Execute(command.GetCommandDelegate());
    }
}
