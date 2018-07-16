package aeon.extensions.perfecto;

import aeon.core.common.interfaces.IConfiguration;
import aeon.core.extensions.ITestExecutionExtension;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.testabstraction.product.Aeon;
import aeon.core.testabstraction.product.Configuration;
import aeon.selenium.SeleniumAdapter;
import aeon.selenium.SeleniumConfiguration;
import aeon.selenium.extensions.ISeleniumExtension;
import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.CustomField;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResult;
import com.perfecto.reportium.test.result.TestResultFactory;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Plugin class for Perfecto Plugin.
 */
public class PerfectoPlugin extends Plugin {

    private static ReportiumClient reportiumClient;

    static IConfiguration configuration;

    /**
     * Constructor to be used by plugin manager for plugin instantiation.
     * Your plugins have to provide constructor with this exact signature to
     * be successfully loaded by manager.
     *
     * @param wrapper The plugin wrapper to be set.
     */
    public PerfectoPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    static Logger log = LogManager.getLogger(PerfectoPlugin.class);

    /**
     * Test execution extensions for sending test details to Perfecto.
     */
    @Extension
    public static class PerfectoTestExecutionExtension implements ITestExecutionExtension {

        @Override
        public void onStartUp(Configuration configuration) {
            // No actions needed
        }

        @Override
        public void onBeforeStart() {
            // No actions needed
        }

        @Override
        public void onAfterLaunch(Configuration configuration, IAdapter adapter) {

            List<String> configurationKeys = configuration.getConfigurationKeys();
            List<CustomField> customFields = new ArrayList<>();
            for (String configurationKey : configurationKeys) {
                customFields.add(new CustomField(configurationKey, configuration.getString(configurationKey, null)));
            }

            customFields.add(new CustomField("Aeon Version", Aeon.getVersion()));

            PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                    .withCustomFields(customFields.toArray(new CustomField[0]))
                    .withWebDriver(((SeleniumAdapter) adapter).getWebDriver())
                    .build();
            reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
        }

        @Override
        public void onBeforeTest(String name, String... tags) {
            TestContext testContext = new TestContext();
            if (tags != null && tags.length != 0) {
                testContext = new TestContext.Builder().withTestExecutionTags(tags).build();
            }

            reportiumClient.testStart(name, testContext);
        }

        @Override
        public void onSucceededTest() {
            reportiumClient.testStop(TestResultFactory.createSuccess());

            log.info("Test Report URL: " + reportiumClient.getReportUrl());
        }

        @Override
        public void onSkippedTest() {
            reportiumClient.testStop(TestResultFactory.createFailure("Skipped"));

            log.info("Test Report URL: " + reportiumClient.getReportUrl());
        }

        @Override
        public void onFailedTest(String reason, Throwable e) {
            reportiumClient.testStop(TestResultFactory.createFailure(reason, e));

            log.info("Test Report URL: " + reportiumClient.getReportUrl());
        }

        @Override
        public void onBeforeStep(String message) {
            reportiumClient.stepStart(message);
        }

        @Override
        public void onExecutionEvent(String eventName, Object payload) {
            // No actions needed
        }

        @Override
        public void onDone() {

        }
    }

    /**
     * ISeleniumExtension to add Perfecto capabilities to Selenium plugin.
     */
    @Extension
    public static class PerfectoSeleniumExtension implements ISeleniumExtension {

        @Override
        public void onGenerateCapabilities(Configuration configuration, MutableCapabilities capabilities) {
            //check if PerfectoConfiguration has been instantiated
            if (PerfectoPlugin.configuration == null) {
                PerfectoPlugin.configuration = new PerfectoConfiguration();
                try {
                    PerfectoPlugin.configuration.loadConfiguration();
                } catch (IllegalAccessException | IOException e) {
                    log.info("Could not load plugin configuration, using Aeon configuration instead");
                    PerfectoPlugin.configuration = configuration;
                }
            }

            //set variables
            String perfectoUser = PerfectoPlugin.configuration.getString(PerfectoConfiguration.Keys.PERFECTO_USER, "");
            String perfectoPass = PerfectoPlugin.configuration.getString(PerfectoConfiguration.Keys.PERFECTO_PASS, "");
            String perfectoToken = PerfectoPlugin.configuration.getString(PerfectoConfiguration.Keys.PERFECTO_TOKEN, "");
            boolean perfectoAutoInstrument = PerfectoPlugin.configuration.getBoolean(PerfectoConfiguration.Keys.PERFECTO_AUTOINSTRUMENT, false);
            boolean perfectoSensorInstrument = PerfectoPlugin.configuration.getBoolean(PerfectoConfiguration.Keys.PERFECTO_SENSORINSTRUMENT, false);

            //credentials
            setPerfectoCredentials(perfectoUser, perfectoPass, perfectoToken, capabilities);

            //instrumentation
            capabilities.setCapability("autoInstrument", perfectoAutoInstrument);
            capabilities.setCapability("sensorInstrument", perfectoSensorInstrument);

        }

        @Override
        public void onAfterLaunch(Configuration configuration, WebDriver driver) {
            boolean ensureCleanEnvironment = configuration.getBoolean(SeleniumConfiguration.Keys.ENSURE_CLEAN_ENVIRONMENT, true);
            String appPackage = configuration.getString(SeleniumConfiguration.Keys.APP_PACKAGE, "");

            //Only if AndroidHybridApp
            if (driver instanceof AndroidDriver) {
                //Cleans the app data for a fresh new session.
                if (ensureCleanEnvironment && !appPackage.isEmpty()) {
                    try {
                        log.info("Cleaning application environment...");
                        //Clean command
                        Map<String, Object> cleanParams = new HashMap<>();
                        cleanParams.put("identifier", appPackage);
                        ((AndroidDriver) driver).executeScript("mobile:application:clean", cleanParams);

                        //Re-opens the application
                        Map<String, Object> openParams = new HashMap<>();
                        openParams.put("identifier", appPackage);
                        ((AndroidDriver) driver).executeScript("mobile:application:open", openParams);
                    } catch (Exception e) {
                        driver.quit();

                        throw e;
                    }
                }
            }
        }

        /**
         * Adds perfecto credentials to the list of capabilities. perfectoToken is enough for valid credentials
         * only takes username and password when token is not available.
         * @param perfectoUser the user's login
         * @param perfectoPass the user's password
         * @param perfectoToken the user's token
         * @param perfectoCapabilities the capabilities so far
         */
        private void setPerfectoCredentials(String perfectoUser, String perfectoPass, String perfectoToken, MutableCapabilities perfectoCapabilities){
            if (!perfectoToken.isEmpty()) {
                perfectoCapabilities.setCapability("securityToken", perfectoToken);
            } else if (!perfectoUser.isEmpty()) {
                perfectoCapabilities.setCapability("user", perfectoUser);
                if (!perfectoPass.isEmpty()) {
                    perfectoCapabilities.setCapability("password", perfectoPass);
                }
            }
        }


    }
}
