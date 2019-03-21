package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

import java.io.Serializable;

/**
 * The class to handle not all popup windows closed exception.
 */
public class NotAllPopupWindowsClosedException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link NotAllPopupWindowsClosedException} class.
     */
    public NotAllPopupWindowsClosedException() {
        super(Resources.getString("NotAllPopupWindowsClosedException_ctor_DefaultMessage"));
    }
}
