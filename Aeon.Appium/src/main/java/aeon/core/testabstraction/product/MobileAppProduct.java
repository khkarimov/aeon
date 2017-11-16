package aeon.core.testabstraction.product;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.Capability;
import aeon.core.testabstraction.models.Phone;

/**
 * Class to make a mobile app product.
 */
public class MobileAppProduct extends WebProduct {

    public Phone phone;

    /**
     * Default {@link MobileAppProduct} constructor.
     */
    public MobileAppProduct() {

    }

    /**
     * Create new browser using a provided AutomationInfo variable.
     *
     * @param automationInfo An AutomationInfo object provided to the function.
     */
    protected MobileAppProduct(AutomationInfo automationInfo) {
        phone = new Phone(automationInfo);
    }

    @Override
    public Capability getRequestedCapability() {
        return Capability.MOBILE;
    }

    @Override
    protected void afterLaunch() {
        super.afterLaunch();

        phone = new Phone(getAutomationInfo());
    }
}
