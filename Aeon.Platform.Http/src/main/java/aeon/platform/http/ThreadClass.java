package aeon.platform.http;

import aeon.platform.DaggerAeonPlatformComponent;
import aeon.platform.http.models.ResponseBody;
import aeon.platform.session.ISession;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

public class ThreadClass extends Thread {

    private ObjectId sessionId;
    private ISession session;
    private String commandString;
    private List<Object> args;
    private Channel channel;

    private static final String QUEUE_NAME = "AeonApp";

    public ThreadClass(ObjectId sessionId, ISession session, String commandString, List<Object> args) {
        this.sessionId = sessionId;
        this.session = session;
        this.commandString = commandString;
        this.args = args;

        this.channel = DaggerAeonPlatformComponent.create().buildChannel();
    }

    public void run() {
        try {
            Object result = session.executeCommand(commandString, args);

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
