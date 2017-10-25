package aeon.core.testabstraction.product;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.WebCommandExecutionFacade;
import aeon.core.command.execution.consumers.DelegateRunnerFactory;
import aeon.core.common.Capability;
import aeon.core.common.helpers.AjaxWaiter;
import aeon.core.common.helpers.StringUtils;
import aeon.core.common.web.BrowserType;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import aeon.core.framework.abstraction.adapters.IWebAdapter;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import aeon.core.testabstraction.models.Browser;
import org.joda.time.Duration;

/**
 * Class to make a web product.
 */
public class WebProduct extends Product {

    public Browser browser;

    /**
     * Default WebProduct constructor.
     */
    public WebProduct() {

    }

    /**
     * Create new browser using a provided AutomationInfo variable.
     *
     * @param automationInfo An AutomationInfo object provided to the function.
     */
    protected WebProduct(AutomationInfo automationInfo) {
        browser = new Browser(automationInfo);
    }

    @Override
    public Capability getRequestedCapability() {
        return Capability.WEB;
    }

    @Override
    protected void afterLaunch() {
        // Set WebCommandExecutionFacade
        long timeout = (long) configuration.getDouble(Configuration.Keys.TIMEOUT, 10);
        long ajaxTimeout = (long) configuration.getDouble(Configuration.Keys.AJAX_TIMEOUT, 20);

        commandExecutionFacade = new WebCommandExecutionFacade(
                new DelegateRunnerFactory(Duration.millis(250), Duration.standardSeconds(timeout)),
                new AjaxWaiter(this.automationInfo.getDriver(), Duration.standardSeconds(ajaxTimeout)));

        automationInfo.setCommandExecutionFacade(commandExecutionFacade);

        // Instantiate browser and optionally maximize the window
        browser = new Browser(getAutomationInfo());
        boolean maximizeBrowser = configuration.getBoolean(Configuration.Keys.MAXIMIZE_BROWSER, true);
        BrowserType browserType = browser.getBrowserType();
        if (maximizeBrowser
                && !browserType.equals(BrowserType.AndroidChrome)
                && !browserType.equals(BrowserType.IOSSafari)
                && !browserType.equals(BrowserType.AndroidHybridApp)
                && !browserType.equals(BrowserType.IOSHybridApp)) {
            browser.maximize();
        }

        // Optionally navigate to the environment
        String environment = getConfig(Configuration.Keys.ENVIRONMENT, "");
        if (StringUtils.isNotBlank(environment)) {
            String protocol = getConfig(Configuration.Keys.PROTOCOL, "https");
            if (StringUtils.isBlank(protocol)) {
                protocol = "https";
            }
            browser.goToUrl(protocol + "://" + environment);
        }
    }

    /**
     * Disables the ajax waiter.
     */
    public void disableAjaxWaiting() {
        configuration.setBoolean(Configuration.Keys.WAIT_FOR_AJAX_RESPONSES, false);
    }

    /**
     * Enables the ajax waiter.
     */
    public void enableAjaxWaiting() {
        configuration.setBoolean(Configuration.Keys.WAIT_FOR_AJAX_RESPONSES, true);
    }
}
