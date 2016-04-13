package echo.core.framework_abstraction.webdriver;

import echo.core.common.JavaScriptFinalizerOptions;

/**
 Factory for <see cref="IJavaScriptFinalizer"/>.
*/
public interface IJavaScriptFinalizerFactory
{
	/**
	 Creates an instance for the JavaScript finalizer.
	 
	 @param options The options.
	 @return A new <see cref="IJavaScriptFinalizer"/> object.
	*/
	IJavaScriptFinalizer CreateInstance(JavaScriptFinalizerOptions options);
}
