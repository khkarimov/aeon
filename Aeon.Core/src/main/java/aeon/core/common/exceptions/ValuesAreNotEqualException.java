package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.io.Serializable;
import java.util.Locale;

/**
 * Class that handles the exception thrown when the values are not equal.
 */
public class ValuesAreNotEqualException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ValuesAreNotEqualException} class.
     * @param actualValue the inut actual value.
     * @param expectedValue the input expected value.
     */
    public ValuesAreNotEqualException(String actualValue, String expectedValue) {
        super(String.format(Locale.getDefault(), Resources.getString("ValuesAreNotEqualException_ctor_DefaultMessage"), expectedValue, actualValue));
    }
}
