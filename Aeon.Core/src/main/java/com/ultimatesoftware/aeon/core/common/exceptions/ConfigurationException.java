package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

import java.io.Serializable;

/**
 * The exception that is thrown when there is a problem in the Configuration.
 */
public class ConfigurationException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ConfigurationException} class.
     *
     * @param key    The key.
     * @param source The source.
     * @param reason The reason.
     */
    public ConfigurationException(String key, String source, String reason) {
        super(String.format(Resources.getString("ConfigurationException_ctor_DefaultMessage"), key, source, reason));
    }

    /**
     * Initializes a new instance of the {@link ConfigurationException} class.
     *
     * @param key            The key.
     * @param source         The source.
     * @param reason         The reason.
     * @param innerException The exception that is the cause of the current exception, or a null reference (Nothing in Visual Basic) if no inner exception is specified.
     */
    public ConfigurationException(String key, String source, String reason, RuntimeException innerException) {
        super(String.format(Resources.getString("ConfigurationException_ctor_DefaultMessage"), key, source, reason), innerException);
    }
}
