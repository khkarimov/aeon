package aeon.core.framework.abstraction.controls.web;

import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.Control;

/**
 * Model class for web controls.
 */
public class WebControl implements Control {

    private IByWeb selector;

    /**
     * Gets the Web Control's selector.
     *
     * @return the web control's selector.
     */
    public IByWeb getSelector() {
        return selector;
    }

    /**
     * Sets the Web control's selector.
     *
     * @param selector the selector to be set.
     */
    public void setSelector(IByWeb selector) {
        this.selector = selector;
    }
}
