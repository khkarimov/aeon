package com.ultimatesoftware.aeon.extensions.selenium.jquery;

/**
 * Factory for {@link IJavaScriptFinalizerFactory}.
 */
public interface IJavaScriptFinalizerFactory {
    /**
     * Creates an instance for the JavaScript finalizer.
     *
     * @param options The options.
     * @return A new {@link IJavaScriptFinalizerFactory} object.
     */
    IJavaScriptFinalizer createInstance(JavaScriptFinalizerOptions options);
}
