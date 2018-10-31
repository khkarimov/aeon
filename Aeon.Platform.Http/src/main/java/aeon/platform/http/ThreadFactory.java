package aeon.platform.http;

import aeon.platform.session.ISession;
import com.rabbitmq.client.Channel;
import org.bson.types.ObjectId;

import java.util.List;

public class ThreadFactory {

    public ThreadClass getThread(ObjectId sessionId, ISession session, String commandString, List<Object> args) {
        return new ThreadClass(sessionId, session, commandString, args);
    }
}
