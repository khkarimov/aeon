package framework_abstraction.webdriver;

import common.JavaScriptFinalizerOptions;
import common.QuadFunction;

import java.util.UUID;
import java.util.function.Function;

/**
 Interface for executing JavaScript.
*/
public interface IJavaScriptFlowExecutor
{
	/**
	 Gets a function return a finalizer based upon the given option.
	*/
	Function<JavaScriptFinalizerOptions, IJavaScriptFinalizer> getFinalizer();

	/** 
	 Gets a function return an object, as performed by <see cref="IWebDriverScriptExecutor"/>.
	*/
	QuadFunction<IWebDriverScriptExecutor, UUID, String, Iterable<Object>, Object> getExecutor();
}
