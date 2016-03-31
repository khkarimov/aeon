package common.exceptions;

import common.Resources;

import java.io.Serializable;

/**
 * The exception that is thrown when a script does not return an HTML element.
 */
public class ScriptReturnValueNotHtmlElementException extends RuntimeException implements Serializable {
    private Object returnValue;
    private String script;

    /**
     * Initializes a new instance of the <see cref="ScriptReturnValueNotHtmlElementException"/> class.
     *
     * @param returnValue The return value of the script.
     * @param script      The script that was executed.
     */
    public ScriptReturnValueNotHtmlElementException(Object returnValue, String script) {
        super(String.format(Resources.getString("ScriptReturnValueNotHTMLElementException_ctor_DefaultMessage"), returnValue, script));
        this.returnValue = returnValue;
        this.script = script;
    }

    /**
     * Initializes a new instance of the <see cref="ScriptReturnValueNotHtmlElementException"/> class.
     *
     * @param returnValue    The return value of the script.
     * @param script         The script that was executed.
     * @param innerException The exception that is the cause of the current exception, or a null reference (Nothing in Visual Basic) if no inner exception is specified.
     */
    public ScriptReturnValueNotHtmlElementException(Object returnValue, String script, RuntimeException innerException) {
        super(String.format(Resources.getString("ScriptReturnValueNotHTMLElementException_ctor_DefaultMessage"), returnValue, script), innerException);
        this.returnValue = returnValue;
        this.script = script;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}