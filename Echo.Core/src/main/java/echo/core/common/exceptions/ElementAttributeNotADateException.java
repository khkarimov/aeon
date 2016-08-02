package echo.core.common.exceptions;

import echo.core.common.Resources;

/**
 * Created by RafaelT on 7/1/2016.
 */
public class ElementAttributeNotADateException extends RuntimeException{
    public ElementAttributeNotADateException (String attribute, String date) {
        super(String.format(Resources.getString("ElementAttributeNotADateException_ctor_DefaultMessage"), attribute, date));
    }
}
