package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.CommandWithReturn;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Switches focus to a specified window by its URL.
 * Usage:
 * Context.browser.switchToWindowByUrl("windowURL");
 */
public class SwitchToWindowByUrlCommand extends CommandWithReturn {

    private String url;

    /**
     * Initializes a new instance of the {@link SwitchToWindowByUrlCommand} class.
     *
     * @param url The url of the desired window.
     */
    public SwitchToWindowByUrlCommand(String url) {
        super(String.format(Resources.getString("SwitchToWindowByUrlCommand_Info"), url));
        this.url = url;
    }

    @Override
    protected Object commandDelegate(IDriver driver) {
        return ((IWebDriver) driver).switchToWindowByUrl(url);
    }
}
