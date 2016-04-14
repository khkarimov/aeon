package echo.core.common.helpers;

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
    public static void WaitBrowser() {
        try {
            Thread.sleep(TimeForBrowser);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Waits. May be used between OS calls.
     */
    public static void WaitOperatingSystem() {
        try {
            Thread.sleep(TimeForOperatingSystem);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void WaitInternal() {
        try {
            Thread.sleep(TimeForInternal);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void WaitInternalLong() {
        try {
            Thread.sleep(TimeForInternalLong);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void WaitDriverQuit() {
        try {
            Thread.sleep(TimeForDriverQuit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void WaitDuration(org.joda.time.Duration duration) {
        try {
            Thread.sleep(duration.getMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void Wait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void WaitDuration(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
