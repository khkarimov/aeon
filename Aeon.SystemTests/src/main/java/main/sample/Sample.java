package main.sample;

import aeon.core.testabstraction.product.WebProduct;
import main.sample.pages.SamplePage;
import main.sample.pages.SampleUltiHomePage;
import main.sample.pages.VTeamSamplePage;
import main.ultipro.pages.HomePage;
import main.ultipro.pages.LoginPage;

public class Sample extends WebProduct {
    public SamplePage main;
    public LoginPage login;
    public HomePage home;
    public VTeamSamplePage startPage;
    public SampleUltiHomePage ultihome;

    public Sample() {
        main = new SamplePage();
    }

    @Override
    protected void afterLaunch() {
        super.afterLaunch();
        login = new LoginPage(getAutomationInfo());
        home = new HomePage(getAutomationInfo());
        startPage = new VTeamSamplePage(getAutomationInfo());
        ultihome = new SampleUltiHomePage(getAutomationInfo());
    }
}
