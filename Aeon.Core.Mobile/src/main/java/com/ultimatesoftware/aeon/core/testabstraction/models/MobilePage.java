package com.ultimatesoftware.aeon.core.testabstraction.models;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.command.execution.commands.mobile.SwitchToWebViewCommand;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;

/**
 * Parent class for all mobile pages which are potentially located only in a specific webview.
 */
public abstract class MobilePage extends WebPage {

    private IByWeb webViewSelector = null;

    /**
     * Initializes a new instance of the {@link MobilePage} class
     * with an optional switch mechanism.
     *
     * @param automationInfo  The automation info object.
     * @param switchMechanism The switch mechanism for the web page.
     */
    public MobilePage(AutomationInfo automationInfo, IByWeb... switchMechanism) {
        super(automationInfo, switchMechanism);
    }

    /**
     * Initializes a new instance of the {@link MobilePage} class
     * with an optional switch mechanism.
     *
     * @param webViewSelector The selector to use in order to identify the containing webview.
     * @param automationInfo  The automation info object.
     * @param switchMechanism The switch mechanism for the web page.
     */
    public MobilePage(IByWeb webViewSelector, AutomationInfo automationInfo, IByWeb... switchMechanism) {
        super(automationInfo, switchMechanism);
        this.webViewSelector = webViewSelector;
    }

    /**
     * Switches to the web view that contains a specific element.
     */
    public void switchToWebView() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new SwitchToWebViewCommand(webViewSelector));
    }

    /**
     * Switches to the main web view.
     */
    public void switchToMainWebView() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new SwitchToWebViewCommand(null));
    }
}
