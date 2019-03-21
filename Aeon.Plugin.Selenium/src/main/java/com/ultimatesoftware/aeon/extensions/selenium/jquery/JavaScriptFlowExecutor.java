package com.ultimatesoftware.aeon.extensions.selenium.jquery;

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
        return options -> getFinalizerFactory().createInstance(options);
    }
}
