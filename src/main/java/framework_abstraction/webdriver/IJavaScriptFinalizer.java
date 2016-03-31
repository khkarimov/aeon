package framework_abstraction.webdriver;

/**
 * Interface for preparing JavaScript according to formats specified by classes implementing this interface.
 */
public interface IJavaScriptFinalizer {
    /**
     * Specifies the particular preparation formatting.
     *
     * @param javaScript The JavaScript code to format.
     * @return Formatted JavaScript code.
     */
    String Prepare(String javaScript);
}
