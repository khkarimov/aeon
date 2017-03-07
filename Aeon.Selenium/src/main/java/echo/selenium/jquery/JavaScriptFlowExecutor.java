package aeon.selenium.jquery;

import aeon.core.common.helpers.QuadFunction;

import java.util.UUID;
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
        return (options) -> getFinalizerFactory().CreateInstance(options);
    }

    public abstract QuadFunction<IScriptExecutor, UUID, String, Iterable<Object>, Object> getExecutor();
}
