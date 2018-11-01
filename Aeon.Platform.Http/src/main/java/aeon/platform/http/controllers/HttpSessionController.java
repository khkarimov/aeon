package aeon.platform.http.controllers;

import aeon.platform.factories.SessionFactory;
import aeon.platform.http.models.CreateSessionBody;
import aeon.platform.http.models.ExecuteCommandBody;
import aeon.platform.http.models.ResponseBody;
import aeon.platform.http.threads.ThreadFactory;
import aeon.platform.session.ISession;
import org.bson.types.ObjectId;
import org.json.JSONObject;
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
    private ThreadFactory threadFactory;

    /**
     * Constructs a Session Controller.
     * @param sessionFactory Session factory
     * @param threadFactory Thread factory
     */
    @Autowired
    public HttpSessionController(SessionFactory sessionFactory, ThreadFactory threadFactory) {
        this.sessionFactory = sessionFactory;
        this.threadFactory = threadFactory;
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

        JSONObject sessionIdJson = new JSONObject();
        sessionIdJson.put("sessionId", sessionId.toString());

        return new ResponseEntity<>(sessionIdJson.toString(), HttpStatus.CREATED);
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
            Object result = session.executeCommand(body.getCommand(), body.getArgs());

            if (result == null) {
                return new ResponseEntity<>(new ResponseBody(sessionId.toString(), true, null, null), HttpStatus.OK);
            }

            return new ResponseEntity<>(new ResponseBody(sessionId.toString(), true, result.toString(), null), HttpStatus.OK);
        } catch (Throwable e) {
            return new ResponseEntity<>(new ResponseBody(sessionId.toString(), false, null, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Executes a given command asynchronously.
     * @param sessionId Session ID
     * @param body Command body
     * @return Response body
     */
    @PostMapping("sessions/{sessionId}/async")
    public ResponseEntity executeAsyncCommand(@PathVariable ObjectId sessionId, @RequestBody ExecuteCommandBody body) {
        if (!sessionTable.containsKey(sessionId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ISession session = sessionTable.get(sessionId);
        threadFactory.getThread(sessionId, session, body.getCommand(), body.getArgs()).start();

        return null;
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
