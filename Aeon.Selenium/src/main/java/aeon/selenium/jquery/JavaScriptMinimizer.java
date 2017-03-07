package aeon.selenium.jquery;

/**
 * Minimizes whitespace in JavaScript.
 */
public class JavaScriptMinimizer implements IJavaScriptFinalizer {
    /**
     * Specifies the particular preparation formatting.
     *
     * @param javaScript The JavaScript code to format.
     * @return Formatted JavaScript code.
     */
    public final String Prepare(String javaScript) {
        return StringUtils.MinimizeWhiteSpace(javaScript);
    }
}
