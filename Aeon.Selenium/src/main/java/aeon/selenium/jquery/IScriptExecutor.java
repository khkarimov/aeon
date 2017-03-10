package aeon.selenium.jquery;

import org.joda.time.Duration;


/**
 * Defines the interface through which the user can execute JavaScript.
 */
public interface IScriptExecutor {
    /**
     * Specifies the amount of time the driver should wait when executing JavaScript asynchronously.
     *
     * @param timeToWait A {@link Duration} structure defining the amount of time to wait.
     */
    void SetTimeout(Duration timeToWait);

    /**
     * Executes JavaScript in the context of the currently selected frame or window.
     *
     * @param script The JavaScript code to execute.
     * @param args   The arguments to the script.
     * @return The value returned by the script.
     */
    Object ExecuteScript(String script, Object... args);

    /**
     * Executes JavaScript asynchronously in the context of the currently selected frame or window.
     *
     * @param script The JavaScript code to execute.
     * @param args   The arguments to the script.
     * @return The value returned by the script.
     */
    Object ExecuteAsyncScript(String script, Object... args);
}
