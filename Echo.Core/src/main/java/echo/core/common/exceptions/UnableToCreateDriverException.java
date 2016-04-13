package echo.core.common.exceptions;

import echo.core.common.Resources;

/**
 * Created by DionnyS on 4/1/2016.
 */
public class UnableToCreateDriverException extends RuntimeException {
    /**
     * Initializes a new instance of the <see cref="UnableToCreateDriverException"/> class.
     * @param e
     */
    public UnableToCreateDriverException(Exception e) {
        super(Resources.getString("UnableToCreateDriverException_ctor_DefaultMessage"), e);
    }
}
