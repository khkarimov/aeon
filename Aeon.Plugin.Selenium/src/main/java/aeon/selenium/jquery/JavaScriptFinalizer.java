package aeon.selenium.jquery;

/**
 * Base class for finalizing JavaScript.
 */
public abstract class JavaScriptFinalizer implements IJavaScriptFinalizer {
    protected IJavaScriptFinalizer successor;

    /**
     * Initializes a new instance of the {@link JavaScriptFinalizer} class.
     *
     * @param successor The next finalizer for formatting.
     */
    protected JavaScriptFinalizer(IJavaScriptFinalizer successor) {
        this.successor = successor;
    }
}
