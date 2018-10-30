package aeon.platform.http.controllers;

import aeon.platform.factories.SessionFactory;
import aeon.platform.http.models.CreateSessionBody;
import aeon.platform.http.models.ExecuteCommandBody;
import aeon.platform.http.models.ResponseBody;
import aeon.platform.session.ISession;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

/**
 * Controller for session.
 */
@RestController
@RequestMapping("api/v1")
public class HttpSessionController {

    private static final String QUEUE_NAME = "AeonApp";

    private Map<ObjectId, ISession> sessionTable = new ConcurrentHashMap<>();
    private SessionFactory sessionFactory;

    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;

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
            Object result = session.executeCommand(body.getCommand(), body.getArgs());

            if (result == null) {
                return new ResponseEntity<>(new ResponseBody(true, null, null), HttpStatus.OK);
            }

            return new ResponseEntity<>(new ResponseBody(true, result.toString(), null), HttpStatus.OK);
        } catch (Throwable e) {
            return new ResponseEntity<>(new ResponseBody(false, null, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("sessions/{sessionId}/async")
    public ResponseEntity executeAsyncCommand(@PathVariable ObjectId sessionId, @RequestBody ExecuteCommandBody body) throws IOException, TimeoutException {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        new Thread(() -> {
            System.out.println("Execute method asynchronously - " + Thread.currentThread().getName());

            if (!sessionTable.containsKey(sessionId)) {
                //ResponseEntity response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                String response = "<404 Not Found>";

                try {
                    channel.basicPublish("", QUEUE_NAME, null, response.getBytes());
                    //channel.basicPublish("", QUEUE_NAME, null, response.toString().getBytes());
                    return;
                } catch (IOException e) {
                    //
                    e.printStackTrace();
                }
            }

            ISession session = sessionTable.get(sessionId);

            try {
                Object result = session.executeCommand(body.getCommand(), body.getArgs());

                if (result == null) {
                    String response = "<200 OK, {success: true, data: null, failureMessage: null}>";

                    //ResponseEntity response = new ResponseEntity<>(new ResponseBody(true, null, null), HttpStatus.OK);
                    channel.basicPublish("", QUEUE_NAME, null, response.getBytes());
                    //channel.basicPublish("", QUEUE_NAME, null, response.toString().getBytes());
                    return;
                }

                System.out.println("\nResult from async process - " + result);

                String response = "<200 OK, {success: true, data: " + result.toString() + ", failureMessage: null}>";
                //ResponseEntity response = new ResponseEntity<>(new ResponseBody(true, result.toString(), null), HttpStatus.OK);
                channel.basicPublish("", QUEUE_NAME, null, response.getBytes());
                //channel.basicPublish("", QUEUE_NAME, null, response.toString().getBytes());
                return;
            } catch (Throwable e) {
                System.out.println("\nBAD REQUEST");

                String response = "<400 Bad Request, {success: false, data: null, failureMessage: " + e.getMessage() + "}>";
                //ResponseEntity response = new ResponseEntity<>(new ResponseBody(false, null, e.getMessage()), HttpStatus.BAD_REQUEST);
                try {
                    channel.basicPublish("", QUEUE_NAME, null, response.getBytes());
                    //channel.basicPublish("", QUEUE_NAME, null, response.toString().getBytes());
                    return;
                } catch (IOException e1) {
                    //
                    e1.printStackTrace();
                }
            }
        }).start();

        System.out.println("DONE");
        return null;
    }

    /**
     * Quits the current session.
     * @param sessionId Session ID
     * @return Response entity
     */
    @DeleteMapping("sessions/{sessionId}")
    public ResponseEntity quitSession(@PathVariable ObjectId sessionId) throws IOException, TimeoutException{
        if (!sessionTable.containsKey(sessionId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ISession session = sessionTable.get(sessionId);

        session.quitSession();
        sessionTable.remove(sessionId);

        channel.close();
        connection.close();
        System.out.println("Closing channel and connection...");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
