package echo.core.common.exceptions;

import echo.core.common.Resources;

import java.util.Locale;

/**
 * Created by RafaelT on 7/1/2016.
 */
public class ElementAttributeNotADateException extends RuntimeException {
    public ElementAttributeNotADateException(String attribute, String value) {
        super(String.format(Locale.getDefault(), Resources.getString("ElementAttributeNotADateException_ctor_DefaultMessage"), attribute, value));
    }
}
