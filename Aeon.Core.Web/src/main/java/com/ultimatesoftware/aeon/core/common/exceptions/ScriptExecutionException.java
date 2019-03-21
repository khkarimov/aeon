package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

import java.io.Serializable;
import java.util.Locale;

/**
 * The exception that is thrown when a script has an error.
 */
public class ScriptExecutionException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ScriptExecutionException} class.
     *
     * @param script         The script that was executed.
     * @param innerException The exception that is the cause of the current exception, or a null reference (Nothing in Visual Basic) if no inner exception is specified.
     */
    public ScriptExecutionException(String script, RuntimeException innerException) {
        super(String.format(Locale.getDefault(), Resources.getString("ScriptExecutionException_ctor_DefaultMessage"), script), innerException);
    }
}
