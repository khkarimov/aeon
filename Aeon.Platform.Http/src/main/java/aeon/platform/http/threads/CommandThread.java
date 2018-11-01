package aeon.platform.http.threads;

import aeon.platform.http.models.ResponseBody;
import aeon.platform.session.ISession;
import com.rabbitmq.client.Channel;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.List;

/**
 * Class to handle creating threads for sessions.
 */
public class CommandThread extends Thread {

    private ObjectId sessionId;
    private ISession session;
    private String commandString;
    private List<Object> args;
    private Channel channel;

    private static final String QUEUE_NAME = "AeonApp";

    /**
     * Constructs a thread.
     * @param sessionId Session ID
     * @param session Session
     * @param commandString Command string
     * @param args Arguments
     */
    CommandThread(ObjectId sessionId, ISession session, String commandString, List<Object> args, Channel channel) {
        this.sessionId = sessionId;
        this.session = session;
        this.commandString = commandString;
        this.args = args;
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            Object result = session.executeCommand(commandString, args);
            ResponseBody response;

            if (result == null) {
                response = new ResponseBody(sessionId.toString(), true, null, null);
            } else {
                response = new ResponseBody(sessionId.toString(), true, result.toString(), null);
            }

            channel.basicPublish("", QUEUE_NAME, null, response.toString().getBytes());
        } catch (Throwable e) {
            ResponseBody response = new ResponseBody(sessionId.toString(), false, null, e.getMessage());

            try {
                channel.basicPublish("", QUEUE_NAME, null, response.toString().getBytes());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
