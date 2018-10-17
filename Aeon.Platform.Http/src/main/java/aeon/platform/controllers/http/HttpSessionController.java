package aeon.platform.controllers.http;

import aeon.platform.models.CreateSessionBody;
import aeon.platform.models.ExecuteCommandBody;
import aeon.platform.services.SessionFactory;
import aeon.platform.session.ISession;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Controller for session.
 */
@RestController
@RequestMapping("api/v1")
public class HttpSessionController {

    private Map<ObjectId, ISession> sessionTable = new ConcurrentHashMap<>();
    private SessionFactory sessionFactory;

    /**
     * Constructs a Session Controller.
     * @param sessionFactory Session factory
     */
    @Autowired
    public HttpSessionController(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }



// ??
    /**
     * Sets the session table.
     * @param sessionTable ISession table
     */
    public void setSessionTable(Map<ObjectId, ISession> sessionTable) {
        this.sessionTable = sessionTable;
    }

    /**
     * Creates a new session.
     * @param body Session body
     * @return Response entity
     * @throws Exception Throws an exception if an error occurs
     */
    @PostMapping("sessions")
    public ResponseEntity createSession(@RequestBody CreateSessionBody body) throws Exception {
        ObjectId sessionId = new ObjectId();
        sessionTable.put(sessionId, sessionFactory.getSession(body.getSettings()));

        return new ResponseEntity<>(sessionId.toString(), HttpStatus.CREATED);
    }

    /**
     * Executes a given command.
     * @param sessionId ISession ID
     * @param body Command body
     * @return Response entity
     */
    @PostMapping("sessions/{sessionId}/commands")
    public ResponseEntity executeCommand(@PathVariable ObjectId sessionId, @RequestBody ExecuteCommandBody body) {
        ISession session = sessionTable.get(sessionId);

        if (body.getCommand().equals("QuitCommand")) {
            sessionTable.remove(sessionId);
        }

        Object result;

        try {
            result = session.executeCommand(body);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
