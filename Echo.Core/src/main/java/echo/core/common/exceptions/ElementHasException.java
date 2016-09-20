package echo.core.common.exceptions;

import echo.core.common.Resources;

/**
 * Created by RafaelT on 6/28/2016.
 */
public class ElementHasException extends RuntimeException {
    public ElementHasException(String message) {
        super(Resources.getString("ElementHasException_ctor_DefaultMessage") + " " + message);
    }
}
