package aeon.platform.lite.controllers;

import aeon.platform.factories.SessionFactory;
import aeon.platform.lite.models.CreateSessionBody;
import aeon.platform.lite.models.ExecuteCommandBody;
import aeon.platform.lite.models.ResponseBody;
import aeon.platform.lite.threads.ThreadFactory;
import aeon.platform.session.ISession;
import com.codahale.metrics.annotation.Timed;
import org.bson.types.ObjectId;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Controller for session.
 */
@Path("/api/v1")
@Produces(MediaType.APPLICATION_JSON)
public class HttpSessionController {

    private Map<ObjectId, ISession> sessionTable = new ConcurrentHashMap<>();
    private SessionFactory sessionFactory;
    private ThreadFactory threadFactory;

    /**
     * Constructs a Session Controller.
     *
     * @param sessionFactory Session factory
     * @param threadFactory  Thread factory
     */
    public HttpSessionController(SessionFactory sessionFactory, ThreadFactory threadFactory) {
        this.sessionFactory = sessionFactory;
        this.threadFactory = threadFactory;
    }

    /**
     * Sets the session table.
     *
     * @param sessionTable Session table
     */
    public void setSessionTable(Map<ObjectId, ISession> sessionTable) {
        this.sessionTable = sessionTable;
    }

    /**
     * Creates a new session.
     *
     * @param body Session body
     * @return Response entity
     * @throws Exception Throws an exception if an error occurs
     */
    @POST
    @Timed
    @Path("sessions")
    public Response createSession(CreateSessionBody body) throws Exception {

        ObjectId sessionId = new ObjectId();

        if (body != null) {
            sessionTable.put(sessionId, sessionFactory.getSession(body.getSettings()));
        } else {
            sessionTable.put(sessionId, sessionFactory.getSession(null));

        }

        JSONObject sessionIdJson = new JSONObject();
        sessionIdJson.put("sessionId", sessionId.toString());

        return Response.status(Response.Status.CREATED).entity(sessionIdJson.toString()).build();
    }

    /**
     * Executes a given command.
     *
     * @param sessionId Session ID
     * @param body      Command body
     * @return Response entity
     */
    @POST
    @Timed
    @Path("sessions/{sessionId}/commands")
    public Response executeCommand(@PathParam("sessionId") ObjectId sessionId, ExecuteCommandBody body) {
        if (!sessionTable.containsKey(sessionId)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        ISession session = sessionTable.get(sessionId);

        try {
            Object result = session.executeCommand(body.getCommand(), body.getArgs());

            if (result == null) {
                return Response.status(Response.Status.OK)
                        .entity(new ResponseBody(sessionId.toString(), true, null, null))
                        .build();
            }

            return Response.status(Response.Status.OK)
                    .entity(new ResponseBody(sessionId.toString(), true, result.toString(), null))
                    .build();
        } catch (Throwable e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ResponseBody(sessionId.toString(), false, null, e.getMessage()))
                    .build();
        }
    }
//
//    /**
//     * Executes a given command asynchronously.
//     *
//     * @param sessionId Session ID
//     * @param body      Command body
//     * @return Response body
//     */
//    @PostMapping("sessions/{sessionId}/async-commands")
//    public ResponseEntity executeAsyncCommand(@PathVariable ObjectId sessionId, @RequestBody ExecuteCommandBody body) {
//        if (!sessionTable.containsKey(sessionId)) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND_404);
//        }
//
//        ISession session = sessionTable.get(sessionId);
//        threadFactory.getCommandExecutionThread(sessionId, session, body.getCommand(), body.getArgs()).start();
//
//        return new ResponseEntity<>(new ResponseBody(sessionId.toString(), true, "The asynchronous command was successfully scheduled.", null), HttpStatus.OK_200);
//    }

    /**
     * Quits the current session.
     *
     * @param sessionId Session ID
     * @return Response entity
     */
    @DELETE
    @Timed
    @Path("sessions/{sessionId}")
    public Response quitSession(@PathParam("sessionId") ObjectId sessionId) {
        if (!sessionTable.containsKey(sessionId)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        ISession session = sessionTable.get(sessionId);

        session.quitSession();
        sessionTable.remove(sessionId);

        return Response.status(Response.Status.OK).build();
    }
}
