package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.CommandWithReturn;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.helpers.URLUtil;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.net.URL;
import java.util.Locale;

/**
 * Navigate the currently focused browser to the URL provided.
 * Usage:
 * Context.Browser.goToUrl("url String")
 * Context.Browser.goToUrl("url String", setMainWindowBoolean)
 * Context.Browser.goToUrl({@link java.net.URI})
 * Context.Browser.goToUrl({@link java.net.URI}, setMainWindowBoolean)
 */
public class GoToUrlCommand extends CommandWithReturn {

    private URL url;

    /**
     * Initializes a new instance of the {@link GoToUrlCommand} class.
     *
     * @param url The title of the window.
     */
    public GoToUrlCommand(String url) {
        super(String.format(Locale.getDefault(), Resources.getString("GoToUrlCommand_Info"), url));
        this.url = URLUtil.createURL(url);
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     * @return return the driver going to the new url.
     */
    @Override
    protected Object commandDelegate(IDriver driver) {
        return ((IWebDriver) driver).goToUrl(url);
    }
}
