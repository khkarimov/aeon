package aeon.core.extensions;

import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.testabstraction.product.Configuration;
import org.pf4j.ExtensionPoint;

/**
 * The interface for the Test Execution Extension.
 */
public interface ITestExecutionExtension extends ExtensionPoint {

    /**
     * Is called when Aeon is starting up.
     *
     * @param configuration The aeon configuration object.
     */
    void onStartUp(Configuration configuration);

    /**
     * Is called after a product was successfully launched.
     *
     * @param configuration The Aeon configuration object.
     * @param adapter The adapter that is used for the product.
     */
    void onAfterLaunch(Configuration configuration, IAdapter adapter);

    /**
     * Is called when AeonTestExecution.startTest() is used.
     *
     * @param name Test name
     * @param tags Tags to add
     */
    void onBeforeTest(String name, String... tags);

    /**
     * Is called when the driver is quit or testSucceeded is used.
     */
    void onSucceededTest();

    /**
     * Is called when the driver is skipped.
     */
    void onSkippedTest();

    /**
     * Is called when a test failed.
     *
     * @param reason Error message.
     */
    void onFailedTest(String reason);

    /**
     * Is called when a step method is used.
     *
     * @param message The message or name of the step.
     */
    void onBeforeStep(String message);

    /**
     * Is called when Aeon.done() is used.
     */
    void onDone();
}
