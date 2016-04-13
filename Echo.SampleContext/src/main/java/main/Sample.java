package main;

import echo.core.framework_interaction.selenium.Selenium;
import echo.core.test_abstraction.context.Product;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class Sample extends Product<Selenium> {
    public SamplePage Main;

    public Sample() {
        super();

        Main = new SamplePage();
    }
}
