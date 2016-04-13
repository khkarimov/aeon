package main;

import echo.core.test_abstraction.context.Product;
import echo.core.test_abstraction.driver.Selenium;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class Sample extends Product<Selenium> {
    public SamplePage Main;

    public Sample() {
        Main = new SamplePage();
    }
}
