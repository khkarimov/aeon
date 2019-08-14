package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Moves the mouse pointer over an element.
 */
public class MouseOverCommand extends WebControlCommand {

    /**
     * Initializes a new {@link MouseOverCommand}.
     *
     * @param selector    The selector
     * @param initializer The initializer
     */
    public MouseOverCommand(IByWeb selector, ICommandInitializer initializer) {
        super(String.format(Locale.getDefault(), Resources.getString("MouseOverCommand_Info"), selector), selector, initializer);
    }

    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.mouseOver(control);
    }
}
