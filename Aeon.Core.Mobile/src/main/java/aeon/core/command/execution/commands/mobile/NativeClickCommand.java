package aeon.core.command.execution.commands.mobile;

import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IMobileDriver;

/**
 * Command for clicking on an element via coordinates.
 */
public class NativeClickCommand extends MobileWebControlCommand {

    /**
     * Clicks on an elements via coordinates.
     *
     * @param selector The selector of the element to click on.
     * @param webCommandInitializer The web command initializer to use.
     */
    public NativeClickCommand(IByWeb selector, WebCommandInitializer webCommandInitializer) {
        super(String.format(Resources.getString("ClickCommand_Info"), selector), selector, webCommandInitializer);
    }

    @Override
    protected void commandDelegate(IMobileDriver driver, WebControl control) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        driver.mobileClick(control);
    }
}
