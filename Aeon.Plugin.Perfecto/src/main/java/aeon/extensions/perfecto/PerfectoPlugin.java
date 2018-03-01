package aeon.extensions.perfecto;

import aeon.core.extensions.ITestExecutionExtension;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.testabstraction.product.Aeon;
import aeon.core.testabstraction.product.Configuration;
import aeon.selenium.appium.AppiumAdapter;
import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.CustomField;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Plugin class for Perfecto Plugin.
 */
public class PerfectoPlugin extends Plugin {

    private ReportiumClient reportiumClient;

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

    private static Logger log = LogManager.getLogger(PerfectoPlugin.class);

    /**
     * Test execution extension for sending test details to Perfecto.
     */
    @Extension
    public class PerfectoTestExecutionExtension implements ITestExecutionExtension {

        @Override
        public void onStartUp(Configuration configuration) {

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
                    .withWebDriver(((AppiumAdapter) adapter).getMobileWebDriver())
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
        public void onFailedTest(String reason) {
            reportiumClient.testStop(TestResultFactory.createFailure(reason));

            log.info("Test Report URL: " + reportiumClient.getReportUrl());
        }

        @Override
        public void onBeforeStep(String message) {
            reportiumClient.stepEnd("STEP END");
            reportiumClient.stepStart(message);
        }

        @Override
        public void onDone() {

        }
    }
}
