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
    public void onStartUp(Configuration configuration) {
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
    public void onFailedTest(String reason) {
        log.info("TEST FAILED: " + reason);
    }

    @Override
    public void onBeforeStep(String message) {
        log.info(message);
    }

    @Override
    public void onDone() {
        // Nothing to log
    }
}
