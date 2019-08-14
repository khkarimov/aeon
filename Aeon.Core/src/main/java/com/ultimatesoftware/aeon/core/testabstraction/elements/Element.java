package com.ultimatesoftware.aeon.core.testabstraction.elements;

import com.ultimatesoftware.aeon.core.common.interfaces.IBy;

/**
 * The class for elements modeling.
 */
public class Element {

    /**
     * Creates a new instance of {@link Element}.
     *
     * @param selector IBy selector that will identify the element.
     */
    public Element(IBy selector) {
        if (selector == null) {
            throw new IllegalArgumentException("An element has to have a valid selector");
        }
    }
}

