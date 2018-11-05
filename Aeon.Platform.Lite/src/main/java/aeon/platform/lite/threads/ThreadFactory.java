package aeon.platform.lite.threads;

import aeon.platform.session.ISession;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Factory for threads.
 */
public class ThreadFactory {

    /**
     * Gets a new thread.
     *
     * @param sessionId     Session ID
     * @param session       Session
     * @param commandString Command string
     * @param args          Arguments
     * @return Thread
     */
    public CommandExecutionThread getCommandExecutionThread(ObjectId sessionId, ISession session, String commandString, List<Object> args, String url) {
        return new CommandExecutionThread(sessionId, session, commandString, args, url);
    }
}
