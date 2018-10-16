package aeon.platform.session;

import aeon.platform.models.ExecuteCommandBody;

/**
 * Interface for session.
 */
public interface ISession {

    /**
     * Executes a given command.
     * @param body Command body
     * @return Object
     * @throws Exception Throws an exception if an error occurs
     */
    Object executeCommand(ExecuteCommandBody body) throws Exception;
}
