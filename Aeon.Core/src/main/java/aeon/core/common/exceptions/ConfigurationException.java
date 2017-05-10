package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.io.Serializable;

/**
 * Created by DionnyS on 4/1/2016.
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
