package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.Command;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.web.BrowserSizeMap;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.awt.*;

/**
 * Resize the currently focused browser window.
 * Usage:
 * Context.browser.resize(BrowserSize.Maximized);
 * Context.browser.resize(800, 600);
 */
public class ResizeCommand extends Command {

    private Dimension size;

    /**
     * Initializes a new instance of the {@link ResizeCommand} class.
     *
     * @param size The new browser size.
     */
    public ResizeCommand(String size) {
        super(Resources.getString("ResizeCommand_Info"));
        this.size = BrowserSizeMap.map(size);
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void driverDelegate(IDriver driver) {
        ((IWebDriver) driver).resize(size);
    }
}
