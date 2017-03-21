package aeon.selenium.jquery;

import aeon.core.common.helpers.QuadFunction;

import java.util.function.Function;

public abstract class JavaScriptFlowExecutor implements IJavaScriptFlowExecutor {
    private IJavaScriptFinalizerFactory finalizerFactory;

    protected JavaScriptFlowExecutor(IJavaScriptFinalizerFactory finalizerFactory) {
        this.finalizerFactory = finalizerFactory;
    }

    protected final IJavaScriptFinalizerFactory getFinalizerFactory() {
        return finalizerFactory;
    }

    public final Function<JavaScriptFinalizerOptions, IJavaScriptFinalizer> getFinalizer() {
        return (options) -> getFinalizerFactory().createInstance(options);
    }

    public abstract QuadFunction<IScriptExecutor, String, Iterable<Object>, Object> getExecutor();
}
