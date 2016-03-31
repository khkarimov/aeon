package framework_abstraction.webdriver;

/**
 Factory for <see cref="IJavaScriptFlowExecutor"/>.
*/
	public interface IJavaScriptFlowExecutorFactory
	{
		/** 
		 Creates an instance for JavaScript executor.
		 
		 @return A new <see cref="IJavaScriptFlowExecutor"/>.
		*/
		IJavaScriptFlowExecutor CreateInstance();
	}
