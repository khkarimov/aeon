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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Plugin class for Reporting Plugin.
 */
public class ReportingPlugin extends Plugin {
    private static Report report_bean;
    static final SimpleDateFormat report_date_format = new SimpleDateFormat("d MMM yyyy HH:mm:ss");

    private static IConfiguration aeonConfiguration;
    private static IConfiguration configuration;

    private static Logger log = LogManager.getLogger(ReportingPlugin.class);
    public static String suiteName;

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

        @Override
        public void onBeforeTestClass() {
            if (report_bean == null) {
                report_bean = new Report();
                startTime = System.currentTimeMillis();
                log.info("Start Time " + report_date_format.format(new Date(startTime)));
                report_bean.setSuiteName(suiteName);
            }
            initializeConfiguration();
        }

        /**
         * This gets called by the AutomationInfo constructor.
         * @param aeonConfiguration The aeon Configuration for the tests.
         */
        @Override
        public void onStartUp(Configuration aeonConfiguration) {
            if (report_bean == null) {
                report_bean = new Report();
                startTime = System.currentTimeMillis();
                log.info("Start Time " + report_date_format.format(new Date(startTime)));
                report_bean.setSuiteName(suiteName);
            }
            initializeConfiguration(aeonConfiguration);
        }

        @Override
        public void onAfterLaunch(Configuration configuration, IAdapter adapter) {
            aeonConfiguration = configuration;
        }

        @Override
        public void onBeforeTest(String name, String... tags) {
            boolean displayClassName = Boolean.valueOf(configuration.getString(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, "true"));

            if (displayClassName && name.lastIndexOf('.')>-1) {
                int classNameIndex = name.lastIndexOf('.');
                currentTest = name.substring(0,classNameIndex);
                currentClass = name.substring(classNameIndex + 1);
            } else {
                currentTest = name;
            }

            currentStartTime = System.currentTimeMillis();
        }

        @Override
        public void onSucceededTest() {
            report_bean.addPass();
            Scenario scenarioBean = setScenarioDetails(currentTest, currentStartTime);
            scenarioBean.setModuleName(currentClass);
            scenarioBean.setStatus("PASSED");
        }

        @Override
        public void onSkippedTest() {
            report_bean.addSkipped();
            Scenario scenarioBean = setScenarioDetails(currentTest, currentStartTime);
            scenarioBean.setModuleName(currentClass);
            scenarioBean.setErrorMessage("");
            scenarioBean.setStatus("SKIPPED");
        }

        @Override
        public void onFailedTest(String reason) {
            Scenario scenarioBean = setScenarioDetails(currentTest, currentStartTime);
            scenarioBean.setModuleName(currentClass);
            scenarioBean.setErrorMessage(reason);
            scenarioBean.setStatus("FAILED");
            report_bean.addFailed();
        }

        @Override
        public void onBeforeStep(String message) {
            // Nothing to do
        }

        @Override
        public void onDone() {
            long time = System.currentTimeMillis();
            log.info("End Time " + report_date_format.format(new Date(time)));
            report_bean.setTotalTime(getTime(time - startTime));
            new ReportSummary(configuration, aeonConfiguration).sendSummaryReport(report_bean);
        }
    }

    public static String getTime() {
        Date resultDate = new Date(ReportingTestExecutionExtension.startTime);
        return resultDate.toString();
    }

    private static Scenario setScenarioDetails(String testName, long startTime) {
        Scenario scenarioBean = new Scenario();
        scenarioBean.setScenarioName(testName);
        scenarioBean.setStartTime(report_date_format.format(startTime));
        report_bean.getScenarioBeans().add(scenarioBean);
        return scenarioBean;
    }

    private static String getTime(long time) {
        int seconds = (int) (time / 1000);
        if (seconds >= 60) {
            int minutes = seconds / 60;
            if (minutes >= 60) {
                int hours = minutes / 60;
                minutes = minutes % 60;
                return hours + " hours" + minutes + " minutes";
            }
            seconds = seconds % 60;
            return minutes + " minutes " + seconds + " seconds";
        } else {
            return seconds + " seconds";
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
