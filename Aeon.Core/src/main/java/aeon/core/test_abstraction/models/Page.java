package aeon.core.test_abstraction.models;

import aeon.core.common.web.interfaces.IBy;
import aeon.core.test_abstraction.elements.Element;

/**
 * Created by DionnyS on 4/12/2016.
 */
public class Page {
    public Element createElement(IBy selector) {
        return new Element(selector);
    }
}
