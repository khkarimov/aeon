package com.ultimatesoftware.aeon.platform.http.controllers;

import com.codahale.metrics.annotation.Timed;
import com.ultimatesoftware.aeon.core.testabstraction.product.Aeon;
import com.ultimatesoftware.aeon.platform.factories.SessionFactory;
import com.ultimatesoftware.aeon.platform.http.HttpSessionIdProvider;
import com.ultimatesoftware.aeon.platform.http.models.CreateSessionBody;
import com.ultimatesoftware.aeon.platform.http.models.ExecuteCommandBody;
import com.ultimatesoftware.aeon.platform.http.models.ResponseBody;
import com.ultimatesoftware.aeon.platform.http.threads.ThreadFactory;
import com.ultimatesoftware.aeon.platform.session.ISession;
import org.bson.types.ObjectId;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
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
     */
    @POST
    @Timed
    public Response createSession(CreateSessionBody body) {

        ObjectId sessionId = new ObjectId();
        getSessionIdProvider().setCurrentSessionId(sessionId.toString());

        try {
            sessionTable.put(sessionId, sessionFactory.getSession(body == null ? null : body.getSettings()));
        } catch (IllegalAccessException | IOException | InstantiationException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
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
        getSessionIdProvider().setCurrentSessionId(sessionId.toString());

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
        } catch (Exception e) {
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
        threadFactory.getCommandExecutionThread(sessionId, session, body.getCommand(), body.getArgs(), body.getCallbackUrl(), getSessionIdProvider()).start();

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
        getSessionIdProvider().setCurrentSessionId(sessionId.toString());

        session.quitSession();
        sessionTable.remove(sessionId);

        return Response.status(Response.Status.OK).build();
    }

    private HttpSessionIdProvider getSessionIdProvider() {
        return (HttpSessionIdProvider) Aeon.getSessionIdProvider();
    }
}
