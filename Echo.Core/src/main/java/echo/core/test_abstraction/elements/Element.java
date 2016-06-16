package echo.core.test_abstraction.elements;

import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.web.WebControlFinder;
import echo.core.command_execution.commands.web.WebSelectorFinder;
import echo.core.common.web.interfaces.IBy;

import java.util.ArrayList;

/**
 * Created by DionnyS on 4/12/2016.
 */
public class Element extends ElementAssertions {
    private IBy selector;
    public Element(IBy selector) {
        this.selector = selector;
    }

    /**
     * Every, if not most, subclasses of element require a WebCommandInitializer. This facilities the instantiation
     * of a WebCommandInitializer.
     * @return An instance of the WebCommandInitializer
     */
    protected WebCommandInitializer getWebCommandInitializer(){
        return new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>());
    }
}

