package echo.core.common.exceptions;

import echo.core.common.Resources;

/**
 * Created by RafaelT on 6/14/2016.
 */
public class ElementDoesNotHaveNumberOfOptionsException extends RuntimeException {
    public ElementDoesNotHaveNumberOfOptionsException(int actualValue, int expectedValue) {
        super(String.format(Resources.getString("ElementDoesNotHaveNumberOfOptionsException_ctor_DefaultMessage"),expectedValue,actualValue));
    }
}
