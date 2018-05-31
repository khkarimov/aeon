package aeon.core.testabstraction.product;

import aeon.core.extensions.ITestExecutionExtension;
import aeon.core.framework.abstraction.adapters.IAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Provides methods for annotating tests with meta data for behavior driven development.
 */
public class AeonTestExecution {

    private static List<ITestExecutionExtension> testExecutionPlugins;

    private static Logger log = LogManager.getLogger(AeonTestExecution.class);

    private static void init() {
        if (testExecutionPlugins == null) {
            testExecutionPlugins = Aeon.getExtensions(ITestExecutionExtension.class);
        }
    }

    /**
     * Is called when Aeon is starting up.
     *
     * @param configuration The aeon configuration object.
     */
    public static void startUp(Configuration configuration) {
        init();

        for (ITestExecutionExtension testExecutionPlugin: testExecutionPlugins) {
            testExecutionPlugin.onStartUp(configuration);
        }
    }

    /**
     * May be called at the end of all test executions.
     *
     * Should be called through Aeon.
     */
    static void done() {
        init();

        for (ITestExecutionExtension testExecutionPlugin : testExecutionPlugins) {
            testExecutionPlugin.onDone();
        }
    }

    /**
     * Method to indicate a successful launch of a product.
     *
     * Should be called through Aeon.
     *
     * @param configuration The aeon configuration.
     * @param adapter The adapter that is  used.
     */
    public static void launched(Configuration configuration, IAdapter adapter) {
        init();
        try {
            for (ITestExecutionExtension testExecutionPlugin: testExecutionPlugins) {
                testExecutionPlugin.onAfterLaunch(configuration, adapter);
            }
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
        init();
        for (ITestExecutionExtension testExecutionPlugin: testExecutionPlugins) {
            testExecutionPlugin.onBeforeTest(name, tags);
        }
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
    public static void testStep(String message) {
        init();
        for (ITestExecutionExtension testExecutionPlugin: testExecutionPlugins) {
            testExecutionPlugin.onBeforeStep(message);
        }
    }

    /**
     * Method to indicate the successful completion of a test.
     */
    public static void testSucceeded() {
        init();
        for (ITestExecutionExtension testExecutionPlugin: testExecutionPlugins) {
            testExecutionPlugin.onSucceededTest();
        }
    }

    /**
     * Method to indicate the end of a test due to a failure.
     *
     * @param message The failure message.
     */
    public static void testFailed(String message) {
        init();
        for (ITestExecutionExtension testExecutionPlugin: testExecutionPlugins) {
            testExecutionPlugin.onFailedTest(message);
        }
    }

    /**
     * Method to indicate the skipping of a test.
     */
    public static void testSkipped() {
        init();
        for (ITestExecutionExtension testExecutionPlugin: testExecutionPlugins) {
            testExecutionPlugin.onSkippedTest();
        }
    }
}
