package echo.core.common.exceptions;

import echo.core.common.Resources;

/**
 * Created by RafaelT on 6/1/2016.
 */
public class ElementDoesNotHaveOptionException extends RuntimeException {
    public ElementDoesNotHaveOptionException () {
        super(Resources.getString("ElementDoesNotHaveOptionException_ctor_DefaultMessage"));
    }
}
