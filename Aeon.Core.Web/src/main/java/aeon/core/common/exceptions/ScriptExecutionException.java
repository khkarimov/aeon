package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.io.Serializable;
import java.util.Locale;

/**
 * The exception that is thrown when a script has an error.
 */
public class ScriptExecutionException extends RuntimeException implements Serializable {

    private String script;

    /**
     * Initializes a new instance of the {@link ScriptExecutionException} class.
     *
     * @param script         The script that was executed.
     * @param innerException The exception that is the cause of the current exception, or a null reference (Nothing in Visual Basic) if no inner exception is specified.
     */
    public ScriptExecutionException(String script, RuntimeException innerException) {
        super(String.format(Locale.getDefault(), Resources.getString("ScriptExecutionException_ctor_DefaultMessage"), script), innerException);
        this.script = script;
    }

    /**
     * Gets the current script.
     * @return the current script.
     */
    public String getScript() {
        return script;
    }

    /**
     * Sets the script to a string input.
     * @param script the script to be set.
     */
    public void setScript(String script) {
        this.script = script;
    }
}
