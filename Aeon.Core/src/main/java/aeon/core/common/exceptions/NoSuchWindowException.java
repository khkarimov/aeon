package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.io.Serializable;
import java.util.Locale;

/**
 * The class for a no such window exception.
 */
public class NoSuchWindowException extends RuntimeException implements Serializable {

    private String window;

    /**
     * Initializes a new instance of the {@link NoSuchWindowException} class.
     *
     * @param window The window.
     */
    public NoSuchWindowException(String window) {
        super(String.format(Locale.getDefault(), Resources.getString("NoSuchWindowException_ctor_DefaultMessage"), window));
        this.window = window;
    }
}
