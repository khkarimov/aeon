package com.ultimatesoftware.aeon.core.common.helpers;

import com.ultimatesoftware.aeon.core.common.exceptions.TimeoutExpiredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * Utility class for waiting capabilities.
 * <p>
 * Can be used to wait for successful task completion or assertions.
 */
public class Wait {

    private static Logger log = LoggerFactory.getLogger(Wait.class);

    /**
     * Private constructor to prevent instantiation.
     */
    private Wait() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Repeatedly executes a task until it completes successfully, ie without throwing an exception.
     * <p>
     * The task is retried in the given interval until it completes successfully or the timeout is reached. The latter
     * case will bubble up the last thrown exception.
     *
     * @param task     The task to execute.
     * @param timeout  Maximum time to wait.
     * @param interval The interval in which to execute the task.
     */
    public static void forSuccess(Runnable task, Duration timeout, Duration interval) {

        long startTime = System.currentTimeMillis();
        while (true) {

            try {
                task.run();

                return;
            } catch (Throwable e) {

                if (startTime + timeout.toMillis() < System.currentTimeMillis()) {
                    throw e;
                }

                log.trace(e.getMessage(), e);

                Sleep.wait(interval);
            }
        }
    }

    /**
     * Repeatedly executes a task until it completes successfully, ie without throwing an exception.
     * <p>
     * The task is retried every second until it completes successfully or the timeout is reached. The latter
     * case will bubble up the last thrown exception.
     *
     * @param task    The task to execute.
     * @param timeout Maximum time to wait.
     */
    public static void forSuccess(Runnable task, Duration timeout) {

        forSuccess(task, timeout, Duration.ofMillis(1000));
    }

    /**
     * Repeatedly executes a task until it returns true.
     * <p>
     * The task is retried in the given interval until it returns true or the timeout is reached. The latter
     * case will bubble up the last thrown exception.
     *
     * @param task     The task to execute.
     * @param timeout  Maximum time to wait.
     * @param interval The interval in which to execute the task.
     */
    public static void forSuccess(BooleanSupplier task, Duration timeout, Duration interval) {

        forSuccess(() -> {
            if (!task.getAsBoolean()) {
                throw new TimeoutExpiredException("Found false instead of true for task", timeout);
            }
        }, timeout, interval);
    }

    /**
     * Repeatedly executes a task until it returns true.
     * <p>
     * The task is retried every second until it returns true or the timeout is reached. The latter
     * case will bubble up the last thrown exception.
     *
     * @param task    The task to execute.
     * @param timeout Maximum time to wait.
     */
    public static void forSuccess(BooleanSupplier task, Duration timeout) {

        forSuccess(task, timeout, Duration.ofMillis(1000));
    }

    /**
     * Repeatedly executes a task until it returns the expected value.
     * <p>
     * The task is retried in the given interval until it returns the expected value or the timeout is reached.
     * The latter case will bubble up the last thrown exception.
     * <p>
     * Please note that is using "==" instead of "equals" for comparing the values.
     *
     * @param task     The task to execute.
     * @param value    Expected value.
     * @param timeout  Maximum time to wait.
     * @param interval The interval in which to execute the task.
     * @param <T>      The type of the expected value.
     */
    public static <T> void forValue(Supplier<T> task, T value, Duration timeout, Duration interval) {

        forSuccess(() -> {
            T result = task.get();

            if (result != value) {
                throw new TimeoutExpiredException(
                        String.format("Expected \"%s\", but found \"%s\" for task", value, result), timeout);
            }
        }, timeout, interval);
    }

    /**
     * Repeatedly executes a task until it returns the expected value.
     * <p>
     * The task is retried every second until it returns the expected value or the timeout is reached.
     * The latter case will bubble up the last thrown exception.
     * <p>
     * Please note that is using "==" instead of "equals" for comparing the values.
     *
     * @param task    The task to execute.
     * @param value   Expected value.
     * @param timeout Maximum time to wait.
     * @param <T>     The type of the expected value.
     */
    public static <T> void forValue(Supplier<T> task, T value, Duration timeout) {

        forValue(task, value, timeout, Duration.ofMillis(1000));
    }

    /**
     * Repeatedly executes a task until it returns the expected string.
     * <p>
     * The task is retried in the given interval until it returns the expected string or the timeout is reached.
     * The latter case will bubble up the last thrown exception.
     *
     * @param task     The task to execute.
     * @param value    Expected string.
     * @param timeout  Maximum time to wait.
     * @param interval The interval in which to execute the task.
     */
    public static void forValue(Supplier<String> task, String value, Duration timeout, Duration interval) {

        forSuccess(() -> {
            String result = task.get();
            if (!result.equals(value)) {
                throw new TimeoutExpiredException(
                        String.format("Expected \"%s\", but found \"%s\"", value, result), timeout);
            }
        }, timeout, interval);
    }

    /**
     * Repeatedly executes a task until it returns the expected string.
     * <p>
     * The task is retried every second until it returns the expected string or the timeout is reached.
     * The latter case will bubble up the last thrown exception.
     *
     * @param task    The task to execute.
     * @param value   Expected string.
     * @param timeout Maximum time to wait.
     */
    public static void forValue(Supplier<String> task, String value, Duration timeout) {

        forValue(task, value, timeout, Duration.ofMillis(1000));
    }
}
