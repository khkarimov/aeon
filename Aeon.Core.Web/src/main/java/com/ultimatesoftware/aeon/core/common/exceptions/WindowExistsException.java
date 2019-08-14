package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

import java.io.Serializable;
import java.util.Locale;

/**
 * Class that handles the exception thrown when there is a windows exists exception.
 */
public class WindowExistsException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link WindowExistsException} class.
     * @param window the string input of the window.
     */
    public WindowExistsException(String window) {
        super(String.format(Locale.getDefault(), Resources.getString("WindowExistsException_ctor_DefaultMessage"), window));
    }
}
