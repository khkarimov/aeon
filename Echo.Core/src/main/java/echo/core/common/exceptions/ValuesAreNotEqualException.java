package echo.core.common.exceptions;

import echo.core.common.Resources;

/**
 * Created by RafaelT on 6/28/2016.
 */
public class ValuesAreNotEqualException extends RuntimeException {
    public ValuesAreNotEqualException (String expectedvalue, String value, String attribute) {
        super(Resources.getString("ElementHasException_ctor_DefaultMessage" + " " + expectedvalue + " " + value + " " + attribute));
    }
}
