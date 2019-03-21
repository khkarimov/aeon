package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

import java.util.Locale;

/**
 * The exception that is thrown when the element is not a date.
 */
public class ElementAttributeNotADateException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link ElementAttributeNotADateException} class.
     *
     * @param attribute the string element.
     * @param value     the value.
     */
    public ElementAttributeNotADateException(String attribute, String value) {
        super(String.format(Locale.getDefault(), Resources.getString("ElementAttributeNotADateException_ctor_DefaultMessage"), attribute, value));
    }
}
