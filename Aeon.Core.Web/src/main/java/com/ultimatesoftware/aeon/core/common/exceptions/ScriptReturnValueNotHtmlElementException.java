package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

import java.io.Serializable;
import java.util.Locale;

/**
 * The exception that is thrown when a script does not return an HTML element.
 */
public class ScriptReturnValueNotHtmlElementException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ScriptReturnValueNotHtmlElementException} class.
     *
     * @param returnValue The return value of the script.
     * @param script      The script that was executed.
     */
    public ScriptReturnValueNotHtmlElementException(Object returnValue, String script) {
        super(String.format(Locale.getDefault(), Resources.getString("ScriptReturnValueNotHTMLElementException_ctor_DefaultMessage"), returnValue, script));
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
    }
}
