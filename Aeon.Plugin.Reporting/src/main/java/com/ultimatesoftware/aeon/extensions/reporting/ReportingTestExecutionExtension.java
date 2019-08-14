package com.ultimatesoftware.aeon.extensions.reporting;

import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.core.extensions.ITestExecutionExtension;
import com.ultimatesoftware.aeon.core.extensions.IUploadListenerExtension;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapter;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import com.ultimatesoftware.aeon.extensions.reporting.models.Report;
import com.ultimatesoftware.aeon.extensions.reporting.models.TestCase;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Test execution extension for creating a report.
 */
@Extension
public class ReportingTestExecutionExtension implements ITestExecutionExtension, IUploadListenerExtension {

    private IConfiguration configuration;
    private TestCase currentTestCase;

    private ReportController reportController;

    private static Report report = null;
    private static Queue<TestCase> finishedTestCases = new ConcurrentLinkedQueue<>();

    private static Logger log = LoggerFactory.getLogger(ReportingTestExecutionExtension.class);
    private final SimpleDateFormat uploadDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    ReportingTestExecutionExtension(
            ReportController reportController,
            IConfiguration configuration) {
        this.reportController = reportController;
        this.configuration = configuration;
    }

    /**
     * Creates an instance for this extension.
     *
     * @return An instance of this extension.
     */
    public static Object createInstance() {

        IConfiguration configuration = new ReportingConfiguration();

        try {
            configuration.loadConfiguration();
        } catch (IllegalAccessException | IOException e) {
            log.warn("Could not load plugin configuration.");
        }

        return new ReportingTestExecutionExtension(
                new ReportController(
                        new HtmlReport(configuration)),
                configuration
        );
    }

    @Override
    public void onBeforeStart(String correlationId, String suiteName) {
        // Don't check that reportDetails is null, as it should be re-initialized with this message
        initializeReport(suiteName);

        report.setCorrelationId(correlationId);
    }

    @Override
    public void onStartUp(Configuration aeonConfiguration, String correlationId) {
        // Only initialize if it wasn't already
        if (report == null) {
            initializeReport(null);
        }

        report.setCorrelationId(correlationId);
    }

    @Override
    public void onBeforeLaunch(Configuration configuration) {
        // Nothing to do
    }

    @Override
    public void onAfterLaunch(Configuration configuration, IAdapter adapter) {
        // Nothing to do
    }

    /**
     * Returns the test case details of the currently active thread
     * creating the object if it doesn't exist.
     *
     * @return The test case details object of the current thread.
     */
    private TestCase getCurrentTestCaseBucket() {
        return getCurrentTestCaseBucket(false);
    }

    /**
     * Returns the test case details of the currently active thread
     * creating the object if it doesn't exist and `create` is false.
     *
     * @param create Set to true if a new object should be created
     *               replacing the old one for the current thread.
     * @return The test case details object of the current thread.
     */
    private TestCase getCurrentTestCaseBucket(boolean create) {

        if (create || this.currentTestCase == null) {
            this.currentTestCase = new TestCase();
            this.currentTestCase.setThreadId(Thread.currentThread().getId());

            return this.currentTestCase;
        }

        return this.currentTestCase;
    }

    @Override
    public void onBeforeTest(String name, String... tags) {

        TestCase testCase = getCurrentTestCaseBucket(true);

        boolean displayClassName = configuration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true);

        // Determines whether the testName should include the full suite/class or not
        if (displayClassName && name.lastIndexOf('.') > -1) {
            int classNameIndex = name.lastIndexOf('.');
            testCase.setPrefix(name.substring(0, classNameIndex));
            testCase.setDescription(name.substring(classNameIndex + 1));
        } else {
            testCase.setDescription(name);
        }

        long time = System.currentTimeMillis();
        testCase.setStartTime(time);
        testCase.setStarted(uploadDateFormat.format(new Date(time)));
    }

    @Override
    public void onSucceededTest() {
        TestCase testCase = getCurrentTestCaseBucket();

        recordEndTime(testCase);
        testCase.setStatus("passed");

        finishedTestCases.add(testCase);
    }

    @Override
    public void onSkippedTest(String name, String... tags) {

        // Test didn't run
        // Treat it as if it just started and ended
        onBeforeTest(name, tags);

        TestCase testCase = getCurrentTestCaseBucket();

        recordEndTime(testCase);
        testCase.setStatus("disabled");

        finishedTestCases.add(testCase);
    }

    private void recordEndTime(TestCase testCase) {
        long time = System.currentTimeMillis();
        testCase.setStopped(uploadDateFormat.format(new Date(time)));
        testCase.setDuration(ReportingPlugin.getTime(time - testCase.getStartTime()));
    }

    @Override
    public void onFailedTest(String reason, Throwable e) {

        TestCase testCase = getCurrentTestCaseBucket();

        if (e != null) {
            final StringWriter sw = new StringWriter();
            final PrintWriter pw = new PrintWriter(sw, true);
            e.printStackTrace(pw);
            testCase.setError(reason, sw.getBuffer().toString());
        } else {
            testCase.setError(reason, null);
        }

        recordEndTime(testCase);
        testCase.setStatus("failed");

        finishedTestCases.add(testCase);
    }

    @Override
    public void onBeforeStep(String message) {

        TestCase testCase = getCurrentTestCaseBucket();

        // Only add steps if the test is not marked as completed yet.
        if (testCase.getStatus().equals("")) {
            testCase.addHighLevelStep(message);
        }
    }

    @Override
    public void onExecutionEvent(String eventName, Object payload) {
        switch (eventName) {
            case "screenshotTaken":
                handleScreenshotTaken((java.awt.Image) payload);
                break;
            case "commandInitialized":
                TestCase testCase = getCurrentTestCaseBucket();

                // Only add steps if the test is not marked as completed yet.
                if (testCase.getStatus().equals("")) {
                    testCase.addStep((String) payload);
                }
                break;
            case "browserLogsCollected":
                handleBrowserLogsCollectedEvent((List<Map<String, Object>>) payload);
                break;
            default:
                // Skip unknown events.
        }
    }

    @Override
    public void onDone() {
        long time = System.currentTimeMillis();

        report.getTimer().setEndTime(time);
        report.setSequence(new ArrayList<>(finishedTestCases));

        this.reportController.writeReportsAndUpload(report);
    }

    private void handleBrowserLogsCollectedEvent(List<Map<String, Object>> payload) {

        if (payload != null) {
            getCurrentTestCaseBucket().setBrowserLogs(payload);
            for (TestCase testCase : finishedTestCases) {
                if (testCase.getBrowserLogs() == null
                        && testCase.getThreadId() == Thread.currentThread().getId()) {
                    testCase.setBrowserLogs(payload);
                }
            }
        }
    }

    private void handleScreenshotTaken(java.awt.Image screenshot) {
        if (screenshot == null) {
            return;
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write((BufferedImage) screenshot, "png", stream);
            String data = Base64.getEncoder().encodeToString(stream.toByteArray());
            getCurrentTestCaseBucket().setScreenshotPath("data:image/png;base64," + data);
            stream.close();
        } catch (IOException e) {
            log.warn("Could not write screenshot", e);
        }
    }

    private static void initializeReport(String suiteName) {
        finishedTestCases = new ConcurrentLinkedQueue<>();

        report = new Report();
        long startTime = System.currentTimeMillis();
        report.getTimer().setStartTime(startTime);
        report.setName(suiteName);
        String startTimeFormatted = new SimpleDateFormat("d MMM yyyy HH:mm:ss")
                .format(new Date(startTime));
        log.info("Start Time {}", startTimeFormatted);
    }

    @Override
    public void onUploadSucceeded(String url, String type, String label) {

        if (url == null || !type.equalsIgnoreCase("video")) {
            return;
        }

        getCurrentTestCaseBucket().setVideoUrl(url);
        for (TestCase testCase : finishedTestCases) {
            if (testCase.getVideoUrl().isEmpty()
                    && testCase.getThreadId() == Thread.currentThread().getId()) {
                testCase.setVideoUrl(url);
            }
        }
    }
}
