package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.CommandWithReturn;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.net.URL;
import java.util.Locale;

/**
 * <p>Navigate the currently focused browser to the URL provided.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.Browser.GoToUrl("url String")</p>
 * <p>      Context.Browser.GoToUrl("url String", setMainWindowBoolean)</p>
 * <p>      Context.Browser.GoToUrl({@link java.net.URI})</p>
 * <p>      Context.Browser.GoToUrl({@link java.net.URI}, setMainWindowBoolean)</p>
 */
public class GoToUrlCommand extends CommandWithReturn {
    private URL url;

    /**
     * Initializes a new instance of the GoToUrlCommand class.
     *
     * @param log The logger.
     * @param url The title of the window.
     */
    public GoToUrlCommand(ILog log, URL url) {
        super(log, String.format(Locale.getDefault(), Resources.getString("GoToUrlCommand_Info"), url.toString()));
        this.url = url;
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected Object CommandDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        return ((IWebDriver) driver).GoToUrl(getGuid(), url);
    }
}
