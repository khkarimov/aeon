package aeon.core.command.execution;

import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.command.execution.commands.web.WebControlCommand;
import aeon.core.command.execution.commands.web.WebControlCommandWithReturn;
import aeon.core.command.execution.consumers.interfaces.IDelegateRunnerFactory;
import aeon.core.common.helpers.AjaxWaiter;

/**
 * Created by SebastianR on 11/8/2016.
 */
public class WebCommandExecutionFacade implements ICommandExecutionFacade {
    private IDelegateRunnerFactory delegateRunnerFactory;
    private AjaxWaiter ajaxWaiter;

    /**
     * Initializes a new instance of the {@link WebCommandExecutionFacade} class.
     *
     * @param delegateRunnerFactory The delegate runner factory.
     */
    public WebCommandExecutionFacade(IDelegateRunnerFactory delegateRunnerFactory, AjaxWaiter ajaxWaiter) {
        this.delegateRunnerFactory = delegateRunnerFactory;
        this.ajaxWaiter = ajaxWaiter;
    }

    public long getAjaxWaiterTimeoutMillis(){
        return ajaxWaiter.getTimeout();
    }

    public void setAjaxWaiterTimeout(long millis){
        ajaxWaiter.setTimeout(millis);
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
        if(command instanceof WebControlCommand){
            ajaxWaiter.WaitForAsync();
        }
        delegateRunnerFactory.CreateInstance(automationInfo).Execute(command.GetCommandDelegate());
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
        if(command instanceof WebControlCommandWithReturn){
            ajaxWaiter.WaitForAsync();
        }
        return delegateRunnerFactory.CreateInstance(automationInfo).Execute(command.GetCommandDelegate());
    }
}
