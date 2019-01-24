package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.util.Locale;

/**
 * Class that handles the exception thrown when there is an incorrect element tag.
 */
public class IncorrectElementTagException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link IncorrectElementTagException} class.
     * @param expectedTagName the expected string tag name.
     * @param actualTagName the actual string tag name.
     */
    public IncorrectElementTagException(String expectedTagName, String actualTagName) {
        super(String.format(Locale.getDefault(), Resources.getString("IncorrectElementTagException_ctor_DefaultMessage"), actualTagName, expectedTagName));
    }
}
