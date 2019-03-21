package com.ultimatesoftware.aeon.core.command.execution.commands.mobile;

import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IMobileDriver;

/**
 * Sets the GPS location on a mobile device.
 */
public class SwitchToWebViewCommand extends MobileCommand {

    private IByWeb selector;

    /**
     * Initializes a new instance of the {@link SwitchToWebViewCommand} class.
     * @param selector Selector of an element in the web view to switch to
     */
    public SwitchToWebViewCommand(IByWeb selector) {
        super(Resources.getString("SwitchToWebViewCommand_Info"));
        this.selector = selector;
    }

    /**
     * The method which provides the logic for the command.
     */
    @Override
    protected void driverDelegate(IMobileDriver driver) {
        driver.switchToWebView(selector);
    }
}
