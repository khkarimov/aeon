package echo.core.common.exceptions;

import echo.core.common.Resources;

/**
 * Created by RafaelT on 6/2/2016.
 */
public class ElementHasOptionException extends RuntimeException {
    public ElementHasOptionException() {
        super(Resources.getString("ElementHasOptionException_ctor_DefaultMessage"));
    }
}
