package aeon.platform.http;

import aeon.platform.http.models.ExecuteCommandBody;
import aeon.platform.http.models.ResponseBody;
import aeon.platform.session.ISession;
import com.rabbitmq.client.Channel;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

public class ThreadClass extends Thread {

    private ObjectId sessionId;
    private ISession session;
    private ExecuteCommandBody body;
    private Channel channel;

    private static final String QUEUE_NAME = "AeonApp";

    public ThreadClass(ObjectId sessionId, ISession session, String commandString, List<Object> args, Channel channel) {
        this.sessionId = sessionId;
        this.session = session;
        this.body = body;
        this.channel = channel;
    }

    public void run() {
        try {
            Object result = session.executeCommand(body.getCommand(), body.getArgs());

            if (result == null) {
                ResponseBody response = new ResponseBody(sessionId.toString(), true, null, null);
                channel.basicPublish("", QUEUE_NAME, null, response.toString().getBytes());
                return;
            }

            ResponseBody response = new ResponseBody(sessionId.toString(), true, result.toString(), null);
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
