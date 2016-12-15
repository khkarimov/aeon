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
 * Created by RafaelT on 6/29/2016.
 */

/**
 * Checks if the value of a given attribute of an element is like a given value.
 */
public class IsLikeCommand extends WebControlCommand {
    private String value;
    private String attribute;
    private ComparisonOption option;

    /**
     * Initializes a new instance of the IsLikeCommand. When comparing the case is ignored.
     *
     * @param log                The logger.
     * @param selector           The selector.
     * @param commandInitializer The web command initializer.
     * @param value              The value the attribute should have.
     * @param option             Whether the "INNERHTML" tag will be treated as the visible text or the raw HTML
     * @param attribute          The attribute to be compared.
     */
    public IsLikeCommand(ILog log, IBy selector, ICommandInitializer commandInitializer, String value, ComparisonOption option, String attribute) {
        super(log, String.format(Locale.getDefault(), Resources.getString("IsLikeCommand_Info"),attribute, value, selector), selector, commandInitializer);
        this.value = value;
        this.option = option;
        this.attribute = attribute;
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.IsLike(getGuid(), control, value, option, attribute);
    }
}
