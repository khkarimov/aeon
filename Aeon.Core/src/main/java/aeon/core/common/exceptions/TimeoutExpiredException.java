package aeon.core.common.exceptions;

import aeon.core.common.Resources;
import aeon.core.common.helpers.TimeUtils;
import org.joda.time.Duration;

import java.io.Serializable;

/**
 * Created by DionnyS on 4/14/2016.
 */
public class TimeoutExpiredException extends RuntimeException implements Serializable {

    private final String operation;
    private final Duration timeout;

    /**
     * Initializes a new instance of the {@link TimeoutExpiredException} class.
     * @param operation string to specify the operation.
     * @param timeout the duration before timeout.
     * @param lastCaughtException the last caught exception.
     */
    public TimeoutExpiredException(String operation, Duration timeout, RuntimeException lastCaughtException) {
        super(String.format(
                Resources.getString("TimeoutExpiredException_ctor_DefaultMessage"),
                TimeUtils.toReadableString(timeout), operation),
                lastCaughtException);

        this.operation = operation;
        this.timeout = timeout;
    }

    /**
     * Gets the operation.
     * @return the string operation.
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Gets the timout duration.
     * @return the timeout duration.
     */
    public Duration getTimeout() {
        return timeout;
    }
}
