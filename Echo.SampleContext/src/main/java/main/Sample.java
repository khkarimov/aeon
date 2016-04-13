package main;

import echo.core.test_abstraction.context.Product;
import echo.core.test_abstraction.driver.Browseable;
import echo.core.test_abstraction.driver.Selenium;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class Sample extends WebProduct<Selenium> implements Browseable {
    public SamplePage Main;

    public Sample() {
        Main = new SamplePage();
    }
}
