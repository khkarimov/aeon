package command_execution;

import common.BrowserType;
import common.logging.ILog;
import framework_abstraction.webdriver.IWebDriverAdapter;

/**
 * Provides access to data required for automation.
 */
public class AutomationInfo {
    private ICommandExecutionFacade CommandExecutionFacade;
    private IWebDriverAdapter webDriverAdapter;
    private ILog log;
    private BrowserType browserType;

    public AutomationInfo(IWebDriverAdapter webDriverAdapter, ILog log, BrowserType browserType) {
        this.webDriverAdapter = webDriverAdapter;
        this.log = log;
        this.browserType = browserType;
    }

    public AutomationInfo(ICommandExecutionFacade commandExecutionFacade, ILog log) {
        setCommandExecutionFacade(commandExecutionFacade);
        this.log = log;
        this.browserType = getBrowserType().Chrome;
    }

    /**
     * Gets the log.
     */
    public final ILog getLog() {
        return log;
    }

    public final IWebDriverAdapter getWebDriverAdapter() {
        return webDriverAdapter;
    }

    public final BrowserType getBrowserType() {
        return browserType;
    }

    public final ICommandExecutionFacade getCommandExecutionFacade() {
        return CommandExecutionFacade;
    }

    public final void setCommandExecutionFacade(ICommandExecutionFacade value) {
        CommandExecutionFacade = value;
    }
}
