package main.iframe;

import aeon.core.testabstraction.product.WebProduct;
import main.iframe.pages.IFramePage;

/**
 * Sample web product.
 */
public class IFrameWikiSample extends WebProduct {
    public IFramePage iFramePage;

    @Override
    protected void afterLaunch() {
        super.afterLaunch();
        iFramePage = new IFramePage(getAutomationInfo());
    }
}
