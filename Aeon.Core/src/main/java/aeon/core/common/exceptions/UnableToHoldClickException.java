package aeon.core.common.exceptions;

import aeon.core.common.Resources;

/**
 * Created By DionnyS on 4/1/2016.
 */
public class UnableToHoldClickException extends RuntimeException {
    /**
     * Initializes a new instance of the {@link UnableToHoldClickException} class.
     */
    public UnableToHoldClickException() {
        super(Resources.getString("UnableToHoldClickException_ctor_DefaultMessage"));
    }
}
