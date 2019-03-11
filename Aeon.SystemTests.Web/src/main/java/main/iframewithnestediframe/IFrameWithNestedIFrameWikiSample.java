package main.iframewithnestediframe;

import aeon.core.testabstraction.product.WebProduct;
import main.iframewithnestediframe.pages.IFrameWithNestedIFrame;

/**
 * Sample web product.
 */
public class IFrameWithNestedIFrameWikiSample extends WebProduct {
    public IFrameWithNestedIFrame iFrameWithNestedIFrame;

    @Override
    protected void afterLaunch() {
        super.afterLaunch();
        iFrameWithNestedIFrame = new IFrameWithNestedIFrame(getAutomationInfo());
    }
}
