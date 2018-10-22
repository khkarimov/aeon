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

    /**
     * Sets the session table.
     * @param sessionTable Session table
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
    public ResponseEntity createSession(@RequestBody(required = false) CreateSessionBody body) throws Exception {
        ObjectId sessionId = new ObjectId();

        if (body != null) {
            sessionTable.put(sessionId, sessionFactory.getSession(body.getSettings()));
        } else {
            sessionTable.put(sessionId, sessionFactory.getSession(null));

        }

        return new ResponseEntity<>(sessionId.toString(), HttpStatus.CREATED);
    }

    /**
     * Executes a given command.
     * @param sessionId Session ID
     * @param body Command body
     * @return Response entity
     */
    @PostMapping("sessions/{sessionId}/commands")
    public ResponseEntity executeCommand(@PathVariable ObjectId sessionId, @RequestBody ExecuteCommandBody body) {
        if (!sessionTable.containsKey(sessionId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ISession session = sessionTable.get(sessionId);

        try {
            return new ResponseEntity<>(session.executeCommand(body.getCommand(), body.getArgs()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Quits the current session.
     * @param sessionId Session ID
     * @return Response entity
     */
    @DeleteMapping("sessions/{sessionId}")
    public ResponseEntity quitSession(@PathVariable ObjectId sessionId) {
        if (!sessionTable.containsKey(sessionId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ISession session = sessionTable.get(sessionId);

        session.quitSession();
        sessionTable.remove(sessionId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
