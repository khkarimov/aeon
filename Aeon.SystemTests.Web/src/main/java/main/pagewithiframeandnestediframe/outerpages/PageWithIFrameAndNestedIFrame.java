package main.pagewithiframeandnestediframe.outerpages;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.selectors.By;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Button;
import com.ultimatesoftware.aeon.core.testabstraction.product.WebProduct;
import main.pagewithiframeandnestediframe.innerpages.InnerIFramePage;

/**
 * Wikipedia's main page within an iFrame.
 */
public class PageWithIFrameAndNestedIFrame extends WebProduct {
    public InnerIFramePage iFrame;
    public Button popupButton;

    /**
     * The outer iFrame's popup button is accessed through this method, the PageWithIFrameAndNestedIFrame method.
     *
     * @param automationInfo Grabs the automation information to help initialize the object.
     */
    public PageWithIFrameAndNestedIFrame(AutomationInfo automationInfo) {
        popupButton = new Button(automationInfo, By.cssSelector("#popup-button"), By.cssSelector("iframe[id*=ContentFrame]"));
    }
}
