package com.ultimatesoftware.aeon.platform.session;

import com.ultimatesoftware.aeon.core.common.exceptions.CommandExecutionException;

import java.util.List;

/**
 * Interface for session.
 */
public interface ISession {

    /**
     * Executes a given command.
     *
     * @param commandString Command string
     * @param args          Arguments
     * @return Object
     * @throws CommandExecutionException Throws an exception when a command or its arguments are invalid
     */
    Object executeCommand(String commandString, List<Object> args) throws CommandExecutionException;

    /**
     * Quits the current session.
     */
    void quitSession();
}
