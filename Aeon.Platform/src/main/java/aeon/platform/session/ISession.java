package aeon.platform.session;

import aeon.core.common.exceptions.CommandExecutionException;

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
     * @throws CommandExecutionException Throws an exception if an error occurs
     */
    Object executeCommand(String commandString, List<Object> args) throws CommandExecutionException;

    /**
     * Quits the current session.
     */
    void quitSession();
}
