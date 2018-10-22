package aeon.platform.session;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.command.execution.commands.QuitCommand;
import aeon.platform.services.CommandService;

import java.util.List;

/**
 * Creates a Session object.
 */
public class Session implements ISession {

    private CommandService commandService;
    private AutomationInfo automationInfo;
    private ICommandExecutionFacade commandExecutionFacade;

    /**
     * Constructs a Session.
     * @param commandService Command service
     * @param automationInfo Automation info
     * @param commandExecutionFacade Command execution facade
     */
    public Session(CommandService commandService, AutomationInfo automationInfo, ICommandExecutionFacade commandExecutionFacade) {
        this.automationInfo = automationInfo;
        this.commandExecutionFacade = commandExecutionFacade;
        this.commandService = commandService;
    }

    /**
     * Executes a given command.
     * @param commandString Command string
     * @param args Arguments
     * @return Object
     * @throws Exception Throws an exception if an error occurs
     */
    public Object executeCommand(String commandString, List<Object> args) throws Exception {
        if (commandString != null) {
            return commandService.executeCommand(commandString, args, automationInfo, commandExecutionFacade);
        }

        throw new Exception();
    }

    /**
     * Quits the current session.
     */
    public void quitSession() {
        commandExecutionFacade.execute(automationInfo, new QuitCommand());
    }
}
