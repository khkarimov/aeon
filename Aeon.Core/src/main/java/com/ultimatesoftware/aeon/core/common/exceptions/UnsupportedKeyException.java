package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.KeyboardKey;
import com.ultimatesoftware.aeon.core.common.Resources;

import java.util.Locale;

/**
 * Class that handles the exception thrown when there is an unsupported key.
 */
public class UnsupportedKeyException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link UnsupportedKeyException} class.
     * @param key the input keyboard key.
     */
    public UnsupportedKeyException(KeyboardKey key) {
        super(String.format(Locale.getDefault(), Resources.getString("UnsupportedKeyException_ctor_DefaultMessage"), key));
    }
}
