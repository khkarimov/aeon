package echo.core.common.exceptions;

import echo.core.common.Resources;

import java.util.Collection;

/**
 * Created by RafaelT on 6/28/2016.
 */
public class ElementDoesNotOnlyHaveException extends RuntimeException {
    public ElementDoesNotOnlyHaveException(Collection<String> message) {
        super(Resources.getString("ElementDoesNotOnlyHaveException_ctor_DefaultMessage") + " " + message);
    }
}
