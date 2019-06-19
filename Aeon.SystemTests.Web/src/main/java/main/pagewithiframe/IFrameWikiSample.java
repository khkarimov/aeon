package main.pagewithiframe;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.testabstraction.product.WebProduct;
import main.pagewithiframe.pages.PageWithIFrame;

/**
 * Sample web product.
 */
public class IFrameWikiSample extends WebProduct {
    public final PageWithIFrame pageWithIFrame;

    /**
     * Create a new instance of the Wiki Sample models.
     *
     * @param automationInfo The automation info object to use.
     */
    protected IFrameWikiSample(AutomationInfo automationInfo) {
        super(automationInfo);

        pageWithIFrame = new PageWithIFrame(getAutomationInfo());
    }
}
