package echo.selenium.jQuery;

import echo.core.common.helpers.QuadFunction;
import org.joda.time.Duration;

import java.util.UUID;

public class SeleniumCheckInjectJQueryExecutor extends JavaScriptFlowExecutor {
    private Duration timeout;

    public SeleniumCheckInjectJQueryExecutor(IJavaScriptFinalizerFactory finalizerFactory, Duration timeout) {
        super(finalizerFactory);
        this.timeout = timeout;
    }

    @Override
    public QuadFunction<IScriptExecutor, UUID, String, Iterable<Object>, Object> getExecutor() {
        return (executor, guid, script, args) -> {
            if (script.contains("$(") || script.contains("jQuery(")) {
                boolean hasJQuery = (boolean) (executor.ExecuteScript(guid, "if(window.jQuery)return true;return false;"));
                if (hasJQuery) {
                    return executor.ExecuteScript(guid, getFinalizer().apply(JavaScriptFinalizerOptions.None).Prepare(script), args);
                }

                executor.SetTimeout(guid, timeout);
                return executor.ExecuteAsyncScript(guid, getFinalizer().apply(JavaScriptFinalizerOptions.IncludeJQueryInjection).Prepare(script), args);
            }

            return executor.ExecuteScript(guid, getFinalizer().apply(JavaScriptFinalizerOptions.None).Prepare(script), args);
        };
    }
}