package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

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
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        return ((IWebDriver) driver).switchToWindowByUrl(url);
    }
}
