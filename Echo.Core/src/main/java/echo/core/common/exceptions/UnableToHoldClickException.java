package echo.core.common.exceptions;

import echo.core.common.Resources;

/**
 * Created by DionnyS on 4/1/2016.
 */
public class UnableToHoldClickException extends RuntimeException {
    /**
     * Initializes a new instance of the <see cref="AlertExistsException"/> class.
     */
    public UnableToHoldClickException() {
        super(Resources.getString("UnableToHoldClickException_ctor_DefaultMessage"));
    }
}
