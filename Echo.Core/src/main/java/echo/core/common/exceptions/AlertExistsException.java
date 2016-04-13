package echo.core.common.exceptions;

import echo.core.common.Resources;

import java.io.Serializable;

/**
 * The exception that is thrown when an alert exists.
 */
public class AlertExistsException extends RuntimeException implements Serializable {
    /**
     * Initializes a new instance of the <see cref="AlertExistsException"/> class.
     */
    public AlertExistsException() {
        super(Resources.getString("AlertExistsException_ctor_DefaultMessage"));
    }
}
