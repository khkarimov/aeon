package aeon.selenium.jquery;

import aeon.core.common.helpers.QuadFunction;
import org.joda.time.Duration;


public class SeleniumCheckInjectJQueryExecutor extends JavaScriptFlowExecutor {
    private Duration timeout;

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
