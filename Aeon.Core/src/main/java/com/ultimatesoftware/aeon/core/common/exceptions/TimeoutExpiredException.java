package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;

import java.io.Serializable;
import java.time.Duration;

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
     * @param timeout   the duration before timeout.
     */
    public TimeoutExpiredException(String operation, Duration timeout) {
        super(String.format(
                Resources.getString("TimeoutExpiredException_ctor_DefaultMessage"),
                toReadableString(timeout), operation), null);

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

    /**
     * Translates a Duration to a readable string.
     *
     * @param duration the input Duration to be translated.
     * @return the new readable string of duration.
     */
    private static String toReadableString(Duration duration) {

        if (duration.toMinutes() == 0) {
            return String.format("0:%s", duration.getSeconds());
        }

        return String.format("%s:%s",
                duration.toMinutes(),
                duration.getSeconds() % duration.toMinutes() * 60);
    }
}
