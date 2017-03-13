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
                boolean hasJQuery = (boolean) (executor.ExecuteScript("if(window.jquery)return true;return false;"));
                if (hasJQuery) {
                    return executor.ExecuteScript(getFinalizer().apply(JavaScriptFinalizerOptions.None).Prepare(script), args);
                }

                executor.SetTimeout(timeout);
                return executor.ExecuteAsyncScript(getFinalizer().apply(JavaScriptFinalizerOptions.IncludeJQueryInjection).Prepare(script), args);
            }

            return executor.ExecuteScript(getFinalizer().apply(JavaScriptFinalizerOptions.None).Prepare(script), args);
        };
    }
}
