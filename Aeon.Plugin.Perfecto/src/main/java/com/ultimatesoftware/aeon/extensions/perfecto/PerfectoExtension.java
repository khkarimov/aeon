package com.ultimatesoftware.aeon.extensions.perfecto;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.CustomField;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import com.ultimatesoftware.aeon.core.common.exceptions.AeonLaunchException;
import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.core.extensions.ITestExecutionExtension;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapter;
import com.ultimatesoftware.aeon.core.testabstraction.product.Aeon;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import com.ultimatesoftware.aeon.extensions.selenium.SeleniumAdapter;
import com.ultimatesoftware.aeon.extensions.selenium.SeleniumConfiguration;
import com.ultimatesoftware.aeon.extensions.selenium.extensions.ISeleniumExtension;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * Test execution and Selenium extensions for using Aeon with Perfecto.
 */
@Extension
public class PerfectoExtension implements ITestExecutionExtension, ISeleniumExtension {

    private static final String TEST_REPORT_URL_LABEL = "Test Report URL: {}";
    private final IConfiguration configuration;

    private ReportiumClient reportiumClient;
    private ReportiumClientFactory reportiumClientFactory;
    private String correlationId;
    private String suiteName;
    private String testName;

    private boolean enabled;

    static Logger log = LoggerFactory.getLogger(PerfectoExtension.class);

    PerfectoExtension(ReportiumClientFactory reportiumClientFactory, IConfiguration configuration) {
        this.reportiumClientFactory = reportiumClientFactory;
        this.configuration = configuration;

        this.enabled = this.configuration.getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "").contains("perfectomobile.com") ||
                this.configuration.getBoolean(PerfectoConfiguration.Keys.PERFECTO_FORCED, false);
    }

    /**
     * Creates an instance for this extension.
     *
     * @return An instance of this extension.
     */
    public static Object createInstance() {

        IConfiguration configuration = new PerfectoConfiguration();

        try {
            configuration.loadConfiguration();
        } catch (IllegalAccessException | IOException e) {
            log.warn("Could not load plugin configuration.");
        }

        return new PerfectoExtension(new ReportiumClientFactory(), configuration);
    }

    @Override
    public void onStartUp(Configuration configuration, String correlationId) {
        this.correlationId = correlationId;
    }

    @Override
    public void onBeforeStart(String correlationId, String suiteName) {
        this.correlationId = correlationId;
        this.suiteName = suiteName;
    }

    @Override
    public void onBeforeLaunch(Configuration configuration) {
        // No actions needed
    }

    @Override
    public void onAfterLaunch(Configuration configuration, IAdapter adapter) {
        if (!this.enabled) {
            return;
        }

        List<String> configurationKeys = configuration.getConfigurationKeys();
        List<CustomField> customFields = new ArrayList<>();
        for (String configurationKey : configurationKeys) {
            customFields.add(new CustomField(configurationKey, configuration.getString(configurationKey, null)));
        }

        customFields.add(new CustomField("Aeon Version", Aeon.getVersion()));

        if (this.correlationId != null) {
            customFields.add(new CustomField("Correlation ID", this.correlationId));
        }

        if (this.suiteName != null) {
            customFields.add(new CustomField("Suite Name", this.suiteName));
        }

        PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                .withCustomFields(customFields.toArray(new CustomField[0]))
                .withWebDriver(((SeleniumAdapter) adapter).getWebDriver())
                .build();
        this.reportiumClient = this.reportiumClientFactory.createPerfectoReportiumClient(perfectoExecutionContext);
    }

    @Override
    public void onBeforeTest(String name, String... tags) {
        if (!this.enabled || this.reportiumClient == null) {
            return;
        }

        TestContext testContext = new TestContext();
        if (tags != null) {
            testContext = new TestContext.Builder().withTestExecutionTags(tags).build();
        }

        this.testName = name;

        this.reportiumClient.testStart(name, testContext);
    }

    @Override
    public void onSucceededTest() {
        if (!this.enabled || this.reportiumClient == null) {
            return;
        }

        reportiumClient.testStop(TestResultFactory.createSuccess());

        log.info(TEST_REPORT_URL_LABEL, reportiumClient.getReportUrl());
    }

    @Override
    public void onSkippedTest(String name, String... tags) {
        if (!this.enabled || this.reportiumClient == null) {
            return;
        }

        reportiumClient.testStop(TestResultFactory.createFailure("Skipped"));

        log.info(TEST_REPORT_URL_LABEL, reportiumClient.getReportUrl());
    }

    @Override
    public void onFailedTest(String reason, Throwable e) {
        if (!this.enabled || this.reportiumClient == null) {
            return;
        }

        reportiumClient.testStop(TestResultFactory.createFailure(reason, e));

        log.info(TEST_REPORT_URL_LABEL, reportiumClient.getReportUrl());
    }

    @Override
    public void onBeforeStep(String message) {
        if (!this.enabled || this.reportiumClient == null) {
            return;
        }

        reportiumClient.stepStart(message);
    }

    @Override
    public void onExecutionEvent(String eventName, Object payload) {
        // No actions needed
    }

    @Override
    public void onDone() {
        // No actions needed
    }

    @Override
    public void onGenerateCapabilities(Configuration configuration, MutableCapabilities capabilities) {
        if (!this.enabled) {
            return;
        }
        String perfectoUser = this.configuration.getString(PerfectoConfiguration.Keys.PERFECTO_USER, "");
        String perfectoPass = this.configuration.getString(PerfectoConfiguration.Keys.PERFECTO_PASS, "");
        String perfectoToken = this.configuration.getString(PerfectoConfiguration.Keys.PERFECTO_TOKEN, "");
        boolean perfectoAutoInstrument = this.configuration.getBoolean(PerfectoConfiguration.Keys.PERFECTO_AUTOINSTRUMENT, false);
        boolean perfectoSensorInstrument = this.configuration.getBoolean(PerfectoConfiguration.Keys.PERFECTO_SENSORINSTRUMENT, false);
        String perfectoDeviceDescription = this.configuration.getString(PerfectoConfiguration.Keys.DEVICE_DESCRIPTION, "");

        String perfectoReportJobName = this.configuration.getString(PerfectoConfiguration.Keys.REPORT_JOB_NAME, "");
        int perfectoReportJobNumber = (int) this.configuration.getDouble(PerfectoConfiguration.Keys.REPORT_JOB_NUMBER, 0);
        String perfectoReportJobBranch = this.configuration.getString(PerfectoConfiguration.Keys.REPORT_JOB_BRANCH, "");
        String perfectoReportProjectName = this.configuration.getString(PerfectoConfiguration.Keys.REPORT_PROJECT_NAME, "");
        String perfectoReportProjectVersion = this.configuration.getString(PerfectoConfiguration.Keys.REPORT_PROJECT_VERSION, "");
        String perfectoReportTags = this.configuration.getString(PerfectoConfiguration.Keys.REPORT_TAGS, "");
        String perfectoReportCustomFields = this.configuration.getString(PerfectoConfiguration.Keys.REPORT_CUSTOM_FIELDS, "");

        setScriptNameCapability(capabilities, perfectoReportJobName);

        // Set credentials
        setPerfectoCredentials(perfectoUser, perfectoPass, perfectoToken, capabilities);

        // Set instrumentation
        capabilities.setCapability("autoInstrument", perfectoAutoInstrument);
        capabilities.setCapability("sensorInstrument", perfectoSensorInstrument);

        // Set reporting
        if (!perfectoReportProjectName.isEmpty()) {
            capabilities.setCapability("report.projectName", perfectoReportProjectName);
        }
        if (!perfectoReportProjectVersion.isEmpty()) {
            capabilities.setCapability("report.projectVersion", perfectoReportProjectVersion);
        }
        if (!perfectoReportTags.isEmpty()) {
            capabilities.setCapability("report.tags", perfectoReportTags);
        }
        if (!perfectoReportJobName.isEmpty()) {
            capabilities.setCapability("report.jobName", perfectoReportJobName);
        }
        if (perfectoReportJobNumber != 0) {
            capabilities.setCapability("report.jobNumber", perfectoReportJobNumber);
        }
        if (!perfectoReportJobBranch.isEmpty()) {
            capabilities.setCapability("report.jobBranch", perfectoReportJobBranch);
        }
        if (!perfectoReportCustomFields.isEmpty()) {
            capabilities.setCapability("report.customFields", perfectoReportCustomFields);
        }
        if (!perfectoDeviceDescription.isEmpty()) {
            capabilities.setCapability("description", perfectoDeviceDescription);
        }
    }

    @Override
    public void onAfterLaunch(Configuration configuration, WebDriver driver) {
        if (!enabled) {
            return;
        }

        boolean ensureCleanEnvironment = configuration.getBoolean(SeleniumConfiguration.Keys.ENSURE_CLEAN_ENVIRONMENT, true);
        String appPackage = configuration.getString("aeon.appium.android.app_package", "");

        // Only if AndroidHybridApp
        // Cleans the app data for a fresh new session.
        if (driver instanceof AndroidDriver && ensureCleanEnvironment && !appPackage.isEmpty()) {
            try {
                log.info("Cleaning application environment...");
                // Clean command
                Map<String, Object> cleanParams = new HashMap<>();
                cleanParams.put("identifier", appPackage);
                ((AndroidDriver) driver).executeScript("mobile:application:clean", cleanParams);

                // Re-opens the application
                Map<String, Object> openParams = new HashMap<>();
                openParams.put("identifier", appPackage);
                ((AndroidDriver) driver).executeScript("mobile:application:open", openParams);
            } catch (Exception e) {
                driver.quit();

                throw e;
            }
        }
    }

    /**
     * Adds perfecto credentials to the list of capabilities. perfectoToken is enough for valid credentials
     * only takes username and password when token is not available.
     *
     * @param perfectoUser         the user's login
     * @param perfectoPass         the user's password
     * @param perfectoToken        the user's token
     * @param perfectoCapabilities the capabilities so far
     */
    private void setPerfectoCredentials(String perfectoUser, String perfectoPass, String perfectoToken, MutableCapabilities perfectoCapabilities) {

        if (!perfectoToken.isEmpty()) {
            perfectoCapabilities.setCapability("securityToken", perfectoToken);
        } else if (!perfectoUser.isEmpty() && !perfectoPass.isEmpty()) {
            perfectoCapabilities.setCapability("user", perfectoUser);
            perfectoCapabilities.setCapability("password", perfectoPass);
        } else {
            throw new AeonLaunchException("Please specify either a token or username and password for Perfecto.");
        }
    }

    private void setScriptNameCapability(MutableCapabilities capabilities, String perfectoReportJobName) {
        StringJoiner perfectoScriptNameJoiner = new StringJoiner(" ");
        if (this.testName != null) {
            perfectoScriptNameJoiner.add(this.testName);
        } else if (this.suiteName != null) {
            perfectoScriptNameJoiner.add(this.suiteName);
        } else {
            if (!perfectoReportJobName.isEmpty()) {
                perfectoScriptNameJoiner.add(perfectoReportJobName);
            }
            if (this.correlationId != null) {
                perfectoScriptNameJoiner.add(this.correlationId);
            }
        }

        if (perfectoScriptNameJoiner.length() > 0) {
            capabilities.setCapability("scriptName", perfectoScriptNameJoiner.toString());
        }
    }
}
