package aeon.selenium.jquery;

/**
 * Base class for finalizing JavaScript.
 */
public abstract class JavaScriptFinalizer implements IJavaScriptFinalizer {
    protected IJavaScriptFinalizer Successor;

    /**
     * Initializes a new instance of the {@link JavaScriptFinalizer} class.
     *
     * @param successor The next finalizer for formatting.
     */
    protected JavaScriptFinalizer(IJavaScriptFinalizer successor) {
        this.Successor = successor;
    }

    /**
     * Specifies the particular preparation formatting.
     *
     * @param javaScript The JavaScript code to format.
     * @return Formatted JavaScript code.
     * @throws IllegalArgumentException If {@code javaScript} is null.
     */
    public abstract String Prepare(String javaScript);
}
