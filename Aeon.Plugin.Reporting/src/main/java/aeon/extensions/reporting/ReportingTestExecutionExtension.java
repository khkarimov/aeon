package aeon.extensions.reporting;

import aeon.core.common.interfaces.IConfiguration;
import aeon.core.extensions.ITestExecutionExtension;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.testabstraction.product.Configuration;
import aeon.extensions.reporting.models.ReportDetails;
import aeon.extensions.reporting.models.ScenarioDetails;
import aeon.extensions.reporting.reports.HtmlReport;
import aeon.extensions.reporting.reports.ImageReport;
import aeon.extensions.reporting.reports.SlackReport;
import aeon.extensions.reporting.services.ArtifactoryService;
import aeon.extensions.reporting.services.RnrService;
import aeon.extensions.reporting.services.SlackBotService;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Test execution extension for sending test details to Slack, Artifactory and RocknRoly.
 */
@Extension
public class ReportingTestExecutionExtension implements ITestExecutionExtension {

    private static IConfiguration aeonConfiguration;
    private static IConfiguration configuration;
    private ScenarioDetails currentScenario;

    private ArtifactoryService artifactoryService;
    private ReportController reportController;

    private static ReportDetails reportDetails = null;
    private static Queue<ScenarioDetails> finishedScenarios = new ConcurrentLinkedQueue<>();

    private static Logger log = LoggerFactory.getLogger(ReportingTestExecutionExtension.class);

    private ReportingTestExecutionExtension(
            IConfiguration configuration,
            ReportController reportController,
            ArtifactoryService artifactoryService
    ) {
        ReportingTestExecutionExtension.configuration = configuration;
        this.reportController = reportController;
        this.artifactoryService = artifactoryService;

        initializeConfiguration();
    }

    /**
     * Creates an instance for this extension.
     *
     * @return An instance of this extension.
     */
    public static Object createInstance() {
        ArtifactoryService artifactoryService = new ArtifactoryService();

        return new ReportingTestExecutionExtension(
                new ReportingConfiguration(),
                new ReportController(
                        new HtmlReport(),
                        new SlackReport(
                                new ImageReport(),
                                new SlackBotService()),
                        artifactoryService,
                        new RnrService()),
                artifactoryService
        );
    }

    @Override
    public void onBeforeStart(String correlationId, String suiteName) {
        // Don't check that reportDetails is null, as it should be re-initialized with this message
        initializeReport(suiteName);

        initializeConfiguration();

        reportDetails.setCorrelationId(correlationId);
    }

    @Override
    public void onStartUp(Configuration aeonConfiguration, String correlationId) {
        // Only initialize if it wasn't already
        if (reportDetails == null) {
            initializeReport(null);
        }

        this.initializeConfiguration(aeonConfiguration);

        reportDetails.setCorrelationId(correlationId);
    }

    @Override
    public void onBeforeLaunch(Configuration configuration) {
        this.initializeConfiguration(configuration);
    }

    @Override
    public void onAfterLaunch(Configuration configuration, IAdapter adapter) {
        this.initializeConfiguration(configuration);
    }

    /**
     * Returns the scenario details of the currently active thread
     * creating the object if it doesn't exist.
     *
     * @return The scenario details object of the current thread.
     */
    private ScenarioDetails getCurrentScenarioBucket() {
        return getCurrentScenarioBucket(false);
    }

    /**
     * Returns the scenario details of the currently active thread
     * creating the object if it doesn't exist and `create` is false.
     *
     * @param create Set to true if a new object should be created
     *               replacing the old one for the current thread.
     * @return The scenario details object of the current thread.
     */
    private ScenarioDetails getCurrentScenarioBucket(boolean create) {

        if (create || currentScenario == null) {
            this.currentScenario = new ScenarioDetails();
            this.currentScenario.setThreadId(Thread.currentThread().getId());

            return this.currentScenario;
        }

        return this.currentScenario;
    }

    @Override
    public void onBeforeTest(String name, String... tags) {

        ScenarioDetails scenario = getCurrentScenarioBucket(true);

        boolean displayClassName = configuration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true);

        // Determines whether the testName should include the full suite/class or not
        if (displayClassName && name.lastIndexOf('.') > -1) {
            int classNameIndex = name.lastIndexOf('.');
            scenario.setClassName(name.substring(0, classNameIndex));
            scenario.setTestName(name.substring(classNameIndex + 1));
        } else {
            scenario.setTestName(name);
        }
        scenario.setStartTime(System.currentTimeMillis());
    }

    @Override
    public void onSucceededTest() {
        ScenarioDetails scenario = getCurrentScenarioBucket();

        scenario.setEndTime(new Date().getTime());
        scenario.setStatus("PASSED");

        finishedScenarios.add(scenario);
    }

    @Override
    public void onSkippedTest(String name, String... tags) {

        // Test didn't run
        // Treat it as if it just started and ended
        onBeforeTest(name, tags);

        ScenarioDetails scenario = getCurrentScenarioBucket();

        scenario.setEndTime(new Date().getTime());
        scenario.setStatus("SKIPPED");

        finishedScenarios.add(scenario);
    }

    @Override
    public void onFailedTest(String reason, Throwable e) {

        ScenarioDetails scenario = getCurrentScenarioBucket();

        scenario.setErrorMessage(reason);

        if (e != null) {
            final StringWriter sw = new StringWriter();
            final PrintWriter pw = new PrintWriter(sw, true);
            e.printStackTrace(pw);
            scenario.setStackTrace(sw.getBuffer().toString());
        }

        scenario.setEndTime(new Date().getTime());
        scenario.setStatus("FAILED");

        finishedScenarios.add(scenario);
    }

    @Override
    public void onBeforeStep(String message) {

        ScenarioDetails currentScenarioDetails = getCurrentScenarioBucket();

        // Only add steps if the test is not marked as completed yet.
        if (currentScenarioDetails.getStatus().equals("")) {
            currentScenarioDetails.addHighLevelStep(message);
        }
    }

    @Override
    public void onExecutionEvent(String eventName, Object payload) {
        switch (eventName) {
            case "screenshotTaken":
                getCurrentScenarioBucket().setScreenshot((Image) payload);
                break;
            case "commandInitialized":
                ScenarioDetails currentScenarioDetails = getCurrentScenarioBucket();

                // Only add steps if the test is not marked as completed yet.
                if (currentScenarioDetails.getStatus().equals("")) {
                    currentScenarioDetails.addStep((String) payload);
                }
                break;
            case "videoDownloaded":
                handleVideoDownloadedEvent((String) payload);
                break;
            case "browserLogsCollected":
                handleBrowserLogsCollectedEvent((java.util.List<Map<String, Object>>) payload);
                break;
            default:
                // Skip unknown events.
        }
    }

    @Override
    public void onDone() {
        long time = System.currentTimeMillis();

        reportDetails.setEndTime(time);
        reportDetails.setScenarios(finishedScenarios);

        this.reportController.setConfiguration(
                ReportingTestExecutionExtension.configuration,
                ReportingTestExecutionExtension.aeonConfiguration);
        this.reportController.writeReportsAndUpload(reportDetails);
    }

    private void handleBrowserLogsCollectedEvent(List<Map<String, Object>> payload) {

        if (payload != null) {
            getCurrentScenarioBucket().setBrowserLogs(payload);
            for (ScenarioDetails scenario : finishedScenarios) {
                if (scenario.getBrowserLogs() == null
                        && scenario.getThreadId() == Thread.currentThread().getId()) {
                    scenario.setBrowserLogs(payload);
                }
            }
        }
    }

    private void handleVideoDownloadedEvent(String payload) {
        String videoUrl = this.artifactoryService.uploadToArtifactory(payload);

        if (videoUrl != null) {

            log.info("Video uploaded: {}", videoUrl);

            getCurrentScenarioBucket().setVideoUrl(videoUrl);
            for (ScenarioDetails scenario : finishedScenarios) {
                if (scenario.getVideoUrl().isEmpty()
                        && scenario.getThreadId() == Thread.currentThread().getId()) {
                    scenario.setVideoUrl(videoUrl);
                }
            }
        }
    }

    private static void initializeReport(String suiteName) {
        finishedScenarios = new ConcurrentLinkedQueue<>();

        reportDetails = new ReportDetails();
        long startTime = System.currentTimeMillis();
        reportDetails.setStartTime(startTime);
        reportDetails.setSuiteName(suiteName);
        String startTimeFormatted = new SimpleDateFormat("d MMM yyyy HH:mm:ss")
                .format(new Date(startTime));
        log.info("Start Time {}", startTimeFormatted);
    }

    private void initializeConfiguration() {
        try {
            ReportingTestExecutionExtension.configuration.loadConfiguration();
        } catch (IllegalAccessException | IOException e) {
            log.warn("Could not load plugin configuration.");
        }
    }

    private void initializeConfiguration(Configuration aeonConfiguration) {
        if (ReportingTestExecutionExtension.aeonConfiguration == null) {
            try {
                ReportingTestExecutionExtension.configuration.loadConfiguration();
            } catch (IllegalAccessException | IOException e) {
                log.warn("Could not load plugin configuration, using Aeon configuration.");

                ReportingTestExecutionExtension.configuration = aeonConfiguration;
            }
            ReportingTestExecutionExtension.aeonConfiguration = aeonConfiguration;

            this.reportController.setConfiguration(
                    ReportingTestExecutionExtension.configuration,
                    ReportingTestExecutionExtension.aeonConfiguration);
        }
    }
}
