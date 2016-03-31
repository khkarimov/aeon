package common.exceptions;

import common.Resources;

import java.io.Serializable;

public class NoSuchWindowException extends RuntimeException implements Serializable {
    private String window;

    /**
     * Initializes a new instance of the <see cref="NoSuchWindowException"/> class.
     *
     * @param window The window.
     */
    public NoSuchWindowException(String window) {
        super(String.format(Resources.getString("NoSuchWindowException_ctor_DefaultMessage"), window));
        this.window = window;
    }
}