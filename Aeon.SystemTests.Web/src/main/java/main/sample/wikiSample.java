package main.sample;

import aeon.core.testabstraction.product.WebProduct;
import main.ultipro.pages.IframePage;

/**
 * Sample web product.
 */
public class wikiSample extends WebProduct {
    public IframePage iFramePage;

    @Override
    protected void afterLaunch() {
        super.afterLaunch();
        iFramePage = new IframePage(getAutomationInfo());
    }
}
