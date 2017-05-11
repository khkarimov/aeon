package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.util.Locale;

/**
 * The exception that is thrown when the element is not a date.
 */
public class ElementAttributeNotADateException extends RuntimeException {

    public ElementAttributeNotADateException(String attribute, String value) {
        super(String.format(Locale.getDefault(), Resources.getString("ElementAttributeNotADateException_ctor_DefaultMessage"), attribute, value));
    }
}
