package aeon.core.common.exceptions;

import aeon.core.common.Resources;

/**
 * The exception that is thrown when a native selector cannot be converted.
 */
public class NativeSelectorConversionException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link NativeSelectorConversionException} class.
     *
     * @param message The error message
     */
    public NativeSelectorConversionException(String message) {
        super(String.format(Resources.getString("NativeSelectorConversionException_ctor_DefaultMessage"), message));
    }
}
