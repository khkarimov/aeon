package main.pagewithiframeandnestediframe;

import aeon.core.testabstraction.product.WebProduct;
import main.pagewithiframeandnestediframe.inneriframe.innerpages.InnerIFramePage;
import main.pagewithiframeandnestediframe.outerpages.PageWithIFrameAndNestedIFrame;

/**
 * Sample web product.
 */
public class PageWithIFrameAndNestedIFrameWikiSample extends WebProduct {
    public PageWithIFrameAndNestedIFrame pageWithIFrameAndNestedIFrame;

    @Override
    protected void afterLaunch() {
        super.afterLaunch();
        pageWithIFrameAndNestedIFrame = new PageWithIFrameAndNestedIFrame(getAutomationInfo());
        pageWithIFrameAndNestedIFrame.iFrame = new InnerIFramePage(getAutomationInfo());
    }
}
