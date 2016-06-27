package echo.core.common.exceptions;

import echo.core.common.Resources;

/**
 * Created by RafaelT on 6/17/2016.
 */
public class ElementsNotInOrderException extends RuntimeException {
    public ElementsNotInOrderException() {
        super(Resources.getString("ElementsNotInOrderExceptionException_ctor_DefaultMessage"));
    }
}
