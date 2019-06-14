package main.pagewithiframe;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.testabstraction.product.WebProduct;
import main.pagewithiframe.pages.PageWithIFrame;

/**
 * Sample web product.
 */
public class IFrameWikiSample extends WebProduct {
    public PageWithIFrame pageWithIFrame;

    /**
     * Create new browser using a provided AutomationInfo variable.
     *
     * @param automationInfo An AutomationInfo object provided to the function.
     */
    protected IFrameWikiSample(AutomationInfo automationInfo) {
        super(automationInfo);
    }

    @Override
    protected void afterLaunch() {
        super.afterLaunch();
        pageWithIFrame = new PageWithIFrame(getAutomationInfo());
    }
}
