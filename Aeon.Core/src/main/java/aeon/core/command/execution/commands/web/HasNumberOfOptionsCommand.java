package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.logging.ILog;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by RafaelT on 6/1/2016.
 */

/**
 * Asserts that a given select element has a certain number of options. Can optionally be passed an option group which will be searched
 * instead of the entire select.
 */
public class HasNumberOfOptionsCommand extends WebControlCommand {
    private int number;
    private String optgroup;

    /**
     * Initializes a new instance of the HasNumberOfOptionsCommand.
     *
     * @param log                The logger.
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     * @param numberofoptions    The number of options that the option group should have.
     * @param optgroup           The visible text of the option group.
     */
    public HasNumberOfOptionsCommand(ILog log, IBy selector, ICommandInitializer commandInitializer, int numberofoptions, String optgroup) {
        super(log, String.format(Locale.getDefault(), Resources.getString("HasNumberOfOptionsCommand_Info"), selector, numberofoptions), selector, commandInitializer);
        this.number = numberofoptions;
        this.optgroup = optgroup;
    }

    /**
     * Initializes a new instance of the HasNumberOfOptionsCommand.
     *
     * @param log                The logger.
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     * @param numberofoptions    The number of options the select should have.
     */
    public HasNumberOfOptionsCommand(ILog log, IBy selector, ICommandInitializer commandInitializer, int numberofoptions) {
        super(log, String.format(Locale.getDefault(), Resources.getString("BlurCommand_Info"), selector), selector, commandInitializer);
        this.number = numberofoptions;
        this.optgroup = null;
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver  The web driver.
     * @param control The web control.
     */
    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.HasNumberOfOptions(getGuid(), control, number, optgroup);
    }
}
