package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.logging.ILog;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by Steve Foo on 6/6/2016.
 */

/**
 * Takes the mouse pointer off of an element.
 */
public class MouseOutCommand extends WebControlCommand {

    /**
     * Initializes a new MouseOutCommand
     *
     * @param log         The logger
     * @param selector    The selector
     * @param initializer The initializer
     */
    public MouseOutCommand(ILog log, IBy selector, ICommandInitializer initializer) {
        super(log, String.format(Locale.getDefault(), Resources.getString("MouseOutCommand_Info"), selector), selector, initializer);
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl element) {
        driver.MouseOut(getGuid(), element);

    }

}
