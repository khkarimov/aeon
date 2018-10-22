package aeon.platform.session;

import java.util.List;

/**
 * Interface for session.
 */
public interface ISession {

    /**
     * Executes a given command.
     * @param commandString Command string
     * @param args Arguments
     * @return Object
     * @throws Exception Throws an exception if an error occurs
     */
    Object executeCommand(String commandString, List<Object> args) throws Exception;

    /**
     * Quits the current session.
     */
    void quitSession();
}
