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
     * @param correlationId UUID to uniquely identify this session.
     */
    void onStartUp(Configuration configuration, String correlationId);

    /**
     * Is called before a test class or suite begins test execution.
     *
     * @param correlationId UUID to uniquely identify this session.
     * @param suiteName     Optional Name of the suite (can be set to null).
     */
    void onBeforeStart(String correlationId, String suiteName);

    /**
     * Is called right before a product is launched.
     *
     * @param configuration The Aeon configuration object.
     */
    void onBeforeLaunch(Configuration configuration);

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
     *
     * @param name Test name
     * @param tags Tags to add
     */
    void onSkippedTest(String name, String... tags);

    /**
     * Is called when a test failed.
     *
     * @param reason Error message.
     * @param e      Exception.
     */
    void onFailedTest(String reason, Throwable e);

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

    /**
     * Can be used to broadcast test execution events to plugins.
     *
     * @param eventName The name of the event in order to be able to identify it.
     * @param payload   The payload of the event.
     */
    void onExecutionEvent(String eventName, Object payload);
}
