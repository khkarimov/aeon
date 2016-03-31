package common.exceptions;

import common.Resources;

import java.io.Serializable;

/**
 * The exception that is thrown when a script has an error.
 */
public class ScriptExecutionException extends RuntimeException implements Serializable {
    private String script;

    /**
     * Initializes a new instance of the <see cref="ScriptExecutionException"/> class.
     *
     * @param script         The script that was executed.
     * @param innerException The exception that is the cause of the current exception, or a null reference (Nothing in Visual Basic) if no inner exception is specified.
     */
    public ScriptExecutionException(String script, RuntimeException innerException) {
        super(String.format(Resources.getString("ScriptExecutionException_ctor_DefaultMessage"), script), innerException);
        this.script = script;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}