package aeon.core.common.exceptions;

import aeon.core.common.Resources;

/**
 * Class that handles the exception thrown when there is an unsupported platform.
 */
public class UnsupportedPlatformException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link UnsupportedPlatformException} class.
     */
    public UnsupportedPlatformException() {
        super(Resources.getString("UnsupportedPlatformException_ctor_DefaultMessage"));
    }
}
