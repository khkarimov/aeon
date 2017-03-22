package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created By SebastianR on 6/28/2016.
 */
public class ContainsWhiteSpaceException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ContainsWhiteSpaceException} class.
     */
    public ContainsWhiteSpaceException(String value) {
        super(String.format(Locale.getDefault(),Resources.getString("Argument_StringContainsWhiteSpace"),value));
    }
}
