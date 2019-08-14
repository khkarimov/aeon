package com.ultimatesoftware.aeon.core.testabstraction.models;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;

/**
 * Base page model.
 */
public abstract class Page {

    protected AutomationInfo automationInfo;

    /**
     * Constructor for abstract class {@link Page}.
     *
     * @param automationInfo The automation info object.
     */
    public Page(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
    }
}
