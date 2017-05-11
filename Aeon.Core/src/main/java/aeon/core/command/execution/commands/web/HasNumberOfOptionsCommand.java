package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Asserts that a given select element has a certain number of options. Can optionally be passed an option group which will be searched
 * instead of the entire select.
 */
public class HasNumberOfOptionsCommand extends WebControlCommand {

    private int number;
    private String optGroup;

    /**
     * Initializes a new instance of the {@link HasNumberOfOptionsCommand} class.
     *
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     * @param numberOfOptions    The number of options that the option group should have.
     * @param optGroup           The visible text of the option group.
     */
    public HasNumberOfOptionsCommand(IBy selector, ICommandInitializer commandInitializer, int numberOfOptions, String optGroup) {
        super(String.format(Locale.getDefault(), Resources.getString("HasNumberOfOptionsCommand_Info"), selector, numberOfOptions), selector, commandInitializer);
        this.number = numberOfOptions;
        this.optGroup = optGroup;
    }

    /**
     * Initializes a new instance of the HasNumberOfOptionsCommand.
     *
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     * @param numberOfOptions    The number of options the select should have.
     */
    public HasNumberOfOptionsCommand(IBy selector, ICommandInitializer commandInitializer, int numberOfOptions) {
        super(String.format(Locale.getDefault(), Resources.getString("BlurCommand_Info"), selector), selector, commandInitializer);
        this.number = numberOfOptions;
        this.optGroup = null;
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver  The web driver.
     * @param control The web control.
     */
    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.hasNumberOfOptions(control, number, optGroup);
    }
}
