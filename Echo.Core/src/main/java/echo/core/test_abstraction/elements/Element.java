package echo.core.test_abstraction.elements;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.web.*;
import echo.core.common.web.interfaces.IBy;

import java.util.ArrayList;

/**
 * Created by DionnyS on 4/12/2016.
 */
public class Element extends ElementAssertions {
    private IBy selector;
    private AutomationInfo info;

    public Element(IBy selector) {
        this(selector, null);
        this.selector = selector;
    }

    public Element(IBy selector, AutomationInfo info) {
        this.selector = selector;
        this.info = info;
    }

    public void MouseOut() {
        info.getCommandExecutionFacade().Execute(info, new MouseOutCommand(
                info.getLog(),
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>())));
    }

    public void MouseOver() {
        info.getCommandExecutionFacade().Execute(info, new MouseOverCommand(
                info.getLog(),
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>())));
    }

    public void SetDivValueByJavaScript(String value) {
        info.getCommandExecutionFacade().Execute(info, new SetDivValueByJavaScriptCommand(
                info.getLog(),
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()),
                value));
    }

    public void SetBodyValueByJavaScript(String value) {
        info.getCommandExecutionFacade().Execute(info, new SetBodyValueByJavaScriptCommand(
                info.getLog(),
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()),
                value));
    }

    public void SetValueByJavaScript(String value) {
        info.getCommandExecutionFacade().Execute(info, new SetValueByJavaScriptCommand(
                info.getLog(),
                selector,
                new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()),
                value));
    }
}

