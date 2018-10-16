//package aeon.platform.http;
//
//import aeon.core.command.execution.AutomationInfo;
//import aeon.core.command.execution.WebCommandExecutionFacade;
//import aeon.core.command.execution.commands.Command;
//import aeon.core.command.execution.commands.QuitCommand;
//import aeon.platform.http.HttpSessionController;
//import aeon.platform.models.CreateSessionBody;
//import aeon.platform.models.ExecuteCommandBody;
//import aeon.platform.services.CommandService;
//import aeon.platform.services.SessionService;
//import org.bson.types.ObjectId;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.lang.reflect.Constructor;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * Controller for HTTP session.
// */
//@RestController
//@RequestMapping("api/v1")
//public class HttpController {
//
//
//    private Map<ObjectId, HttpSessionController> sessionTable = new ConcurrentHashMap<>();
//
//
//
//    public HttpController(SessionService sessionService, CommandService commandService) {
//        super(sessionService, commandService);
//    }
//
//
//
//    /**
//     * Creates a new session.
//     * @param body Body
//     * @return Response entity
//     * @throws Exception Throws an exception if an error occurs
//     */
//    @PostMapping("sessions")
//    public ResponseEntity createHttpSession(@RequestBody CreateSessionBody body) throws Exception {
//
//        HttpSessionController sessionController = super();
//
//
//        //ObjectId sessionId = super.createSession(body);
//        ObjectId sessionId = sessionController.createSession(body);
//        sessionTable.put(sessionId, sessionController);
//
//        return new ResponseEntity<>(sessionId.toString(), HttpStatus.CREATED);
//    }
//
//    /**
//     * Executes a given command.
//     * @param sessionId ISession ID
//     * @param body Body
//     * @return Response entity
//     * @throws Exception Throws an exception if an error occurs
//     */
//    @PostMapping("sessions/{sessionId}/commands")
//    public ResponseEntity executeCommand(@PathVariable ObjectId sessionId, @RequestBody ExecuteCommandBody body) throws Exception {
//
//        return super.executeCommand(sessionId, body);
//        //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }
//}
