package aeon.selenium.appium.jquery;

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

    /**
     * Specifies the particular preparation formatting.
     *
     * @param javaScript The JavaScript code to format.
     * @return Formatted JavaScript code.
     * @throws IllegalArgumentException If {@code javaScript} is null.
     */
    public abstract String prepare(String javaScript);
}
