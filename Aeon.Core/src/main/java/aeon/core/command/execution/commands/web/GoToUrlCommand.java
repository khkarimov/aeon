package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.net.URL;
import java.util.Locale;

/**
 * <p>Navigate the currently focused browser to the URL provided.</p>
 * <p>Usage:</p>
 * <p>      Context.Browser.goToUrl("url String")</p>
 * <p>      Context.Browser.goToUrl("url String", setMainWindowBoolean)</p>
 * <p>      Context.Browser.goToUrl({@link java.net.URI})</p>
 * <p>      Context.Browser.goToUrl({@link java.net.URI}, setMainWindowBoolean)</p>
 */
public class GoToUrlCommand extends CommandWithReturn {
    private URL url;

    /**
     * Initializes a new instance of the GoToUrlCommand class.
     * @param url The title of the window.
     */
    public GoToUrlCommand(URL url) {
        super(String.format(Locale.getDefault(), Resources.getString("GoToUrlCommand_Info"), url.toString()));
        this.url = url;
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected Object commandDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        return ((IWebDriver) driver).goToUrl(url);
    }
}
