package aeon.core.testabstraction.elements.mobile;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.WebElement;

/**
 * Parent class for mobile elements.
 */
public class NativeAppElement extends WebElement {

    /**
     * Constructor.
     *
     * @param info The automation info object to use.
     * @param selector The selector that identifies this element.
     */
    public NativeAppElement(AutomationInfo info, IByWeb selector) {
        super(info, selector);
    }

    /**
     * Constructor.
     *
     * @param selector The selector that identifies this element.
     */
    public NativeAppElement(IByWeb selector) {
        super(selector);
    }

    /**
     * Constructor.
     *
     * @param info The automation info object to use.
     * @param selector The selector that identifies this element.
     * @param switchMechanism The switchMechanism to use to find the element.
     */
    public NativeAppElement(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {
        super(info, selector, switchMechanism);
    }
}
