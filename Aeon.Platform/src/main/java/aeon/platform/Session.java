package aeon.platform;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.command.execution.WebCommandExecutionFacade;
import aeon.platform.models.ExecuteCommandBody;
import aeon.platform.services.CommandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Constructor;

public class Session implements ISession {

    private AutomationInfo automationInfo;
    private ICommandExecutionFacade commandExecutionFacade;
    private CommandService commandService = new CommandService();

    public Session(AutomationInfo automationInfo, ICommandExecutionFacade commandExecutionFacade) {
        this.automationInfo = automationInfo;
        this.commandExecutionFacade = commandExecutionFacade;
    }

    public Object executeCommand(ExecuteCommandBody body) {
        if (body.getCommand() != null) {
//            automationInfo = sessionTable.get(sessionId);
//            commandExecutionFacade = (WebCommandExecutionFacade) automationInfo.getCommandExecutionFacade();
            String commandString = body.getCommand();

            Constructor commandCons;
            try {
                commandCons = commandService.createConstructor(commandString);
            } catch (Exception e) {
                return null;
                //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

//            if (commandCons.getName().equals("aeon.core.command.execution.commands.QuitCommand")) {
//                sessionTable.remove(sessionId);
//            }

            try {
                return commandService.executeCommand(commandCons, body, automationInfo, (WebCommandExecutionFacade) commandExecutionFacade);
            } catch (Exception e) {
                return null;
                //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        return null;
        //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
