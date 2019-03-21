package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

import java.io.Serializable;
import java.util.Locale;

/**
 * The exception that is thrown when a select element is not supported.
 */
public class UnsupportedElementException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link UnsupportedElementException} class.
     *
     * @param forType The select element object's {@code Type} which is not supported.
     */
    public UnsupportedElementException(Class forType) {
        super(String.format(Locale.getDefault(), Resources.getString("UnsupportedElementException_ctor_DefaultMessage"), forType));
    }
}
