package aeon.extensions.testmago;

import aeon.core.common.interfaces.IConfiguration;
import aeon.core.extensions.ITestExecutionExtension;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.testabstraction.product.Configuration;
import aeon.selenium.extensions.ISeleniumExtension;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Test execution extensions for sending test details to Perfecto.
 */
@Extension
public class TestMagoExtension implements ITestExecutionExtension, ISeleniumExtension {

    private IConfiguration configuration;

    private static Logger log = LoggerFactory.getLogger(TestMagoExtension.class);

    @Override
    public void onStartUp(Configuration configuration, String correlationId) {
        // No actions needed
    }

    @Override
    public void onBeforeStart(String correlationId, String suiteName) {
        // No actions needed
    }

    @Override
    public void onBeforeLaunch(Configuration configuration) {
        // No actions needed
    }

    @Override
    public void onAfterLaunch(Configuration configuration, IAdapter adapter) {
        // No actions needed
    }

    @Override
    public void onBeforeTest(String name, String... tags) {
        // No actions needed
    }

    @Override
    public void onSucceededTest() {
        // No actions needed
    }

    @Override
    public void onSkippedTest(String name, String... tags) {
        // No actions needed
    }

    @Override
    public void onFailedTest(String reason, Throwable e) {
        // No actions needed
    }

    @Override
    public void onBeforeStep(String message) {
        // No actions needed
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

        // Check if SauceLabsConfiguration has been instantiated
        if (this.configuration == null) {
            this.configuration = new TestMagoConfiguration();
            try {
                this.configuration.loadConfiguration();
            } catch (IllegalAccessException | IOException e) {
                log.info("Could not load plugin configuration, using Aeon configuration instead");
                this.configuration = configuration;
            }
        }

        // Set variables
        String username = this.configuration.getString(TestMagoConfiguration.Keys.TESTMAGO_USERNAME, "");
        String accessKey = this.configuration.getString(TestMagoConfiguration.Keys.TESTMAGO_ACCESS_KEY, "");
        String apiKey = this.configuration.getString(TestMagoConfiguration.Keys.TESTMAGO_API_KEY, "");
        String appId = this.configuration.getString(TestMagoConfiguration.Keys.TESTMAGO_APP_ID, "");

        // Set credentials
        if (!username.isEmpty()) {
            capabilities.setCapability("username", username);
        }

        if (!accessKey.isEmpty()) {
            capabilities.setCapability("accessKey", accessKey);
        }

        if (!apiKey.isEmpty()) {
            capabilities.setCapability("testobject_api_key", apiKey);
        }

        if (!appId.isEmpty()) {
            capabilities.setCapability("testobject_api_id", appId);
        }
    }

    @Override
    public void onAfterLaunch(Configuration configuration, WebDriver driver) {
        // Not needed
    }
}
