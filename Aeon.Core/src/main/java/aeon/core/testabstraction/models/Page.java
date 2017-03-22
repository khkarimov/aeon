package aeon.core.testabstraction.models;

import aeon.core.common.web.interfaces.IBy;
import aeon.core.testabstraction.elements.Element;

/**
 * Created By DionnyS on 4/12/2016.
 */
public class Page {
    public Element createElement(IBy selector) {
        return new Element(selector);
    }
}
