package com.ultimatesoftware.aeon.core.testabstraction.product;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.Capability;
import com.ultimatesoftware.aeon.core.common.helpers.StringUtils;
import com.ultimatesoftware.aeon.core.common.web.BrowserType;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IBrowserType;
import com.ultimatesoftware.aeon.core.extensions.WebProductTypeExtension;
import com.ultimatesoftware.aeon.core.testabstraction.models.Browser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to make a web product.
 */
public class WebProduct extends Product {

    private static Logger log = LoggerFactory.getLogger(WebProduct.class);

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
        super.afterLaunch();

        IBrowserType browserType = ((WebConfiguration) configuration).getBrowserType();
        log.info("Product successfully launched with {}", browserType.getKey());

        // Set WebCommandExecutionFacade
        new WebProductTypeExtension().createCommandExecutionFacade(this.automationInfo);

        // Instantiate browser and optionally maximize the window
        browser = new Browser(getAutomationInfo());
        boolean maximizeBrowser = configuration.getBoolean(WebConfiguration.Keys.MAXIMIZE_BROWSER, true);
        if (maximizeBrowser
                && (browserType == BrowserType.CHROME
                || browserType == BrowserType.FIREFOX
                || browserType == BrowserType.OPERA
                || browserType == BrowserType.INTERNET_EXPLORER
                || browserType == BrowserType.EDGE)) {
            browser.maximize();
        }

        // Optionally navigate to the environment
        String environment = getConfig(WebConfiguration.Keys.ENVIRONMENT, "");
        if (StringUtils.isNotBlank(environment)) {
            String protocol = getConfig(WebConfiguration.Keys.PROTOCOL, "https");
            if (StringUtils.isBlank(protocol)) {
                protocol = "https";
            }
            browser.goToUrl(protocol + "://" + environment);
        }
    }

    @Override
    protected void onLaunchFailure(Exception e) {

        if (browser != null) {
            try {
                browser.quit();
            } catch (Exception exceptionToSuppress) {
                e.addSuppressed(exceptionToSuppress);
            }
        }
    }

    /**
     * Disables the ajax waiter.
     */
    public void disableAjaxWaiting() {
        configuration.setBoolean(WebConfiguration.Keys.WAIT_FOR_AJAX_RESPONSES, false);
    }

    /**
     * Enables the ajax waiter.
     */
    public void enableAjaxWaiting() {
        configuration.setBoolean(WebConfiguration.Keys.WAIT_FOR_AJAX_RESPONSES, true);
    }
}
