package com.ultimatesoftware.aeon.extensions.selenium.jquery;

/**
 * Base class for finalizing JavaScript.
 */
abstract class JavaScriptFinalizer implements IJavaScriptFinalizer {
    IJavaScriptFinalizer successor;

    /**
     * Initializes a new instance of the {@link JavaScriptFinalizer} class.
     *
     * @param successor The next finalizer for formatting.
     */
    JavaScriptFinalizer(IJavaScriptFinalizer successor) {
        if (successor == null) {
            throw new IllegalArgumentException("successor");
        }

        this.successor = successor;
    }
}
