package com.ultimatesoftware.aeon.core.command.execution.commands.mobile;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IMobileDriver;

/**
 * Command for clicking on an element via coordinates.
 */
public class NativeClickCommand extends MobileWebControlCommand {

    /**
     * Clicks on an elements via coordinates.
     *
     * @param selector              The selector of the element to click on.
     * @param webCommandInitializer The web command initializer to use.
     */
    public NativeClickCommand(IByWeb selector, WebCommandInitializer webCommandInitializer) {
        super(String.format(Resources.getString("ClickCommand_Info"), selector), selector, webCommandInitializer);
    }

    @Override
    protected void commandDelegate(IMobileDriver driver, WebControl control) {
        driver.mobileClick(control);
    }
}
