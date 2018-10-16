package aeon.platform.controllers;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.WebCommandExecutionFacade;
import aeon.platform.ISession;
import aeon.platform.Session;
import aeon.platform.SessionFactory;
import aeon.platform.models.CreateSessionBody;
import aeon.platform.models.ExecuteCommandBody;
import aeon.platform.services.CommandService;
import aeon.platform.services.SessionService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Controller for session.
 */
@RestController
@RequestMapping("api/v1")
public class SessionController {

    private AutomationInfo automationInfo;
    private WebCommandExecutionFacade commandExecutionFacade;
    private Map<ObjectId, ISession> sessionTable = new ConcurrentHashMap<>();

    private SessionService sessionService;
    private CommandService commandService;

    /**
     * Constructs a Session Controller.
     * @param sessionService ISession helper class
     * @param commandService Command helper class
     */
    @Autowired
    public SessionController(SessionService sessionService, CommandService commandService) {
        this.sessionService = sessionService;
        this.commandService = commandService;
    }

    //??????

//    public SessionController(Map<ObjectId, ISession> sessionTable) {
//        this.sessionTable = sessionTable;
//    }




//    /**
//     * Sets the session table.
//     * @param sessionTable ISession table
//     */
//    public void setSessionTable(Map<ObjectId, ISession> sessionTable) {
//        this.sessionTable = sessionTable;
//    }

    /**
     * Creates a new session.
     * @param body Body
     * @return Response entity
     * @throws Exception Throws an exception if an error occurs
     */
    @PostMapping("sessions")
    public ResponseEntity createSession(@RequestBody CreateSessionBody body) throws Exception {
    //public ObjectId createSession(CreateSessionBody body) throws Exception {
    //public ResponseEntity createSession(CreateSessionBody body) throws Exception {
        ObjectId sessionId = new ObjectId();

        //
        SessionFactory sessionFactory = new SessionFactory(sessionService);
        //sessionFactory.createSession(body.getSettings());



//        automationInfo = sessionService.setUpAutomationInfo(body.getSettings());
//        commandExecutionFacade = sessionService.setUpCommandExecutionFacade(automationInfo);

        sessionTable.put(sessionId, sessionFactory.getSession(body.getSettings()));



        //return sessionId;
        return new ResponseEntity<>(sessionId.toString(), HttpStatus.CREATED);
    }

    /**
     * Executes a given command.
     * @param sessionId ISession ID
     * @param body Body
     * @return Response entity
     * @throws Exception Throws an exception if an error occurs
     */
    @PostMapping("sessions/{sessionId}/commands")
    public ResponseEntity executeCommand(@PathVariable ObjectId sessionId, @RequestBody ExecuteCommandBody body) throws Exception {
        ISession session = sessionTable.get(sessionId);

        if (body.getCommand().equals("QuitCommand")) {
            sessionTable.remove(sessionId);
        }

        Object result = session.executeCommand(body);

        if (result == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return (ResponseEntity) result;
//        if (body.getCommand() != null) {
//            automationInfo = sessionTable.get(sessionId);
//            commandExecutionFacade = (WebCommandExecutionFacade) automationInfo.getCommandExecutionFacade();
//            String commandString = body.getCommand();
//
//            Constructor commandCons;
//            try {
//                commandCons = commandService.createConstructor(commandString);
//            } catch (Exception e) {
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//
//            if (commandCons.getName().equals("aeon.core.command.execution.commands.QuitCommand")) {
//                sessionTable.remove(sessionId);
//            }
//
//            try {
//                return commandService.executeCommand(commandCons, body, automationInfo, commandExecutionFacade);
//            } catch (Exception e) {
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//        }

//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
