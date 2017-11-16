package aeon.selenium.appium.jquery;

import aeon.core.common.helpers.QuadFunction;

import java.util.function.Function;

/**
 * Class that handles the java script flow execution.
 */
public abstract class JavaScriptFlowExecutor implements IJavaScriptFlowExecutor {
    private IJavaScriptFinalizerFactory finalizerFactory;

    /**
     * Constructor that sets the finalizerFactory.
     *
     * @param finalizerFactory The finalizer factory to assign.
     */
    protected JavaScriptFlowExecutor(IJavaScriptFinalizerFactory finalizerFactory) {
        this.finalizerFactory = finalizerFactory;
    }

    /**
     * Function to get the finalizer factory.
     *
     * @return The finalize factory.
     */
    protected final IJavaScriptFinalizerFactory getFinalizerFactory() {
        return finalizerFactory;
    }

    /**
     * Function that creates the Finalizer.
     *
     * @return The new option instance.
     */
    public final Function<JavaScriptFinalizerOptions, IJavaScriptFinalizer> getFinalizer() {
        return (options) -> getFinalizerFactory().createInstance(options);
    }

    /**
     * Abstract function to get the executor.
     *
     * @return Returns the executor for the script.
     */
    public abstract QuadFunction<IScriptExecutor, String, Iterable<Object>, Object> getExecutor();
}
