package aeon.core.testabstraction.product;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.Capability;
import aeon.core.common.helpers.StringUtils;
import aeon.core.common.web.BrowserType;
import aeon.core.common.web.interfaces.IBrowserType;
import aeon.core.extensions.WebProductTypeExtension;
import aeon.core.testabstraction.models.Browser;
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
        log.info("Product successfully launched with {}", browserType);

        // Set WebCommandExecutionFacade
        new WebProductTypeExtension().createCommandExecutionFacade(this.automationInfo);

        // Instantiate browser and optionally maximize the window
        browser = new Browser(getAutomationInfo());
        boolean maximizeBrowser = configuration.getBoolean(WebConfiguration.Keys.MAXIMIZE_BROWSER, true);
        if (maximizeBrowser
                && !browserType.equals(BrowserType.ANDROID_CHROME)
                && !browserType.equals(BrowserType.IOS_SAFARI)
                && !browserType.equals(BrowserType.ANDROID_CHROME)
                && !browserType.equals(BrowserType.IOS_SAFARI)) {
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
