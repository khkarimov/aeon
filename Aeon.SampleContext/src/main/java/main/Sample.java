package main;

import aeon.core.testabstraction.product.WebProduct;
import main.ultipro.HomePage;
import main.ultipro.LoginPage;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class Sample extends WebProduct {
    public SamplePage main;
    public LoginPage login;
    public HomePage home;
    public VTeamSamplePage startPage;
    public SampleUltihome ultihome;

    public Sample() {
        main = new SamplePage();
    }

    @Override
    protected void afterLaunch() {
        super.afterLaunch();
        login = new LoginPage(getAutomationInfo());
        home = new HomePage(getAutomationInfo());
        startPage = new VTeamSamplePage(getAutomationInfo());
        ultihome = new SampleUltihome(getAutomationInfo());
    }
}
