package com.ultimatesoftware.aeon.extensions.continuum;

import com.levelaccess.continuum.Continuum;
import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.core.extensions.ITestExecutionExtension;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapter;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import com.ultimatesoftware.aeon.extensions.accessibility.IAccessibilityExtension;
import com.ultimatesoftware.aeon.extensions.selenium.extensions.ISeleniumExtension;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Accessibility extension for Continuum.
 */
@Extension
public class ContinuumExtension implements
        ISeleniumExtension,
        ITestExecutionExtension,
        IAccessibilityExtension {

    private IConfiguration configuration;
    private Continuum continuum;
    private WebDriver driver;

    static Logger log = LoggerFactory.getLogger(ContinuumExtension.class);

    ContinuumExtension(IConfiguration configuration, Continuum continuum) {
        this.configuration = configuration;
        this.continuum = continuum;
    }

    /**
     * Creates an instance for this extension.
     *
     * @return An instance of this extension.
     */
    public static Object createInstance() {

        IConfiguration configuration = new ContinuumConfiguration();

        try {
            configuration.loadConfiguration();
        } catch (IllegalAccessException | IOException e) {
            log.warn("Could not load plugin configuration.");
        }

        return new ContinuumExtension(configuration, new Continuum());
    }

    @Override
    public void onGenerateCapabilities(Configuration configuration, MutableCapabilities capabilities) {
        // Not needed
    }

    @Override
    public void onAfterLaunch(Configuration configuration, WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void onStartUp(Configuration configuration, String correlationId) {
        // Not needed
    }

    @Override
    public void onBeforeStart(String correlationId, String suiteName) {
        // Not needed
    }

    @Override
    public void onBeforeLaunch(Configuration configuration) {
        // Not needed
    }

    @Override
    public void onAfterLaunch(Configuration configuration, IAdapter adapter) {
        // Not needed
    }

    @Override
    public void onBeforeTest(String name, String... tags) {
        // Not needed
    }

    @Override
    public void onSucceededTest() {
        if (!this.continuum.getAccessibilityConcerns().isEmpty()) {
            throw new ContinuumAccessibilityException(this.continuum);
        }
    }

    @Override
    public void onSkippedTest(String name, String... tags) {
        // Not needed
    }

    @Override
    public void onFailedTest(String reason, Throwable e) {
        // Not needed
    }

    @Override
    public void onBeforeStep(String message) {
        if (this.configuration.getBoolean(ContinuumConfiguration.Keys.IMPLICIT_VALIDATIONS, true)
                && message.startsWith("THEN ")) {
            this.runAccessibilityTests(message.substring(5));
        }
    }

    @Override
    public void onDone() {
        // Not needed
    }

    @Override
    public void onExecutionEvent(String eventName, Object payload) {
        // Not needed
    }

    @Override
    public void runAccessibilityTests(String pageName) {
        this.continuum.setUp(this.driver);
        this.continuum.runAllTests();
    }
}
