package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Asserts that there is no window with given URL.
 */
public class WindowDoesNotExistByUrlCommand extends CommandWithReturn {

    private String url;

    /**
     * Initializes a new instance of the {@link WindowDoesNotExistByUrlCommand} class.
     *
     * @param url The url for which there should be no window.
     */
    public WindowDoesNotExistByUrlCommand(String url) {
        super(String.format(Locale.getDefault(), Resources.getString("WindowDoesNotExistByUrlCommand_Info"), url));
        this.url = url;
    }

    @Override
    protected Object commandDelegate(IDriver driver) {
        return ((IWebDriver) driver).windowDoesNotExistByUrl(url);
    }
}
