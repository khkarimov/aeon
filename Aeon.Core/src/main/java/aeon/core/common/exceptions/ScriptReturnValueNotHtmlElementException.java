package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.io.Serializable;
import java.util.Locale;

/**
 * The exception that is thrown when a script does not return an HTML element.
 */
public class ScriptReturnValueNotHtmlElementException extends RuntimeException implements Serializable {

    private Object returnValue;
    private String script;

    /**
     * Initializes a new instance of the {@link ScriptReturnValueNotHtmlElementException} class.
     *
     * @param returnValue The return value of the script.
     * @param script      The script that was executed.
     */
    public ScriptReturnValueNotHtmlElementException(Object returnValue, String script) {
        super(String.format(Locale.getDefault(), Resources.getString("ScriptReturnValueNotHTMLElementException_ctor_DefaultMessage"), returnValue, script));
        this.returnValue = returnValue;
        this.script = script;
    }

    /**
     * Initializes a new instance of the {@link ScriptReturnValueNotHtmlElementException} class.
     *
     * @param returnValue    The return value of the script.
     * @param script         The script that was executed.
     * @param innerException The exception that is the cause of the current exception, or a null reference (Nothing in Visual Basic) if no inner exception is specified.
     */
    public ScriptReturnValueNotHtmlElementException(Object returnValue, String script, Exception innerException) {
        super(String.format(Locale.getDefault(), Resources.getString("ScriptReturnValueNotHTMLElementException_ctor_DefaultMessage"), returnValue, script), innerException);
        this.returnValue = returnValue;
        this.script = script;
    }

    /**
     * Gets the return value.
     * @return the current return value.
     */
    public Object getReturnValue() {
        return returnValue;
    }

    /**
     * Function that sets the return value.
     * @param returnValue to set to the current returnValue
     */
    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    /**
     * Gets the script.
     * @return the script.
     */
    public String getScript() {
        return script;
    }

    /**
     * Function that sets the script to the given input.
     * @param script the input to be set.
     */
    public void setScript(String script) {
        this.script = script;
    }
}
