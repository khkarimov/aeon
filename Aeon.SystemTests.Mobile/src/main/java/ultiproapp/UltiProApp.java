package ultiproapp;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.testabstraction.product.MobileProduct;
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

    @Override
    protected void afterLaunch() {
        super.afterLaunch();
        AutomationInfo info = getAutomationInfo();

        accessCodePage = new AccessCodePage(info);
        loginPage = new LoginPage(info);
        iOSIdentitySignInPage = new IOSIdentitySignInPage(info);
    }
}
