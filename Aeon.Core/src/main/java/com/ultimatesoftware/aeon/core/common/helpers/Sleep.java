package com.ultimatesoftware.aeon.core.common.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Performs common routines regarding sleeping.
 */
public final class Sleep {

    private static Logger log = LoggerFactory.getLogger(Sleep.class);

    // Time is in milliseconds.
    private static final int TIME_FOR_INTERNAL = 70;

    /**
     * Default constructor to prevent Java from adding an implicit public constructor.
     */
    private Sleep(){
        throw new IllegalStateException("Illegal instantiation of Sleep!");
    }

    /**
     * Waits. May be used between Internal calls.
     * Default for Internal time is 70.
     */
    public static void waitInternal() {
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
    public static void wait(int millis) {
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
    public static void wait(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);

            Thread.currentThread().interrupt();
        }
    }
}
