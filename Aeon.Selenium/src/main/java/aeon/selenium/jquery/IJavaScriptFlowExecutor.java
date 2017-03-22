package aeon.selenium.jquery;

import aeon.core.common.helpers.QuadFunction;

import java.util.function.Function;

/**
 * Interface for executing JavaScript.
 */
public interface IJavaScriptFlowExecutor {
    /**
     * Gets a function return a finalizer based upon the given option.
     */
    Function<JavaScriptFinalizerOptions, IJavaScriptFinalizer> getFinalizer();

    /**
     * Gets a function return an object, as performed By {@link IScriptExecutor}.
     */
    QuadFunction<IScriptExecutor, String, Iterable<Object>, Object> getExecutor();
}
