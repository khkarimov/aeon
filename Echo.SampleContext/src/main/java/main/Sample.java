package main;

import echo.core.test_abstraction.product.WebProduct;
import main.ultipro.HomePage;
import main.ultipro.LoginPage;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class Sample extends WebProduct {
    public SamplePage Main;
    public LoginPage Login;
    public HomePage Home;
    public VTeamSamplePage StartPage;

    public Sample() {
        Main = new SamplePage();
    }

    @Override
    protected void afterLaunch() {
        super.afterLaunch();
        Login = new LoginPage(getAutomationInfo());
        Home = new HomePage(getAutomationInfo());
        StartPage = new VTeamSamplePage(getAutomationInfo());
    }
}
