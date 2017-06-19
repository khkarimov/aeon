package aeon.core.testabstraction.models;

import aeon.core.common.web.interfaces.IBy;
import aeon.core.testabstraction.elements.Element;

/**
 * Base page model for pages.
 * */
public class Page {

    /**
     * Creates a new {@link Element} with the indicated selector.
     *
     * @param selector The {@link IBy} selector for the new element.
     * @return the new {@link Element}
     */
    public Element createElement(IBy selector) {
        return new Element(selector);
    }
}
