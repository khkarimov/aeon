package echo.core.test_abstraction.context;

import echo.core.common.web.interfaces.IBy;

/**
 * Created by DionnyS on 4/12/2016.
 */
public class Page {
    public Element createElement(IBy selector) {
        return new Element(selector);
    }
}
