package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

import java.util.Locale;

/**
 * The exception that is thrown to indicate a command cannot be executed.
 */
public class CommandExecutionException extends Exception {

    /**
     * Initializes new instance of the {@link CommandExecutionException} class with the specified detail message.
     * @param s Detail message
     */
    public CommandExecutionException(String s) {
        super(String.format(Locale.getDefault(), Resources.getString("CommandExecutionException_ctor_DefaultMessage"), s));
    }
}
