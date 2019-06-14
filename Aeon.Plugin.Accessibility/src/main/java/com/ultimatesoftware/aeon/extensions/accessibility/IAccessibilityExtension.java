package com.ultimatesoftware.aeon.extensions.accessibility;

import org.pf4j.ExtensionPoint;

/**
 * The interface for Accessibility Extensions.
 */
public interface IAccessibilityExtension extends ExtensionPoint {

    /**
     * Runs accessibility tests.
     *
     * @param pageName The page name.
     */
    void runAccessibilityTests(String pageName);
}
