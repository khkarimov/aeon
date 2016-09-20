package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.ComparisonOption;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by RafaelT on 6/28/2016.
 */

/**
 * Asserts that an elements children that match a given selector do not have certain values.
 */
public class DoesNotHaveCommand extends WebControlCommand {
    private String[] messages;
    private String childSelector;
    private String attribute;
    private ComparisonOption option;

    /**
     * Initializes a new instance of the DoesNotHaveCommand.
     *
     * @param log                The logger.
     * @param selector           The selector for the element.
     * @param commandInitializer The Command initializer.
     * @param messages           The strings to be matched.
     * @param childSelector      The selector that the children are matched to.
     * @param option             Whether or not the "INNERHTML" tag will refer to the literal html in the tag or the visible text.
     * @param attribute          The attribute of the children to compare with messages.
     */
    public DoesNotHaveCommand(ILog log, IBy selector, ICommandInitializer commandInitializer, String[] messages, String childSelector, ComparisonOption option, String attribute) {
        super(log, String.format(Locale.getDefault(), Resources.getString("DoesNotHaveCommand_Info"), selector), selector, commandInitializer);
        this.messages = messages;
        this.childSelector = childSelector;
        this.attribute = attribute;
        this.option = option;
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.DoesNotHave(getGuid(), control, messages, childSelector, option, attribute);
    }
}
