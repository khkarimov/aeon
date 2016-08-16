package echo.selenium.jquery;

import org.joda.time.Duration;

import java.util.UUID;

/**
 * Defines the interface through which the user can execute JavaScript.
 */
public interface IScriptExecutor {
    /**
     * Specifies the amount of time the driver should wait when executing JavaScript asynchronously.
     *
     * @param guid       A globally unique identifier associated with this call.
     * @param timeToWait A <see cref="Duration"/> structure defining the amount of time to wait.
     */
    void SetTimeout(UUID guid, Duration timeToWait);

    /**
     * Executes JavaScript in the context of the currently selected frame or window.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param script The JavaScript code to execute.
     * @param args   The arguments to the script.
     * @return The value returned by the script.
     */
    Object ExecuteScript(UUID guid, String script, Object... args);

    /**
     * Executes JavaScript asynchronously in the context of the currently selected frame or window.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param script The JavaScript code to execute.
     * @param args   The arguments to the script.
     * @return The value returned by the script.
     */
    Object ExecuteAsyncScript(UUID guid, String script, Object... args);
}
