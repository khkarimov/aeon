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

    public static void waitInternal() {
        try {
            Thread.sleep(TimeForInternal);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitInternalLong() {
        try {
            Thread.sleep(TimeForInternalLong);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitDriverQuit() {
        try {
            Thread.sleep(TimeForDriverQuit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitDuration(org.joda.time.Duration duration) {
        try {
            Thread.sleep(duration.getMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitDuration(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
