package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.io.Serializable;

/**
 * The exception that is thrown when there is a problem in the Configuration.
 */
public class ConfigurationException extends RuntimeException implements Serializable {

    private String key, source, reason;

    /**
     * Initializes a new instance of the {@link ConfigurationException} class.
     *
     * @param key    The key.
     * @param source The source.
     * @param reason The reason.
     */
    public ConfigurationException(String key, String source, String reason) {
        super(String.format(Resources.getString("ConfigurationException_ctor_DefaultMessage"), key, source, reason));
        this.key = key;
        this.source = source;
        this.reason = reason;
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
        this.key = key;
        this.source = source;
        this.reason = reason;
    }

    /**
     * Function gets the key and returns it as a string.
     * @return the key as a string.
     */
    public String getKey() {
        return key;
    }

    /**
     * Function sets the key of the configuration exception given a string.
     * @param key the string parameter so set the key.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Function gets the source and returns it as a string.
     * @return the source as a string.
     */
    public String getSource() {
        return source;
    }

    /**
     * Function sets the source of the configuration exception given a string.
     * @param source the string parameter so set the source.
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Function gets the reason and returns it as a string.
     * @return the reason as a string.
     */
    public String getReason() {
        return reason;
    }

    /**
     * Function sets the Reason of the configuration exception given a string.
     * @param reason the string parameter so set the reason.
     */
    public void setReason(String reason) {
        this.reason = reason;
    }
}
