package aeon.core.common.exceptions;

import aeon.core.common.Resources;
import aeon.core.common.helpers.TimeUtils;
import org.joda.time.Duration;

import java.io.Serializable;

/**
 * Created By DionnyS on 4/14/2016.
 */
public class TimeoutExpiredException extends RuntimeException implements Serializable {

    private final String operation;
    private final Duration timeout;

    public TimeoutExpiredException(String operation, Duration timeout, RuntimeException lastCaughtException) {
        super(String.format(
                Resources.getString("TimeoutExpiredException_ctor_DefaultMessage"),
                TimeUtils.toReadableString(timeout), operation),
                lastCaughtException);

        this.operation = operation;
        this.timeout = timeout;
    }

    public String getOperation() {
        return operation;
    }

    public Duration getTimeout() {
        return timeout;
    }
}
