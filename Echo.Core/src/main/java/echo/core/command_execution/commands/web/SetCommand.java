package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.Resources;
import echo.core.common.exceptions.Select2Exception;
import echo.core.common.logging.ILog;
import echo.core.common.web.WebSelectOption;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;
import org.openqa.selenium.Keys;

/**
 * Sets an element to a certain value.
 */
public class SetCommand extends WebControlCommand {

    private WebSelectOption selectOption;
    private String value;

    /**
     * Initializes a new instance of the {@link SetCommand} class.
     *
     * @param log         The log.
     * @param selector
     * @param initializer
     */
    public SetCommand(ILog log, IBy selector, ICommandInitializer initializer, WebSelectOption selectOption, String value) {
        super(log, String.format(Resources.getString("SetCommand_Info"), value, selector), selector, initializer);
        this.selectOption = selectOption;
        this.value = value;
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        String tag = driver.GetElementTagName(getGuid(), control).toUpperCase();

        switch (tag) {
            case "SELECT":
                switch (selectOption) {
                    case Value:
                        // When changing by value, Selenium updates the Select2 element properly.
                        driver.ChooseSelectElementByValue(getGuid(), control, value);
                        break;
                    case Text:
                        // Handle Select2 code.
                        if (!HandleSelect2(driver, control)) {
                            // If select2 couldn't be run, use Selenium.
                            driver.ChooseSelectElementByText(getGuid(), control, value);
                        }

                        break;
                    default:
                        throw new UnsupportedOperationException();
                }

                break;
            case "TEXTAREA":
                driver.ClickElement(getGuid(), control);
                driver.ClearElement(getGuid(), control);
                driver.SendKeysToElement(getGuid(), control, value);
                break;
            default:
                String currentValue = driver.GetElementAttribute(getGuid(), control, "value");
                if (currentValue != null) {
                    String backspaces = "";
                    for (int i = 0; i < currentValue.length(); i++) {
                        backspaces += Keys.BACK_SPACE;
                    }
                    driver.SendKeysToElement(getGuid(), control, Keys.END + backspaces);
                }

                driver.SendKeysToElement(getGuid(), control, value);
                break;
        }
    }

    private boolean HandleSelect2(IWebDriver driver, WebControl webControl) {
        // See http://ivaynberg.github.io/select2/#programmatic for the Select2 API (5/16/2014)
        if (selectOption != WebSelectOption.Text) {
            // If we are setting by value, Selenium can handle it.
            return false;
        }

        String scriptToDetermineIfSelect2CanBeInvoked = String.format("return %1$s.hasClass('select2-offscreen')", webControl.getSelector().ToJQuery());

        boolean select2Defined = (boolean) driver.ExecuteScript(getGuid(), scriptToDetermineIfSelect2CanBeInvoked);
        if (!select2Defined) {
            // If select2 is not defined, then we cannot run the select2 functions.
            return false;
        }

            /*
             * This script returns the option ID if there is exactly one element with an exact match between the element's text and the variable 'value'.
             * Otherwise, it returns false.
             */
        String scriptToDetermineIfSelect2HasExactlyThatOption = String.format("var theId;" + "var list = %1$s.select2().find(':contains(%2$s)').filter( function() { if($(this).text() === '%2$s') { theId = $(this).val(); return true; } return false; } );" + "if(list.length === 1) { return theId; }; return false;", webControl.getSelector().ToJQuery(), value);

        Object select2SpecifiedOption = driver.ExecuteScript(getGuid(), scriptToDetermineIfSelect2HasExactlyThatOption);
        if (select2SpecifiedOption instanceof Boolean && !(Boolean) select2SpecifiedOption) {
            throw new Select2Exception("Could not find a unique option with text '" + value + "'.");
        }

        String valueString;
        if (select2SpecifiedOption != null) {
            valueString = String.format("{text:'%1$s', id:'%2$s'}", value, select2SpecifiedOption);
        } else {
            valueString = String.format("{text:'%1$s'}", value);
        }

        String script = String.format("%1$s.select2('data', %2$s)", webControl.getSelector().ToJQuery(), valueString);
        driver.ExecuteScript(getGuid(), script);

        return true;
    }
}
