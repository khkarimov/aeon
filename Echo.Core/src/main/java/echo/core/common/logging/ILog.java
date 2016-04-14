package echo.core.common.logging;

import java.awt.Image;

import java.util.UUID;

/**
 * The logger.
 */
public interface ILog {
    /**
     * Writes a debug message to the log.
     *
     * @param guid    The globally unique identifier of the log event.
     * @param message The message to write to the log.
     */
    void Debug(UUID guid, String message);

    /**
     * Writes an error message to the log.
     *
     * @param guid    The globally unique identifier of the log event.
     * @param message The message to write to the log.
     */
    void Error(UUID guid, String message);

    /**
     * Writes the exception to the log so it has the stack trace.
     *
     * @param message The stack trace message.
     * @param e       The caught exception.
     */
    void Error(String message, RuntimeException e);

    /**
     * Writes an error message with running system process list to the log.
     *
     * @param guid             The globally unique identifier of the log event.
     * @param message          The message to write to the log.
     * @param runningProcesses An enumerable collection of running system processes.
     */
    void Error(UUID guid, String message, Iterable<String> runningProcesses);

    /**
     * Writes an error message with an embedded screenshot and running system process list to the log.
     *
     * @param guid             The globally unique identifier of the log event.
     * @param message          The message to write to the log.
     * @param screenshot       The screenshot to embed with the message.
     * @param runningProcesses An enumerable collection of running system processes.
     */
    void Error(UUID guid, String message, Image screenshot, Iterable<String> runningProcesses);

    /**
     * Writes a information message to the log.
     *
     * @param guid    The globally unique identifier of the log event.
     * @param message The message to write to the log.
     */
    void Info(UUID guid, String message);

    /**
     * Writes a trace message to the log.
     *
     * @param guid    The globally unique identifier of the log event.
     * @param message The message to write to the log.
     */
    void Trace(UUID guid, String message);

    /**
     * Writes a trace message with an embedded page source in plain text.
     *
     * @param guid       The globally unique identifier of the log event.
     * @param message    The message to write to the log.
     * @param pageSource The string containing the page source to embed with the message.
     */
    void Trace(UUID guid, String message, String pageSource);
}
