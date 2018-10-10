package aeon.platform.controllers;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.WebCommandExecutionFacade;
import aeon.platform.models.CreateSessionBody;
import aeon.platform.models.ExecuteCommandBody;
import aeon.platform.services.SessionService;
import aeon.platform.services.CommandService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Controller for browser.
 */
@RestController
@RequestMapping("api/v1")
public class SessionController {

    private AutomationInfo automationInfo;
    private WebCommandExecutionFacade commandExecutionFacade;
    private Map<ObjectId, AutomationInfo> sessionTable = new ConcurrentHashMap<>();

    private SessionService sessionService;
    private CommandService commandService;

    /**
     * Constructs Browser Controller.
     * @param sessionService Browser helper class
     * @param commandService Command helper class
     */
    @Autowired
    public SessionController(SessionService sessionService, CommandService commandService) {
        this.sessionService = sessionService;
        this.commandService = commandService;
    }

    /**
     * Sets the session table.
     * @param sessionTable Session table
     */
    public void setSessionTable(Map<ObjectId, AutomationInfo> sessionTable) {
        this.sessionTable = sessionTable;
    }

    /**
     * Creates a new session.
     * @param body Body
     * @return Response entity
     * @throws Exception Throws an exception if an error occurs
     */
    @PostMapping("sessions")
    public ResponseEntity createSession(@RequestBody CreateSessionBody body) throws Exception {
        ObjectId sessionId = sessionService.createSessionId();

        automationInfo = sessionService.setUpAutomationInfo(body.getSettings());
        commandExecutionFacade = sessionService.setUpCommandExecutionFacade(automationInfo);

        sessionTable.put(sessionId, automationInfo);

        return new ResponseEntity<>(sessionId.toString(), HttpStatus.CREATED);
    }

    /**
     * Executes a given command.
     * @param sessionId Session ID
     * @param body Body
     * @return Response entity
     * @throws Exception Throws an exception if an error occurs
     */
    @PostMapping("sessions/{sessionId}/commands")
    public ResponseEntity executeCommand(@PathVariable ObjectId sessionId, @RequestBody ExecuteCommandBody body) throws Exception {
        if (body.getCommand() != null) {
            automationInfo = sessionTable.get(sessionId);
            commandExecutionFacade = (WebCommandExecutionFacade) automationInfo.getCommandExecutionFacade();
            String commandString = body.getCommand();

            Constructor commandCons = commandService.createConstructor(commandString);

            if (commandCons.getName().equals("aeon.core.command.execution.commands.QuitCommand")) {
                sessionTable.remove(sessionId);
            }

            try {
                return commandService.executeCommand(commandCons, body, automationInfo, commandExecutionFacade);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
