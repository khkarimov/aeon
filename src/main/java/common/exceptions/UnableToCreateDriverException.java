package common.exceptions;

import common.Resources;

import java.io.IOException;

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
