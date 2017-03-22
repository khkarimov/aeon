package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.util.Locale;

/**
 * Created By RafaelT on 7/1/2016.
 */
public class ElementAttributeNotADateException extends RuntimeException {
    public ElementAttributeNotADateException(String attribute, String value) {
        super(String.format(Locale.getDefault(), Resources.getString("ElementAttributeNotADateException_ctor_DefaultMessage"), attribute, value));
    }
}
