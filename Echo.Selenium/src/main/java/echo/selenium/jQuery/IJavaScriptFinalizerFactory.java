package echo.selenium.jQuery;

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
