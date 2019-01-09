package main.sample;

import aeon.core.testabstraction.product.WebProduct;
import main.sample.pages.VTeamSamplePage;
import main.ultipro.pages.HomePage;
import main.ultipro.pages.LoginPage;

/**
 * Sample web product.
 */
public class Sample extends WebProduct {
    public LoginPage login;
    public HomePage home;
    public VTeamSamplePage startPage;

    @Override
    protected void afterLaunch() {
        super.afterLaunch();
        login = new LoginPage(getAutomationInfo());
        home = new HomePage(getAutomationInfo());
        startPage = new VTeamSamplePage(getAutomationInfo());
    }
}
