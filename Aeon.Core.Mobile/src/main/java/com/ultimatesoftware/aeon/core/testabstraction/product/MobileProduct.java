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
     * Create new mobile device object using the provided automation info object.
     *
     * @param automationInfo The automation info object to use.
     */
    public MobileProduct(AutomationInfo automationInfo) {
        super(automationInfo);
        mobileDevice = new MobileDevice(automationInfo);
    }
}
