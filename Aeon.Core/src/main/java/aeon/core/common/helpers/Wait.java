package aeon.core.common.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * Utility class for waiting capabilities.
 *
 * Can be used to wait for successful task completion or assertions.
 */
public class Wait {

    private static Logger log = LogManager.getLogger(Wait.class);

    /**
     * Repeatedly executes a task until it completes successfully, ie without throwing an exception.
     *
     * The task is retried in the given interval until it completes successfully or the timeout is reached. The latter
     * case will bubble up the last thrown exception.
     *
     * @param task      The task to execute.
     * @param timeout   Maximum time to wait in seconds.
     * @param interval  The interval in which to execute the task in seconds.
     */
    public static void forSuccess(Runnable task, int timeout, int interval) {

        long startTime = System.currentTimeMillis();
        while (true) {

            try {
                task.run();

                return;
            } catch (RuntimeException e) {

                if (startTime + timeout * 1000 >= System.currentTimeMillis()) {
                    throw e;
                }

                log.trace(e);

                Sleep.wait(interval * 1000);
            }
        }
    }

    /**
     * Repeatedly executes a task until it completes successfully, ie without throwing an exception.
     *
     * The task is retried every second until it completes successfully or the timeout is reached. The latter
     * case will bubble up the last thrown exception.
     *
     * @param task      The task to execute.
     * @param timeout   Maximum time to wait in seconds.
     */
    public static void forSuccess(Runnable task, int timeout) {

        forSuccess(task, timeout, 1000);
    }

    /**
     * Repeatedly executes a task until it returns true.
     *
     * The task is retried in the given interval until it returns true or the timeout is reached. The latter
     * case will bubble up the last thrown exception.
     *
     * @param task      The task to execute.
     * @param timeout   Maximum time to wait in seconds.
     * @param interval  The interval in which to execute the task in seconds.
     */
    public static void forSuccess(BooleanSupplier task, int timeout, int interval) {

        forSuccess(() -> {
            if (!task.getAsBoolean()) {
                throw new RuntimeException("Found false instead of true for task");
            }
        }, timeout, interval);
    }

    /**
     * Repeatedly executes a task until it returns true.
     *
     * The task is retried every second until it returns true or the timeout is reached. The latter
     * case will bubble up the last thrown exception.
     *
     * @param task      The task to execute.
     * @param timeout   Maximum time to wait in seconds.
     */
    public static void forSuccess(BooleanSupplier task, int timeout) {

        forSuccess(task, timeout, 1000);
    }

    /**
     * Repeatedly executes a task until it returns the expected value.
     *
     * The task is retried in the given interval until it returns the expected value or the timeout is reached.
     * The latter case will bubble up the last thrown exception.
     *
     * Please note that is using "==" instead of "equals" for comparing the values.
     *
     * @param task      The task to execute.
     * @param value     Expected value.
     * @param timeout   Maximum time to wait in seconds.
     * @param interval  The interval in which to execute the task in seconds.
     * @param <T>       The type of the expected value.
     */
    public static <T> void forValue(Supplier<T> task, T value, int timeout, int interval) {

        forSuccess(() -> {
            T result = task.get();

            if (result != value) {
                throw new RuntimeException(
                        String.format("Expected \"%s\", but found \"%s\" for task", value, result));
            }
        }, timeout, interval);
    }

    /**
     * Repeatedly executes a task until it returns the expected value.
     *
     * The task is retried every second until it returns the expected value or the timeout is reached.
     * The latter case will bubble up the last thrown exception.
     *
     * Please note that is using "==" instead of "equals" for comparing the values.
     *
     * @param task      The task to execute.
     * @param value     Expected value.
     * @param timeout   Maximum time to wait in seconds.
     * @param <T>       The type of the expected value.
     */
    public static <T> void forValue(Supplier<T> task, T value, int timeout) {

        forValue(task, value, timeout, 1000);
    }

    /**
     * Repeatedly executes a task until it returns the expected string.
     *
     * The task is retried in the given interval until it returns the expected string or the timeout is reached.
     * The latter case will bubble up the last thrown exception.
     *
     * @param task      The task to execute.
     * @param value     Expected string.
     * @param timeout   Maximum time to wait in seconds.
     * @param interval  The interval in which to execute the task in seconds.
     */
    public static void forValue(Supplier<String> task, String value, int timeout, int interval) {

        forSuccess(() -> {
            String result = task.get();
            if (!result.equals(value)) {
                throw new RuntimeException(
                        String.format("Expected \"%s\", but found \"%s\"", value, result));
            }
        }, timeout, interval);
    }

    /**
     * Repeatedly executes a task until it returns the expected string.
     *
     * The task is retried every second until it returns the expected string or the timeout is reached.
     * The latter case will bubble up the last thrown exception.
     *
     * @param task      The task to execute.
     * @param value     Expected string.
     * @param timeout   Maximum time to wait in seconds.
     */
    public static void forValue(Supplier<String> task, String value, int timeout) {

        forValue(task, value, timeout, 1000);
    }
}
