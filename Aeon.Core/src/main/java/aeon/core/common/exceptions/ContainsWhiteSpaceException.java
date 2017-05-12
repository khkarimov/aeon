package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.io.Serializable;
import java.util.Locale;

/**
 * Exception thrown when white spaced are found in a string that should not contain them.
 */
public class ContainsWhiteSpaceException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ContainsWhiteSpaceException} class.
     *
     * @param value The string value that contained white spaces.
     */
    public ContainsWhiteSpaceException(String value) {
        super(String.format(Locale.getDefault(), Resources.getString("Argument_StringContainsWhiteSpace"), value));
    }
}
