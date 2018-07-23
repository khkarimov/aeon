package aeon.core.extensions;

import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.testabstraction.product.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pf4j.Extension;

/**
 * Test execution extension for logging test details.
 */
@Extension
public class LoggingTestExecutionExtension implements ITestExecutionExtension {

    private static Logger log = LogManager.getLogger(LoggingTestExecutionExtension.class);

    @Override
    public void onStartUp(Configuration configuration, String correlationId) {
        // Nothing to log
    }

    @Override
    public void onBeforeStart(String correlationId, String suiteName) {
        // Nothing to log
    }

    @Override
    public void onBeforeLaunch(Configuration configuration) {
        // Nothing to log
    }

    @Override
    public void onAfterLaunch(Configuration configuration, IAdapter adapter) {
        // Launching is logged by default on the info level
    }

    @Override
    public void onBeforeTest(String name, String... tags) {
        log.info("TEST STARTED: " + name);
    }

    @Override
    public void onSucceededTest() {
        log.info("TEST SUCCEEDED");
    }

    @Override
    public void onSkippedTest() {
        log.info("TEST SKIPPED");
    }

    @Override
    public void onFailedTest(String reason, Throwable e) {
        log.info("TEST FAILED: " + reason);
    }

    @Override
    public void onBeforeStep(String message) {
        log.info(message);
    }

    @Override
    public void onExecutionEvent(String eventName, Object payload) {
        // Nothing to log
    }

    @Override
    public void onDone() {
        // Nothing to log
    }
}
