package com.ultimatesoftware.aeon.core.common.web.interfaces;

/**
 * Interface for ByXPath selectors.
 */
public interface IByXPath extends IByWeb {
    /**
     * Appends a selector to the current instance in order
     * to be able to select child elements.
     *
     * @param selector The selector of the child elements
     * @return The new IByXPath with the appended selector
     */
    IByXPath find(IByXPath selector);
}
