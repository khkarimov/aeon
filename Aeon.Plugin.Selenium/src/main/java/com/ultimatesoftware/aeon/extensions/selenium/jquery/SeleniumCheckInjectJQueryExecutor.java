package com.ultimatesoftware.aeon.extensions.selenium.jquery;

import com.ultimatesoftware.aeon.extensions.selenium.QuadFunction;

import java.time.Duration;


/**
 * Class to check injected jquery execution flow.
 */
public class SeleniumCheckInjectJQueryExecutor extends JavaScriptFlowExecutor {
    private Duration timeout;
    private final boolean executeAsync;

    /**
     * Constructor for the Selenium check inject jquery executor with given parameters.
     *
     * @param finalizerFactory The javascript finalizer factory to be set.
     * @param timeout          The timeout duration to be set.
     */
    public SeleniumCheckInjectJQueryExecutor(IJavaScriptFinalizerFactory finalizerFactory,
                                             Duration timeout) {
        this(finalizerFactory, timeout, false);
    }

    /**
     * Constructor for the Selenium check inject jquery executor with given parameters.
     *
     * @param finalizerFactory The javascript finalizer factory to be set.
     * @param timeout          The timeout duration to be set.
     * @param executeAsync     Execute scripts asynchronously.
     */
    public SeleniumCheckInjectJQueryExecutor(IJavaScriptFinalizerFactory finalizerFactory,
                                             Duration timeout,
                                             boolean executeAsync) {
        super(finalizerFactory);
        this.timeout = timeout;
        this.executeAsync = executeAsync;
    }

    @Override
    public QuadFunction<IScriptExecutor, String, Iterable<Object>, Object> getExecutor() {
        return (executor, script, args) -> {
            if (script.contains("$(") || script.contains("jquery(")) {
                boolean hasJQuery = (boolean) (executor.executeScript("if(window.jquery)return true;return false;"));
                if (hasJQuery) {
                    if (executeAsync) {
                        return executor.executeAsyncScript(getFinalizer().apply(JavaScriptFinalizerOptions.NONE).prepare(script), args);
                    } else {
                        return executor.executeScript(getFinalizer().apply(JavaScriptFinalizerOptions.NONE).prepare(script), args);
                    }
                }

                executor.setTimeout(timeout);
                return executor.executeAsyncScript(getFinalizer().apply(JavaScriptFinalizerOptions.INCLUDE_JQUERY_INJECTION).prepare(script), args);
            }

            if (executeAsync) {
                return executor.executeAsyncScript(getFinalizer().apply(JavaScriptFinalizerOptions.NONE).prepare(script), args);
            } else {
                return executor.executeScript(getFinalizer().apply(JavaScriptFinalizerOptions.NONE).prepare(script), args);
            }
        };
    }
}
