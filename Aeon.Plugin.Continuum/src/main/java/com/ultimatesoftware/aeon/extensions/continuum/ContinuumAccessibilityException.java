package com.ultimatesoftware.aeon.extensions.continuum;

import com.levelaccess.continuum.Continuum;

/**
 * Exception thrown if accessibility issues are encountered.
 */
public class ContinuumAccessibilityException extends RuntimeException {

    /**
     * Constructor for generating a message with accessibility issues.
     *
     * @param continuum The {@link Continuum} object with the accessibility issues.
     */
    public ContinuumAccessibilityException(Continuum continuum) {
        super(continuum.getAccessibilityConcernsPrettyPrinted());
    }
}
