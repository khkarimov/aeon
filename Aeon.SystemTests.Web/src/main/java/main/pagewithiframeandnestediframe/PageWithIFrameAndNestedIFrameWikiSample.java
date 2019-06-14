package main.pagewithiframeandnestediframe;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.testabstraction.product.WebProduct;
import main.pagewithiframeandnestediframe.innerpages.InnerIFramePage;
import main.pagewithiframeandnestediframe.outerpages.PageWithIFrameAndNestedIFrame;

/**
 * Sample web product.
 */
public class PageWithIFrameAndNestedIFrameWikiSample extends WebProduct {
    public PageWithIFrameAndNestedIFrame pageWithIFrameAndNestedIFrame;

    /**
     * Create new browser using a provided AutomationInfo variable.
     *
     * @param automationInfo An AutomationInfo object provided to the function.
     */
    protected PageWithIFrameAndNestedIFrameWikiSample(AutomationInfo automationInfo) {
        super(automationInfo);
    }

    @Override
    protected void afterLaunch() {
        super.afterLaunch();
        pageWithIFrameAndNestedIFrame = new PageWithIFrameAndNestedIFrame(getAutomationInfo());
        pageWithIFrameAndNestedIFrame.iFrame = new InnerIFramePage(getAutomationInfo());
    }
}
