package main;

import echo.core.test_abstraction.product.WebProduct;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class Sample extends WebProduct {
    public SamplePage Main;
    public LoginPage Login;

    public Sample() {
        Main = new SamplePage();
    }

    @Override
    protected void afterLaunch() {
        super.afterLaunch();
        Login = new LoginPage(getAutomationInfo());
    }
}
