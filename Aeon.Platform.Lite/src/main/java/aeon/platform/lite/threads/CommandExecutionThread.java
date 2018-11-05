package aeon.platform.lite.threads;

import aeon.platform.lite.exceptions.PublishMessageException;
import aeon.platform.lite.models.ResponseBody;
import aeon.platform.session.ISession;
import com.rabbitmq.client.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.List;

/**
 * Class to handle creating threads for sessions.
 */
public class CommandExecutionThread extends Thread {

    private ObjectId sessionId;
    private ISession session;
    private String commandString;
    private List<Object> args;
    private Channel channel;

    private static final String QUEUE_NAME = "AeonApp";
    private static Logger log = LogManager.getLogger(CommandExecutionThread.class);

    /**
     * Constructs a thread.
     *
     * @param sessionId     Session ID
     * @param session       Session
     * @param commandString Command string
     * @param args          Arguments
     */
    CommandExecutionThread(ObjectId sessionId, ISession session, String commandString, List<Object> args, Channel channel) {
        this.sessionId = sessionId;
        this.session = session;
        this.commandString = commandString;
        this.args = args;
        this.channel = channel;
    }

    @Override
    public void run() {
        ResponseBody response;

        try {
            Object result = session.executeCommand(commandString, args);

            if (result == null) {
                response = new ResponseBody(sessionId.toString(), true, null, null);
            } else {
                response = new ResponseBody(sessionId.toString(), true, result.toString(), null);
            }
        } catch (Throwable e) {
            response = new ResponseBody(sessionId.toString(), false, null, e.getMessage());
        }

        try {
            channel.basicPublish("", QUEUE_NAME, null, response.toString().getBytes());
        } catch (IOException e) {
            log.error("Could not publish message.", e);
            throw new PublishMessageException(e);
        }
    }
}
