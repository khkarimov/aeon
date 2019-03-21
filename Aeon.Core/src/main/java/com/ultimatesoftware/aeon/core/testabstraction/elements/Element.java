package com.ultimatesoftware.aeon.core.testabstraction.elements;

import com.ultimatesoftware.aeon.core.common.interfaces.IBy;

/**
 * The class for elements modeling.
 */
public class Element {

    private IBy selector;

    /**
     * Creates a new instance of {@link Element}.
     *
     * @param selector IBy selector that will identify the element.
     */
    public Element(IBy selector) {
        this.selector = selector;
    }
}

