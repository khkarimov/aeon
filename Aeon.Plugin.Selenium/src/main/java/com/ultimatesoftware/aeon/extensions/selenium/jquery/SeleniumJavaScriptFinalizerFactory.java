package com.ultimatesoftware.aeon.extensions.selenium.jquery;

/**
 * The default {@link IJavaScriptFinalizerFactory} implementation for Web.
 */
public class SeleniumJavaScriptFinalizerFactory implements IJavaScriptFinalizerFactory {
    /**
     * Creates an instance for the JavaScript finalizer.
     *
     * @param options The options.
     * @return A new {@link IJavaScriptFinalizerFactory} object.
     */
    public final IJavaScriptFinalizer createInstance(JavaScriptFinalizerOptions options) {
        IJavaScriptFinalizer jQueryRegexPluginRegistration = new JQueryRegexPluginRegistration(new JavaScriptMinimizer());

        return options == JavaScriptFinalizerOptions.INCLUDE_JQUERY_INJECTION ?
                new InjectJQueryScripter(jQueryRegexPluginRegistration) : jQueryRegexPluginRegistration;
    }
}
