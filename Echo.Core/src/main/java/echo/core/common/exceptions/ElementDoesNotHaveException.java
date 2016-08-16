package echo.core.common.exceptions;

import echo.core.common.Resources;

/**
 * Created by RafaelT on 6/28/2016.
 */
public class ElementDoesNotHaveException extends RuntimeException {
    public ElementDoesNotHaveException(String message) {
        super(Resources.getString("ElementDoesNotHaveException_ctor_DefaultMessage") + " " + message);
    }
}