package aeon.core.common.exceptions;

import aeon.core.common.Resources;

/**
 * Created By DionnyS on 4/1/2016.
 */
public class UnsupportedPlatformException extends RuntimeException {
    /**
     * Initializes a new instance of the {@link UnsupportedPlatformException} class.
     */
    public UnsupportedPlatformException() {
        super(Resources.getString("UnsupportedPlatformException_ctor_DefaultMessage"));
    }
}
