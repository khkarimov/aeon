package aeon.platform.http.threads;

import aeon.platform.http.models.ResponseBody;
import aeon.platform.session.ISession;
import org.bson.types.ObjectId;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import java.util.List;

/**
 * Class to handle creating threads for sessions.
 */
public class CommandExecutionThread extends Thread {

    private ObjectId sessionId;
    private ISession session;
    private String commandString;
    private List<Object> args;
    private String url;
    private Client client;

    /**
     * Constructs a thread.
     *
     * @param sessionId     Session ID
     * @param session       Session
     * @param commandString Command string
     * @param args          Arguments
     * @param url           Callback URL
     * @param client        Client
     */
    CommandExecutionThread(ObjectId sessionId, ISession session, String commandString, List<Object> args, String url, Client client) {
        this.sessionId = sessionId;
        this.session = session;
        this.commandString = commandString;
        this.args = args;
        this.url = url;
        this.client = client;
    }

    @Override
    public void run() {
        ResponseBody response;

        try {
            Object result = session.executeCommand(commandString, args);

            if (url == null || url.isEmpty()) {
                return;
            }

            if (result == null) {
                response = new ResponseBody(sessionId.toString(), true, null, null);
            } else {
                response = new ResponseBody(sessionId.toString(), true, result.toString(), null);
            }
        } catch (Throwable e) {
            response = new ResponseBody(sessionId.toString(), false, null, e.getMessage());
        }

        WebTarget target = client.target(url);

        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        invocationBuilder.post(Entity.entity(response, MediaType.APPLICATION_JSON));
    }
}
