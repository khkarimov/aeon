package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Takes the mouse pointer off of an element.
 */
public class MouseOutCommand extends WebControlCommand {

    /**
     * Initializes a new {@link MouseOutCommand}.
     *
     * @param selector    The selector
     * @param initializer The initializer
     */
    public MouseOutCommand(IByWeb selector, ICommandInitializer initializer) {
        super(String.format(Locale.getDefault(), Resources.getString("MouseOutCommand_Info"), selector), selector, initializer);
    }

    @Override
    protected void commandDelegate(IWebDriver driver, WebControl element) {
        driver.mouseOut(element);
    }
}
