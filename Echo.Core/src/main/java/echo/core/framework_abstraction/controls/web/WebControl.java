package echo.core.framework_abstraction.controls.web;

import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.Control;

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
