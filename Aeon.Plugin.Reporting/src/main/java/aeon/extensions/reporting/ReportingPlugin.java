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

/**
 * Plugin class for Reporting Plugin.
 */
public class ReportingPlugin extends Plugin {
    private static Report reportBean;
    static final SimpleDateFormat reportDateFormat = new SimpleDateFormat("d MMM yyyy HH:mm:ss");

    private static IConfiguration aeonConfiguration;
    private static IConfiguration configuration;

    private static Logger log = LogManager.getLogger(ReportingPlugin.class);
    static String suiteName;

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

        static long startTime = 0;
        static String currentTest = "";
        static String currentClass = "";
        static long currentStartTime = System.currentTimeMillis();
        static Image currentScreenshot = null;

        @Override
        public void onBeforeStart() {
            // Don't check that reportBean is null, as it should be re-initialized with this message
            initializeReport();

            initializeConfiguration();
        }

        /**
         * This gets called by the AutomationInfo constructor.
         *
         * @param aeonConfiguration The aeon Configuration for the tests.
         */
        @Override
        public void onStartUp(Configuration aeonConfiguration) {
            // This check is required, as TestNG projects will not call onBeforeStart
            if (reportBean == null) {
                initializeReport();
            }
            initializeConfiguration(aeonConfiguration);
        }

        @Override
        public void onAfterLaunch(Configuration configuration, IAdapter adapter) {
            aeonConfiguration = configuration;
        }

        @Override
        public void onBeforeTest(String name, String... tags) {
            boolean displayClassName = configuration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true);

            if (displayClassName && name.lastIndexOf('.') > -1) {
                int classNameIndex = name.lastIndexOf('.');
                currentTest = name.substring(0, classNameIndex);
                currentClass = name.substring(classNameIndex + 1);
            } else {
                currentTest = name;
                currentClass = "";
            }

            currentStartTime = System.currentTimeMillis();
            currentScreenshot = null;
        }

        @Override
        public void onSucceededTest() {
            reportBean.addPass();
            Scenario scenarioBean = setScenarioDetails(currentTest, currentStartTime);
            scenarioBean.setModuleName(currentClass);
            scenarioBean.setStatus("PASSED");
        }

        @Override
        public void onSkippedTest() {
            reportBean.addSkipped();
            Scenario scenarioBean = setScenarioDetails(currentTest, currentStartTime);
            scenarioBean.setModuleName(currentClass);
            scenarioBean.setErrorMessage("");
            scenarioBean.setStatus("SKIPPED");
        }

        @Override
        public void onFailedTest(String reason, Throwable e) {
            Scenario scenarioBean = setScenarioDetails(currentTest, currentStartTime);
            scenarioBean.setModuleName(currentClass);
            scenarioBean.setErrorMessage(reason);
            final StringWriter sw = new StringWriter();
            final PrintWriter pw = new PrintWriter(sw, true);
            e.printStackTrace(pw);
            scenarioBean.setStackTrace(sw.getBuffer().toString());
            scenarioBean.setStatus("FAILED");
            scenarioBean.setScreenshot(currentScreenshot);
            reportBean.addFailed();
        }

        @Override
        public void onBeforeStep(String message) {
            // Nothing to do
        }

        @Override
        public void onExecutionEvent(String eventName, Object payload) {
            switch (eventName) {
                case "screenshotTaken":
                    currentScreenshot = (Image) payload;
                    break;
            }
        }

        @Override
        public void onDone() {
            long time = System.currentTimeMillis();
            log.info("End Time " + reportDateFormat.format(new Date(time)));
            reportBean.setTotalTime(time - startTime);
            ReportSummary reportSummary = new ReportSummary(configuration, aeonConfiguration);
            reportSummary.sendSummaryReport(reportBean);
            reportSummary.createReportFile(reportBean);
        }

        private void initializeReport() {
            reportBean = new Report();
            startTime = System.currentTimeMillis();
            log.info("Start Time " + reportDateFormat.format(new Date(startTime)));
            reportBean.setSuiteName(suiteName);
        }
    }

    static String getTime() {
        Date resultDate = new Date(ReportingTestExecutionExtension.startTime);
        return resultDate.toString();
    }

    private static Scenario setScenarioDetails(String testName, long startTime) {
        Scenario scenarioBean = new Scenario();
        scenarioBean.setScenarioName(testName);
        scenarioBean.setStartTime(startTime);
        scenarioBean.setEndTime(new Date().getTime());
        reportBean.getScenarioBeans().add(scenarioBean);
        return scenarioBean;
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
