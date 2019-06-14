package ultiproapp;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.testabstraction.product.MobileProduct;
import ultiproapp.pages.shared.AccessCodePage;
import ultiproapp.pages.shared.loginpages.IOSIdentitySignInPage;
import ultiproapp.pages.shared.loginpages.LoginPage;

/**
 * Sample UltiPro mobile app product.
 */
public class UltiProApp extends MobileProduct {

    // Core Pages
    public AccessCodePage accessCodePage;
    public LoginPage loginPage;
    public IOSIdentitySignInPage iOSIdentitySignInPage;

    /**
     * Create new browser using a provided AutomationInfo variable.
     *
     * @param automationInfo An AutomationInfo object provided to the function.
     */
    protected UltiProApp(AutomationInfo automationInfo) {
        super(automationInfo);
    }

    @Override
    protected void afterLaunch() {
        super.afterLaunch();
        AutomationInfo automationInfo = getAutomationInfo();

        accessCodePage = new AccessCodePage(automationInfo);
        loginPage = new LoginPage(automationInfo);
        iOSIdentitySignInPage = new IOSIdentitySignInPage(automationInfo);
    }
}
