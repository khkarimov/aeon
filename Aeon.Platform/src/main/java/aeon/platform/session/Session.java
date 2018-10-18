package aeon.platform.session;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.platform.DaggerAeonPlatformComponent;
import aeon.platform.models.ExecuteCommandBody;
import aeon.platform.services.CommandService;

/**
 * Creates a Session object.
 */
public class Session implements ISession {

    private AutomationInfo automationInfo;
    private ICommandExecutionFacade commandExecutionFacade;

    private CommandService commandService;

    /**
     * Constructs a Session.
     * @param automationInfo Automation info
     * @param commandExecutionFacade Command execution facade
     */
    public Session(AutomationInfo automationInfo, ICommandExecutionFacade commandExecutionFacade) {
        this.automationInfo = automationInfo;
        this.commandExecutionFacade = commandExecutionFacade;

        commandService = DaggerAeonPlatformComponent.create().buildCommandService();
    }

    /**
     * Sets the Command Service.
     * @param commandService Command service
     */
    void setCommandService(CommandService commandService) {
        this.commandService = commandService;
    }

    /**
     * Executes a given command.
     * @param body Command body
     * @return Object
     * @throws Exception Throws an exception if an error occurs
     */
    public Object executeCommand(ExecuteCommandBody body) throws Exception {
        String commandString = body.getCommand();

        if (commandString != null) {
            return commandService.executeCommand(commandString, body, automationInfo, commandExecutionFacade);
        }

        throw new Exception();
    }
}
