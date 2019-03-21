package main.pagewithiframe;

import aeon.core.testabstraction.product.WebProduct;
import main.pagewithiframe.pages.PageWithIFrame;

/**
 * Sample web product.
 */
public class IFrameWikiSample extends WebProduct {
    public PageWithIFrame pageWithIFrame;

    @Override
    protected void afterLaunch() {
        super.afterLaunch();
        pageWithIFrame = new PageWithIFrame(getAutomationInfo());
    }
}
