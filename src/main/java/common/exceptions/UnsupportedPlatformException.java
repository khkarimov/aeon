package common.exceptions;

import common.Resources;

/**
 * Created by DionnyS on 4/1/2016.
 */
public class UnsupportedPlatformException extends RuntimeException {
    /**
     * Initializes a new instance of the <see cref="UnsupportedPlatformException"/> class.
     */
    public UnsupportedPlatformException() {
        super(Resources.getString("UnsupportedPlatformException_ctor_DefaultMessage"));
    }
}
