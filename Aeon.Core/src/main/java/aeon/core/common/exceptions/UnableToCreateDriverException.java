package aeon.core.common.exceptions;

import aeon.core.common.Resources;

/**
 * Created By DionnyS on 4/1/2016.
 */
public class UnableToCreateDriverException extends RuntimeException {
    /**
     * Initializes a new instance of the {@link UnableToCreateDriverException} class.
     *
     * @param e
     */
    public UnableToCreateDriverException(Exception e) {
        super(Resources.getString("UnableToCreateDriverException_ctor_DefaultMessage"), e);
    }
}
