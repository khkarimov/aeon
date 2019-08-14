package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

import java.io.Serializable;

/**
 * The exception that is thrown when an alert exists.
 */
public class AlertExistsException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link AlertExistsException} class.
     */
    public AlertExistsException() {
        super(Resources.getString("AlertExistsException_ctor_DefaultMessage"));
    }
}
