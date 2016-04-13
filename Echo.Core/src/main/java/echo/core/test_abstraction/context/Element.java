package echo.core.test_abstraction.context;

import echo.core.common.webobjects.interfaces.IBy;

/**
 * Created by DionnyS on 4/12/2016.
 */
public class Element extends ElementAssertions {
    private IBy selector;

    public Element(IBy selector) {
        this.selector = selector;
    }
}

