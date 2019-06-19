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

    public final AccessCodePage accessCodePage;
    public final LoginPage loginPage;
    public final IOSIdentitySignInPage iOSIdentitySignInPage;

    /**
     * Create a new instance of the UltiProApp models.
     *
     * @param automationInfo The automation info object to use.
     */
    protected UltiProApp(AutomationInfo automationInfo) {
        super(automationInfo);

        accessCodePage = new AccessCodePage(automationInfo);
        loginPage = new LoginPage(automationInfo);
        iOSIdentitySignInPage = new IOSIdentitySignInPage(automationInfo);
    }
}
