package aeon.selenium.appium.jquery;

import aeon.core.common.helpers.QuadFunction;
import org.joda.time.Duration;


/**
 * Class to check injected jquery execution flow.
 */
public class SeleniumCheckInjectJQueryExecutor extends JavaScriptFlowExecutor {
    private Duration timeout;

    /**
     * Constructor for the selenum check inject jquery executor with given parameters.
     * @param finalizerFactory The javascript finalizer factory to be set.
     * @param timeout The timeout duration to be set.
     */
    public SeleniumCheckInjectJQueryExecutor(IJavaScriptFinalizerFactory finalizerFactory, Duration timeout) {
        super(finalizerFactory);
        this.timeout = timeout;
    }

    @Override
    public QuadFunction<IScriptExecutor, String, Iterable<Object>, Object> getExecutor() {
        return (executor, script, args) -> {
            if (script.contains("$(") || script.contains("jquery(")) {
                boolean hasJQuery = (boolean) (executor.executeScript("if(window.jquery)return true;return false;"));
                if (hasJQuery) {
                    return executor.executeScript(getFinalizer().apply(JavaScriptFinalizerOptions.None).prepare(script), args);
                }

                executor.setTimeout(timeout);
                return executor.executeAsyncScript(getFinalizer().apply(JavaScriptFinalizerOptions.IncludeJQueryInjection).prepare(script), args);
            }

            return executor.executeScript(getFinalizer().apply(JavaScriptFinalizerOptions.None).prepare(script), args);
        };
    }
}
