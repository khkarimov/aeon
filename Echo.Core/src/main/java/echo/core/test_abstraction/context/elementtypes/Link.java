package echo.core.test_abstraction.context.elementtypes;

import echo.core.common.webobjects.interfaces.IBy;
import echo.core.test_abstraction.context.Element;

/**
 * Created by AdamC on 4/13/2016.
 */
public class Link extends Element {
    public Link(IBy selector) {
        super(selector);
    }

    public void Click()
    {
        throw new UnsupportedOperationException();
    }
}
