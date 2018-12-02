package aeon.platform.http.controllers;

import aeon.platform.factories.SessionFactory;
import aeon.platform.http.models.CreateSessionBody;
import aeon.platform.http.models.ExecuteCommandBody;
import aeon.platform.http.models.ResponseBody;
import aeon.platform.http.threads.ThreadFactory;
import aeon.platform.session.ISession;
import com.codahale.metrics.annotation.Timed;
import org.bson.types.ObjectId;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Map;

/**
 * Controller for session.
 */
@Path("api/v1/sessions")
@Produces(MediaType.APPLICATION_JSON)
public class HttpSessionController {

    private Map<ObjectId, ISession> sessionTable;
    private SessionFactory sessionFactory;
    private ThreadFactory threadFactory;

    /**
     * Constructs a Session Controller.
     *
     * @param sessionFactory Session factory
     * @param threadFactory  Thread factory
     * @param sessionTable   Session table
     */
    public HttpSessionController(SessionFactory sessionFactory, ThreadFactory threadFactory, Map<ObjectId, ISession> sessionTable) {
        this.sessionFactory = sessionFactory;
        this.threadFactory = threadFactory;
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
    @Path("{sessionId}/commands")
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

    /**
     * Executes a given command asynchronously.
     *
     * @param sessionId Session ID
     * @param body      Command body
     * @return Response body
     */
    @POST
    @Timed
    @Path("{sessionId}/async-commands")
    public Response executeAsyncCommand(@PathParam("sessionId") ObjectId sessionId, ExecuteCommandBody body) {
        if (!sessionTable.containsKey(sessionId)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        ISession session = sessionTable.get(sessionId);
        threadFactory.getCommandExecutionThread(sessionId, session, body.getCommand(), body.getArgs(), body.getCallbackUrl()).start();

        return Response.status(Response.Status.OK)
                .entity(new ResponseBody(sessionId.toString(), true, "The asynchronous command was successfully scheduled.", null))
                .build();
    }

    /**
     * Quits the current session.
     *
     * @param sessionId Session ID
     * @return Response entity
     */
    @DELETE
    @Timed
    @Path("{sessionId}")
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
