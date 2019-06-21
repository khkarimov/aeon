package com.ultimatesoftware.aeon.core.testabstraction.models;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;

/**
 * Parent class for all web pages.
 */
public abstract class WebPage extends Page {

    protected final IByWeb[] switchMechanism;

    /**
     * Initializes a new instance of the {@link WebPage} class
     * with an optional switch mechanism.
     *
     * @param automationInfo  The automation info object.
     * @param switchMechanism The switch mechanism for the web page.
     */
    public WebPage(AutomationInfo automationInfo, IByWeb... switchMechanism) {
        super(automationInfo);
        this.switchMechanism = switchMechanism;
    }
}
