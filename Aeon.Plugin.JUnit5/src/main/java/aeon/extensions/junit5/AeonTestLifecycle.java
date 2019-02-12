package aeon.extensions.junit5;

import aeon.core.testabstraction.product.Aeon;
import aeon.core.testabstraction.product.AeonTestExecution;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Optional;

/**
 * Aeon extension for JUnit5.
 */
public class AeonTestLifecycle implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeAll(ExtensionContext context) {

        if (context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL)
                .get("aeon-test-lifecycle") != null) {
            return;
        }

        context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL)
                .put("aeon-test-lifecycle", new AeonCloseableResource());

        AeonTestExecution.beforeStart();
    }

    @Override
    public void beforeEach(ExtensionContext context) {

        String name = context.getDisplayName();
        Optional<ExtensionContext> parent = context.getParent();
        if (parent.isPresent()) {
            name = String.format("%s.%s", parent.get().getDisplayName(), name);
        }

        AeonTestExecution.startTest(name, context.getTags().toArray(new String[0]));
    }

    @Override
    public void afterEach(ExtensionContext context) {

        Optional<Throwable> throwable = context.getExecutionException();
        if (throwable.isPresent()) {
            AeonTestExecution.testFailed(throwable.get().getMessage(), throwable.get());

            return;
        }

        AeonTestExecution.testSucceeded();
    }

    static class AeonCloseableResource implements ExtensionContext.Store.CloseableResource {

        @Override
        public void close() {
            Aeon.done();
        }
    }
}
