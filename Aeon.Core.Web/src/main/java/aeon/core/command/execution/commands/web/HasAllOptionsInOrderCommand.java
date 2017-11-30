package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.CompareType;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Asserts that a select element has all of its options in lexicographic order. Can either specify the options are ascending or descending by alphanumeric order, comparing either their value or
 * text.
 */
public class HasAllOptionsInOrderCommand extends WebControlCommand {

    private CompareType compare;
    private String optGroup;

    /**
     * Initializes a new instance of the {@link HasAllOptionsInOrderCommand} class.
     *
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     * @param compare            The way that all the options in the select element will be compared.
     * @param optGroup           the optional option group that would be searched in isolation instead of the entire select.
     */
    public HasAllOptionsInOrderCommand(IByWeb selector, ICommandInitializer commandInitializer, CompareType compare, String optGroup) {
        super(String.format(Locale.getDefault(), Resources.getString("HasAllOptionsInOrderCommand_Info"), selector), selector, commandInitializer);
        this.compare = compare;
        this.optGroup = optGroup;
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver  The web driver.
     * @param control Thw web control.
     */
    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.hasAllOptionsInOrder(control, compare, optGroup);
    }
}
