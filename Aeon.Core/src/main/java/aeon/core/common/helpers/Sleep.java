package aeon.core.common.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Performs common routines regarding sleeping.
 */
public final class Sleep {

    private static Logger log = LogManager.getLogger(Sleep.class);

    // Time is in milliseconds.
    private static final int TimeForInternal = 70;

    /**
     * Waits. May be used between Internal calls.
     * Default for Internal time is 70.
     */
    public static void waitInternal() {
        try {
            Thread.sleep(TimeForInternal);
        } catch (InterruptedException e) {
            log.error(e);

            Thread.currentThread().interrupt();
        }
    }

    /**
     * Function makes the thread wait for the specified duration in milliseconds.
     *
     * @param duration the time set to wait.
     */
    public static void waitDuration(org.joda.time.Duration duration) {
        try {
            Thread.sleep(duration.getMillis());
        } catch (InterruptedException e) {
            log.error(e);

            Thread.currentThread().interrupt();
        }
    }

    /**
     * Function makes threads wait for a specified amount of time in milliseconds.
     *
     * @param millis the time to wait in milliseconds.
     */
    public static void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error(e);

            Thread.currentThread().interrupt();
        }
    }
}
