package aeon.core.framework.abstraction.controls.web;

import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.Control;

/**
 * Created by DionnyS on 4/20/2016.
 */
public class WebControl extends Control {

    private IByWeb selector;

    /**
     * Gets the Web Control's selector.
     * @return the web control's selector.
     */
    public IByWeb getSelector() {
        return selector;
    }

    /**
     * Sets the Web control's selector.
     * @param selector the selector to be set.
     */
    public void setSelector(IByWeb selector) {
        this.selector = selector;
    }
}
