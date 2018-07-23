package aeon.extensions.reporting;

import aeon.core.common.interfaces.IConfiguration;
import aeon.core.extensions.ITestExecutionExtension;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.testabstraction.product.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Plugin class for Reporting Plugin.
 */
public class ReportingPlugin extends Plugin {
    static final SimpleDateFormat reportDateFormat = new SimpleDateFormat("d MMM yyyy HH:mm:ss");
    static final SimpleDateFormat uploadDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private static IConfiguration aeonConfiguration;
    private static IConfiguration configuration;

    private static Logger log = LogManager.getLogger(ReportingPlugin.class);
    private static ReportDetails reportDetails = null;

    /**
     * Constructor to be used by plugin manager for plugin instantiation.
     * Your plugins have to provide constructor with this exact signature to
     * be successfully loaded by manager.
     *
     * @param wrapper The plugin wrapper to be set.
     */
    public ReportingPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    /**
     * Test execution extension for sending test details to Slack or via email.
     */
    @Extension
    public static class ReportingTestExecutionExtension implements ITestExecutionExtension {

        public ReportingTestExecutionExtension() {

        }

        static Map<Long, ScenarioDetails> scenarios = new ConcurrentHashMap<>();
        static Queue<ScenarioDetails> finishedScenarios = new ConcurrentLinkedQueue<>();

        @Override
        public void onBeforeStart(String correlationId, String suiteName) {
            // Don't check that reportContainer is null, as it should be re-initialized with this message
            initializeReport(suiteName);

            initializeConfiguration();

            ReportingPlugin.reportDetails.setCorrelationId(correlationId);
        }

        @Override
        public void onStartUp(Configuration aeonConfiguration, String correlationId) {
            // Only initialize if it wasn't already
            if (reportDetails == null) {
                initializeReport(null);
            }

            initializeConfiguration(aeonConfiguration);

            ReportingPlugin.reportDetails.setCorrelationId(correlationId);
        }

        @Override
        public void onBeforeLaunch(Configuration configuration) {
            aeonConfiguration = configuration;
        }

        @Override
        public void onAfterLaunch(Configuration configuration, IAdapter adapter) {
            aeonConfiguration = configuration;
        }

        /**
         * Returns the scenario details of the currently active thread
         * creating the object if it doesn't exist.
         *
         * @return The scenario details object of the current thread.
         */
        ScenarioDetails getCurrentScenarioBucket() {
            return getCurrentScenarioBucket(false);
        }

        /**
         * Returns the scenario details of the currently active thread
         * creating the object if it doesn't exist and `create` is false.
         *
         * @param create    Set to true if a new object should be created
         *                  replacing the old one for the current thread.
         * @return The scenario details object of the current thread.
         */
        ScenarioDetails getCurrentScenarioBucket(boolean create) {
            long threadId = Thread.currentThread().getId();
            if (!create && scenarios.containsKey(threadId)) {
                return scenarios.get(threadId);
            }

            ScenarioDetails scenario = new ScenarioDetails();
            scenario.setThreadId(threadId);
            scenarios.put(threadId, scenario);

            return scenario;
        }

        @Override
        public void onBeforeTest(String name, String... tags) {

            ScenarioDetails scenario = getCurrentScenarioBucket(true);

            boolean displayClassName = configuration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true);

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
        public void onSkippedTest() {
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
            // Nothing to do
        }

        @Override
        public void onExecutionEvent(String eventName, Object payload) {
            switch (eventName) {
                case "screenshotTaken":
                    getCurrentScenarioBucket().setScreenshot((Image) payload);
                    break;
                case "videoDownloaded":
                    String videoUrl = ReportSummary.uploadToArtifactory(configuration, (String) payload);

                    if (videoUrl != null) {
                        getCurrentScenarioBucket().setVideoUrl(videoUrl);
                        for (ScenarioDetails scenario: finishedScenarios) {
                            if (scenario.getVideoUrl().isEmpty()
                                    && scenario.getThreadId() == Thread.currentThread().getId()) {
                                scenario.setVideoUrl(videoUrl);
                            }
                        }
                    }

                    break;
            }
        }

        @Override
        public void onDone() {
            long time = System.currentTimeMillis();
            log.info("End Time " + reportDateFormat.format(new Date(time)));

            reportDetails.setEndTime(time);
            reportDetails.setScenarios(finishedScenarios);
            ReportSummary reportSummary = new ReportSummary(configuration, aeonConfiguration, reportDetails);

            String reportUrl = reportSummary.createReportFile();
            reportSummary.sendSummaryReport(reportUrl);
        }

        private void initializeReport(String suiteName) {
            reportDetails = new ReportDetails();
            long startTime = System.currentTimeMillis();
            reportDetails.setStartTime(startTime);
            reportDetails.setSuiteName(suiteName);
            log.info("Start Time " + reportDateFormat.format(new Date(startTime)));
        }
    }

    private static void initializeConfiguration() {
        if (configuration == null) {
            configuration = new ReportingConfiguration();
            try {
                configuration.loadConfiguration();
            } catch (IllegalAccessException | IOException e) {
                log.warn("Could not load plugin configuration.");
            }
        }
    }

    private static void initializeConfiguration(Configuration aeonConfiguration) {
        if (configuration == null) {
            configuration = new ReportingConfiguration();
            try {
                configuration.loadConfiguration();
            } catch (IllegalAccessException | IOException e) {
                log.warn("Could not load plugin configuration, using Aeon configuration.");

                configuration = aeonConfiguration;
            }

            ReportingPlugin.aeonConfiguration = aeonConfiguration;
        }
    }
}
