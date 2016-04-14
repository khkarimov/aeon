package echo.selenium.jQuery;

/**
 * Base class for finalizing JavaScript.
 */
public abstract class JavaScriptFinalizer implements IJavaScriptFinalizer {
    protected IJavaScriptFinalizer Successor;

    /**
     * Initializes a new instance of the <see cref="JavaScriptFinalizer"/> class.
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
     * @throws IllegalArgumentException If <paramref name="javaScript"/> is <see langword="null"/>.
     */
    public abstract String Prepare(String javaScript);
}
