package com.ultimatesoftware.aeon.core.common.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Performs common routines regarding sleeping.
 */
public class Sleep {

    private static Logger log = LoggerFactory.getLogger(Sleep.class);

    // Time is in milliseconds.
    private static final int TIME_FOR_INTERNAL = 70;

    private static Sleep instance = new Sleep();

    /**
     * Gets the Sleep instance.
     * @return The Sleep instance.
     */
    public static Sleep getInstance() {
        return Sleep.instance;
    }

    /**
     * Sets the Sleep instance.
     * @param sleep The Sleep instance.
     */
    public static void setInstance(Sleep sleep) {
        Sleep.instance = sleep;
    }

    /**
     * Waits. May be used between Internal calls.
     * Default for Internal time is 70.
     */
    public void waitInternal() {
        try {
            Thread.sleep(TIME_FOR_INTERNAL);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);

            Thread.currentThread().interrupt();
        }
    }

    /**
     * Function makes threads wait for a specified amount of time in milliseconds.
     *
     * @param millis the time to wait in milliseconds.
     */
    public void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Function waits for the specified duration in milliseconds.
     *
     * @param duration the time set to wait.
     */
    public void wait(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);

            Thread.currentThread().interrupt();
        }
    }
}
