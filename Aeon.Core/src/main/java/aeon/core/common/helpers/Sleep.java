package aeon.core.common.helpers;

import java.time.Duration;

/**
 * Performs common routines regarding sleeping.
 */
public final class Sleep {

    // Time is in milliseconds.
    private static final int TimeForBrowser = 1000;
    private static final int TimeForInternal = 70;
    private static final int TimeForInternalLong = 500;
    private static final int TimeForOperatingSystem = 2000;
    private static final int TimeForDriverQuit = 5000;

    /**
     * Waits. May be used between browser calls.
     */
    public static void waitBrowser() {
        try {
            Thread.sleep(TimeForBrowser);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Waits. May be used between OS calls.
     */
    public static void waitOperatingSystem() {
        try {
            Thread.sleep(TimeForOperatingSystem);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

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
     * Waits. May be used between Internal Long calls.
     * Default for InternalLong time is 500.
     */
    public static void waitInternalLong() {
        try {
            Thread.sleep(TimeForInternalLong);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function waits for the time required for the driver to quit.
     * Default for DriverQuit time is 5000.
     */
    public static void waitDriverQuit() {
        try {
            Thread.sleep(TimeForDriverQuit);
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
    public static void waitDuration(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
