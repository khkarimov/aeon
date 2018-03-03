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
    private static Report REPORT_BEAN;
    static final SimpleDateFormat REPORT_DATE_FORMAT = new SimpleDateFormat("d MMM yyyy HH:mm:ss");

    private static IConfiguration aeonConfiguration;
    private static IConfiguration configuration;

    private static Logger log = LogManager.getLogger(ReportingPlugin.class);

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
        static long currentStartTime = System.currentTimeMillis();

        @Override
        public void onStartUp(Configuration aeonConfiguration) {
            configuration = new ReportingConfiguration();

            try {
                configuration.loadConfiguration();
            } catch (IllegalAccessException | IOException e) {
                log.warn("Could not load plugin configuration, using Aeon configuration");

                configuration = aeonConfiguration;
            }

            ReportingPlugin.aeonConfiguration = aeonConfiguration;
            REPORT_BEAN = new Report();
            startTime = System.currentTimeMillis();
            log.info("Start Time " + REPORT_DATE_FORMAT.format(new Date(startTime)));
            REPORT_BEAN.setSuiteName(" Suite");
        }

        @Override
        public void onAfterLaunch(Configuration configuration, IAdapter adapter) {
            aeonConfiguration = configuration;
        }

        @Override
        public void onBeforeTest(String name, String... tags) {
            currentTest = name;
            currentStartTime = System.currentTimeMillis();
        }

        @Override
        public void onSucceededTest() {
            REPORT_BEAN.addPass();
            Scenario scenarioBean = setScenarioDetails(currentTest, currentStartTime);
            scenarioBean.setModuleName("");
            scenarioBean.setStatus("PASSED");
        }

        @Override
        public void onFailedTest(String reason) {
            Scenario scenarioBean = setScenarioDetails(currentTest, currentStartTime);
            scenarioBean.setModuleName("");
            scenarioBean.setErrorMessage(reason);
            REPORT_BEAN.addFailed();
        }

        @Override
        public void onBeforeStep(String message) {
            // Nothing to do
        }

        @Override
        public void onDone() {
            long time = System.currentTimeMillis();
            log.info("End Time " + REPORT_DATE_FORMAT.format(new Date(time)));
            REPORT_BEAN.setTotalTime(getTime(time - startTime));
            new ReportSummary(configuration, aeonConfiguration).sendSummaryReport(REPORT_BEAN);
        }
    }

    private static Scenario setScenarioDetails(String testName, long startTime) {
        Scenario scenarioBean = new Scenario();
        scenarioBean.setScenarioName(testName);
        scenarioBean.setStartTime(REPORT_DATE_FORMAT.format(startTime));
        REPORT_BEAN.getScenarioBeans().add(scenarioBean);
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
}
