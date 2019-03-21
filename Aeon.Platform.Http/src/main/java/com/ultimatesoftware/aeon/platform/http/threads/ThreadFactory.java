package com.ultimatesoftware.aeon.platform.http.threads;

import com.ultimatesoftware.aeon.platform.http.HttpSessionIdProvider;
import com.ultimatesoftware.aeon.platform.session.ISession;
import org.bson.types.ObjectId;

import javax.ws.rs.client.ClientBuilder;

import java.util.List;

/**
 * Factory for threads.
 */
public class ThreadFactory {

    /**
     * Gets a new thread.
     *
     * @param sessionId         Session ID
     * @param session           Session
     * @param commandString     Command string
     * @param args              Arguments
     * @param url               Callback URL
     * @param sessionIdProvider Session ID provider to us
     * @return Thread
     */
    public CommandExecutionThread getCommandExecutionThread(ObjectId sessionId, ISession session, String commandString, List<Object> args, String url, HttpSessionIdProvider sessionIdProvider) {
        return new CommandExecutionThread(sessionId, session, commandString, args, url, sessionIdProvider, ClientBuilder.newClient());
    }
}
