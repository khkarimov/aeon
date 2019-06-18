package com.ultimatesoftware.aeon.core.testabstraction.product;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.Capabilities;
import com.ultimatesoftware.aeon.core.common.Capability;
import com.ultimatesoftware.aeon.core.testabstraction.models.MobileDevice;

/**
 * Class to make a mobile app product.
 */
@Capability(Capabilities.MOBILE)
public class MobileProduct extends WebProduct {

    public MobileDevice mobileDevice;

    /**
     * Create new browser using a provided AutomationInfo variable.
     *
     * @param automationInfo An AutomationInfo object provided to the function.
     */
    public MobileProduct(AutomationInfo automationInfo) {
        super(automationInfo);
        mobileDevice = new MobileDevice(automationInfo);
    }
}
