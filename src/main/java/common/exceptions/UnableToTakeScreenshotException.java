package common.exceptions;

import common.Resources;

/**
 * Created by DionnyS on 4/1/2016.
 */
public class UnableToTakeScreenshotException extends RuntimeException {
    /**
     * Initializes a new instance of the <see cref="AlertExistsException"/> class.
     */
    public UnableToTakeScreenshotException() {
        super(Resources.getString("UnableToTakeScreenshotException_ctor_DefaultMessage"));
    }
}