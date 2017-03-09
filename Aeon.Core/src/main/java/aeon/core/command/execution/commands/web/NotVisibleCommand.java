package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by Steve Foo on 6/29/2016.
 */
public class NotVisibleCommand extends WebControlCommand {

    /**
     * Initializes a new instance of the NotVisible.
     *
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     */
    public NotVisibleCommand(IBy selector, ICommandInitializer commandInitializer) {
        super(String.format(Locale.getDefault(), Resources.getString("NotVisibleCommand_Info"), selector), selector, commandInitializer);
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver  The web driver.
     * @param control The web control.
     */
    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.NotVisible(getGuid(), control);
    }
}
