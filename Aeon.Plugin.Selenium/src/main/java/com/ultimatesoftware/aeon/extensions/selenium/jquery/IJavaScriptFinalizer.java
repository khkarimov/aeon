package com.ultimatesoftware.aeon.extensions.selenium.jquery;

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
    String prepare(String javaScript);
}
