package com.ultimatesoftware.aeon.core.testabstraction.product;

import com.ultimatesoftware.aeon.core.extensions.ITestExecutionExtension;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Provides methods for annotating tests with meta data for behavior driven development.
 */
public class AeonTestExecution {

    private static Logger log = LoggerFactory.getLogger(AeonTestExecution.class);

    private static UUID correlationId = null;

    private AeonTestExecution() {
        // Static classes should not be instantiated.
    }

    private static void trigger(Consumer<ITestExecutionExtension> consumer) {
        List<ITestExecutionExtension> testExecutionExtensions = Aeon.getExtensions(ITestExecutionExtension.class);

        for (ITestExecutionExtension testExecutionExtension : testExecutionExtensions) {
            consumer.accept(testExecutionExtension);
        }
    }

    /**
     * Is called when Aeon is starting up.
     *
     * @param configuration The aeon configuration object.
     */
    public static void startUp(Configuration configuration) {
        if (correlationId == null) {
            correlationId = UUID.randomUUID();
        }

        trigger(testExecutionExtension -> testExecutionExtension.onStartUp(configuration, correlationId.toString()));
    }

    /**
     * Is called right before the product is launched.
     *
     * @param configuration The aeon configuration object.
     */
    static void beforeLaunch(Configuration configuration) {
        trigger(testExecutionExtension -> testExecutionExtension.onBeforeLaunch(configuration));
    }

    /**
     * Should be called in the @BeforeClass step of a test class.
     *
     * @param suiteName The name of the suite.
     */
    public static void beforeStart(String suiteName) {
        correlationId = UUID.randomUUID();

        trigger(testExecutionExtension -> testExecutionExtension.onBeforeStart(correlationId.toString(), suiteName));
    }

    /**
     * Should be called in the @BeforeClass step of a test class.
     */
    public static void beforeStart() {
        beforeStart(null);
    }

    /**
     * May be called at the end of all test executions.
     * <p>
     * Should be called through Aeon.
     */
    static void done() {
        correlationId = null;

        trigger(ITestExecutionExtension::onDone);
    }

    /**
     * Method to indicate a successful launch of a product.
     * <p>
     * Should be called through Aeon.
     *
     * @param configuration The aeon configuration.
     * @param adapter       The adapter that is  used.
     */
    public static void launched(Configuration configuration, IAdapter adapter) {
        try {
            trigger(testExecutionExtension -> testExecutionExtension.onAfterLaunch(configuration, adapter));
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
    }

    /**
     * Should be called before a test is started to document a name with it.
     *
     * @param name The name of the test.
     */
    public static void startTest(String name) {
        startTest(name, (String[]) null);
    }

    /**
     * Should be called before a test is started to document a name with it.
     *
     * @param name The name of the test.
     * @param tags Tags to add.
     */
    public static void startTest(String name, String... tags) {
        trigger(testExecutionExtension -> testExecutionExtension.onBeforeTest(name, tags));
    }

    /**
     * BDD keyword "GIVEN".
     *
     * @param message Message that will be appended to the BDD keyword.
     */
    public static void given(String message) {
        testStep("GIVEN " + message);
    }

    /**
     * BDD keyword "WHEN".
     *
     * @param message Message that will be appended to the BDD keyword.
     */
    public static void when(String message) {
        testStep("WHEN " + message);
    }

    /**
     * BDD keyword "THEN".
     *
     * @param message Message that will be appended to the BDD keyword.
     */
    public static void then(String message) {
        testStep("THEN " + message);
    }

    /**
     * BDD keyword "AND".
     *
     * @param message Message that will be appended to the BDD keyword.
     */
    public static void and(String message) {
        testStep("AND " + message);
    }

    /**
     * Method to indicate the start of a test step.
     *
     * @param message Title of the test step.
     */
    private static void testStep(String message) {
        trigger(testExecutionExtension -> testExecutionExtension.onBeforeStep(message));
    }

    /**
     * Method to indicate the successful completion of a test.
     */
    public static void testSucceeded() {
        trigger(ITestExecutionExtension::onSucceededTest);
    }

    /**
     * Method to indicate the end of a test due to a failure.
     *
     * @param message The failure message.
     */
    public static void testFailed(String message) {
        trigger(testExecutionExtension -> testExecutionExtension.onFailedTest(message, null));
    }

    /**
     * Method to indicate the end of a test due to a failure.
     *
     * @param message The failure message.
     * @param e       The exception of the failure.
     */
    public static void testFailed(String message, Throwable e) {
        trigger(testExecutionExtension -> testExecutionExtension.onFailedTest(message, e));
    }

    /**
     * Method to indicate the skipping of a test.
     *
     * @param name The name of the test.
     * @param tags Tags to add.
     */
    public static void testSkipped(String name, String... tags) {
        trigger(testExecutionExtension -> testExecutionExtension.onSkippedTest(name, tags));
    }

    /**
     * Can be used to broadcast test execution events to plugins.
     *
     * @param eventName The name of the event in order to be able to identify it.
     * @param payload   The payload of the event.
     */
    public static void executionEvent(String eventName, Object payload) {
        trigger(testExecutionExtension -> testExecutionExtension.onExecutionEvent(eventName, payload));
    }
}
