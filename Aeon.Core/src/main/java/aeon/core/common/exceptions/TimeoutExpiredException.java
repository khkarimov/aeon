package aeon.core.common.exceptions;

import aeon.core.common.Resources;
import aeon.core.common.helpers.TimeUtils;
import org.joda.time.Duration;

import java.io.Serializable;

/**
 * This exception is thrown after an operation times out.
 */
public class TimeoutExpiredException extends RuntimeException implements Serializable {

    private final String operation;
    private final Duration timeout;

    /**
     * Initializes a new instance of the {@link TimeoutExpiredException} class.
     *
     * @param operation string to specify the operation.
     * @param timeout the duration before timeout.
     */
    public TimeoutExpiredException(String operation, Duration timeout) {
        super(String.format(
                Resources.getString("TimeoutExpiredException_ctor_DefaultMessage"),
                TimeUtils.toReadableString(timeout), operation), null);

        this.operation = operation;
        this.timeout = timeout;
    }

    /**
     * Gets the operation.
     *
     * @return the string operation.
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Gets the timeout duration.
     *
     * @return the timeout duration.
     */
    public Duration getTimeout() {
        return timeout;
    }
}
