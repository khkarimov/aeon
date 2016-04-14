package main;

import echo.core.test_abstraction.context.Product;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class Sample extends Product {
    public SamplePage Main;

    public Sample() {
        super(new SampleConfiguration());

        Main = new SamplePage();
    }
}
