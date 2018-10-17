package aeon.platform.session;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.command.execution.WebCommandExecutionFacade;
import aeon.platform.models.ExecuteCommandBody;
import aeon.platform.services.CommandService;

import java.lang.reflect.Constructor;

/**
 * Creates a Session object.
 */
public class Session implements ISession {

    private AutomationInfo automationInfo;
    private ICommandExecutionFacade commandExecutionFacade;
    private CommandService commandService = new CommandService();

    /**
     * Constructs a Session.
     * @param automationInfo Automation info
     * @param commandExecutionFacade Command execution facade
     */
    public Session(AutomationInfo automationInfo, ICommandExecutionFacade commandExecutionFacade) {
        this.automationInfo = automationInfo;
        this.commandExecutionFacade = commandExecutionFacade;
    }

    /**
     * Sets the Command Service.
     * @param commandService Command service
     */
    public void setCommandService(CommandService commandService) {
        this.commandService = commandService;
    }

    /**
     * Executes a given command.
     * @param body Command body
     * @return Object
     * @throws Exception Throws an exception if an error occurs
     */
    public Object executeCommand(ExecuteCommandBody body) throws Exception {
        if (body.getCommand() != null) {
            String commandString = body.getCommand();

            Constructor commandCons;
            commandCons = commandService.getCommandInstance(commandString);

            return commandService.executeCommand(commandCons, body, automationInfo, (WebCommandExecutionFacade) commandExecutionFacade);
        }

        throw new Exception();
    }
}
