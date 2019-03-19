package aeon.core.common.exceptions;

import aeon.core.common.Resources;

/**
 * The exception that is thrown when a native selector cannot run a command.
 */
public class NativeSelectorCommandException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link NativeSelectorCommandException} class.
     *
     * @param message The error message
     */
    public NativeSelectorCommandException(String message) {
        super(String.format(Resources.getString("NativeSelectorCommandException_ctor_DefaultMessage"), message));
    }
}
