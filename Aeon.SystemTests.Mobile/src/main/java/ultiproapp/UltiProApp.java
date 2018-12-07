package ultiproapp;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.testabstraction.product.MobileProduct;
import ultiproapp.modals.ProfileEditModal;
import ultiproapp.modals.WhatIsCompanyAccessCodeModal;
import ultiproapp.navigation.TabBar;
import ultiproapp.navigation.menu.MenuSlider;
import ultiproapp.pages.core.devandfeedback.DevAndFeedbackHubPage;
import ultiproapp.pages.core.directory.DirectoryFilterPage;
import ultiproapp.pages.core.directory.DirectoryPage;
import ultiproapp.pages.core.pay.PayHubPage;
import ultiproapp.pages.core.pay.PayStatementDetailsPage;
import ultiproapp.pages.core.pay.PayStatementPage;
import ultiproapp.pages.core.profile.ProfilePage;
import ultiproapp.pages.shared.AccessCodePage;
import ultiproapp.pages.shared.HomePage;
import ultiproapp.pages.shared.loginpages.AndroidIdentitySignInPage;
import ultiproapp.pages.shared.loginpages.IOSIdentitySignInPage;
import ultiproapp.pages.shared.loginpages.LoginPage;
import ultiproapp.pages.shared.loginpages.WebIdentitySignInPage;
import ultiproapp.pages.shared.personhubpage.PersonHubPage;
import ultiproapp.pages.shared.teampages.MyTeamListPage;
import ultiproapp.pages.shared.teampages.TeamHubPage;

/**
 * Sample UltiPro mobile app product.
 */
public class UltiProApp extends MobileProduct {

    // Navigation Controllers
    public TabBar tabBar;
    public MenuSlider menuSlider;

    // Core Pages
    public AccessCodePage accessCodePage;
    public WhatIsCompanyAccessCodeModal whatIsCompanyAccessCodeModal;
    public LoginPage loginPage;
    public WebIdentitySignInPage webIdentitySignInPage;
    public IOSIdentitySignInPage iOSIdentitySignInPage;
    public AndroidIdentitySignInPage androidIdentitySignInPage;
    public HomePage homePage;
    public ProfilePage profilePage;
    public ProfileEditModal profileEditModal;
    public PayHubPage payHubPage;
    public PayStatementPage payStatementPage;
    public PayStatementDetailsPage payStatementDetailsPage;
    public TeamHubPage teamHubPage;
    public MyTeamListPage myTeamListPage;
    public PersonHubPage personHubPage;
    public DevAndFeedbackHubPage devAndFeedbackHubPage;
    public DirectoryPage directoryPage;
    public DirectoryFilterPage directoryFilterPage;

    @Override
    protected void afterLaunch() {
        super.afterLaunch();
        AutomationInfo info = getAutomationInfo();

        tabBar = new TabBar(info);
        menuSlider = new MenuSlider(info);

        accessCodePage = new AccessCodePage(info);
        whatIsCompanyAccessCodeModal = new WhatIsCompanyAccessCodeModal(info);
        loginPage = new LoginPage(info);
        webIdentitySignInPage = new WebIdentitySignInPage(info);
        iOSIdentitySignInPage = new IOSIdentitySignInPage(info);
        androidIdentitySignInPage = new AndroidIdentitySignInPage(info);
        homePage = new HomePage(info);
        profilePage = new ProfilePage(info);
        profileEditModal = new ProfileEditModal(info);
        payHubPage = new PayHubPage(info);
        payStatementPage = new PayStatementPage(info);
        payStatementDetailsPage = new PayStatementDetailsPage(info);
        teamHubPage = new TeamHubPage(info);
        myTeamListPage = new MyTeamListPage(info);
        personHubPage = new PersonHubPage(info);
        devAndFeedbackHubPage = new DevAndFeedbackHubPage(info);
        directoryPage = new DirectoryPage(info);
        directoryFilterPage = new DirectoryFilterPage(info);

    }
}
