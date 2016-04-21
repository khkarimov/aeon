package echo.core.test_abstraction.elements;

import echo.core.common.web.interfaces.IBy;

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
