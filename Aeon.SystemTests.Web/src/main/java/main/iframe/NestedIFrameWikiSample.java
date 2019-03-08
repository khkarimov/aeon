package main.iframe;

import aeon.core.testabstraction.product.WebProduct;
import main.iframe.pages.NestedIFramePage;

/**
 * Sample web product.
 */
public class NestedIFrameWikiSample extends WebProduct {
    public NestedIFramePage nestedIFramePage;

    @Override
    protected void afterLaunch() {
        super.afterLaunch();
        nestedIFramePage = new NestedIFramePage(getAutomationInfo());
    }
}
