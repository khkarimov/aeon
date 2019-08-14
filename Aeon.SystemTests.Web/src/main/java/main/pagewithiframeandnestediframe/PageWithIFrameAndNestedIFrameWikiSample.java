package main.pagewithiframeandnestediframe;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.testabstraction.product.WebProduct;
import main.pagewithiframeandnestediframe.outerpages.PageWithIFrameAndNestedIFrame;

/**
 * Sample web product for testing IFrames.
 */
public class PageWithIFrameAndNestedIFrameWikiSample extends WebProduct {
    public final PageWithIFrameAndNestedIFrame pageWithIFrameAndNestedIFrame;

    /**
     * Create a new instance of the IFrame Wiki Sample models.
     *
     * @param automationInfo The automation info object to use.
     */
    public PageWithIFrameAndNestedIFrameWikiSample(AutomationInfo automationInfo) {
        super(automationInfo);
        pageWithIFrameAndNestedIFrame = new PageWithIFrameAndNestedIFrame(getAutomationInfo());
    }
}
