package aeon.core.framework.abstraction.controls.web;

import aeon.core.framework.abstraction.controls.Control;
import aeon.core.common.web.interfaces.IBy;

/**
 * Created by DionnyS on 4/20/2016.
 */
public class WebControl extends Control {
    private IBy selector;

    public IBy getSelector() {
        return selector;
    }

    public void setSelector(IBy selector) {
        this.selector = selector;
    }
}
