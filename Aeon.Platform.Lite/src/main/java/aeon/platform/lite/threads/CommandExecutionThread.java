package aeon.platform.lite.threads;

import aeon.platform.lite.models.ResponseBody;
import aeon.platform.session.ISession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;

import javax.ws.rs.client.*;
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

    //private static final String baseUrl = "http://localhost:8080/api/v1/";
    //    private static final String QUEUE_NAME = "AeonApp";
    private static Logger log = LogManager.getLogger(CommandExecutionThread.class);

    /**
     * Constructs a thread.
     *
     * @param sessionId     Session ID
     * @param session       Session
     * @param commandString Command string
     * @param args          Arguments
     */
    CommandExecutionThread(ObjectId sessionId, ISession session, String commandString, List<Object> args, String url) {
        this.sessionId = sessionId;
        this.session = session;
        this.commandString = commandString;
        this.args = args;
        this.url = url;
    }

    @Override
    public void run() {
        ResponseBody response;

        Client client = ClientBuilder.newClient();

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
