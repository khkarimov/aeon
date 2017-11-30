package aeon.core.testabstraction.product;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.Capability;
import aeon.core.testabstraction.models.MobileDevice;

/**
 * Class to make a mobile app product.
 */
public class MobileProduct extends WebProduct {

    public MobileDevice mobileDevice;

    /**
     * Default {@link MobileProduct} constructor.
     */
    public MobileProduct() {

    }

    /**
     * Create new browser using a provided AutomationInfo variable.
     *
     * @param automationInfo An AutomationInfo object provided to the function.
     */
    protected MobileProduct(AutomationInfo automationInfo) {
        mobileDevice = new MobileDevice(automationInfo);
    }

    @Override
    public Capability getRequestedCapability() {
        return Capability.MOBILE;
    }

    @Override
    protected void afterLaunch() {
        super.afterLaunch();

        mobileDevice = new MobileDevice(getAutomationInfo());
    }
}
