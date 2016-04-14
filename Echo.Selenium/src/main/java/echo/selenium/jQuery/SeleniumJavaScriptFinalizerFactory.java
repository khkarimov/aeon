package echo.selenium.jQuery;

import echo.selenium.jQuery.IJavaScriptFinalizer;
import echo.selenium.jQuery.IJavaScriptFinalizerFactory;
import echo.selenium.jQuery.JQueryRegexPluginRegistration;
import echo.selenium.jQuery.JavaScriptMinimizer;

/**
 * The default <see cref="IJavaScriptFinalizerFactory"/> implementation for Web.
 */
public class SeleniumJavaScriptFinalizerFactory implements IJavaScriptFinalizerFactory {
    /**
     * Creates an instance for the JavaScript finalizer.
     *
     * @param options The options.
     * @return A new <see cref="IJavaScriptFinalizer"/> object.
     */
    public final IJavaScriptFinalizer CreateInstance(JavaScriptFinalizerOptions options) {
        IJavaScriptFinalizer jQueryRegexPluginRegistration = new JQueryRegexPluginRegistration(new JavaScriptMinimizer());

        return options == JavaScriptFinalizerOptions.IncludeJQueryInjection ?
                new InjectJQueryScripter(jQueryRegexPluginRegistration) : jQueryRegexPluginRegistration;
    }
}
