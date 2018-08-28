package aeon.core.common.helpers;

import java.time.Duration;

/**
 * Performs common routines regarding sleeping.
 */
public final class Sleep {

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
            e.printStackTrace();
        }
    }

    /**
     * Function makes the thread wait for the specified duration in miliseconds.
     * @param duration the time set to wait.
     */
    public static void waitDuration(org.joda.time.Duration duration) {
        try {
            Thread.sleep(duration.getMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function makes threads wait for a specified amount of time in miliseconds.
     * @param millis the time to wait in miliseconds.
     */
    public static void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function waits for the specified duration in miliseconds.
     * @param duration the time set to wait.
     */
    public static void wait(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
