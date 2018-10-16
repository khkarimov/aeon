package aeon.platform;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import aeon.platform.services.SessionService;

import javax.inject.Inject;
import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;

public class SessionFactory {

    private SessionService sessionService;
    private AutomationInfo automationInfo;
    private ICommandExecutionFacade commandExecutionFacade;

    public SessionFactory(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public ISession getSession(Properties settings) throws Exception {
        automationInfo = sessionService.setUpAutomationInfo(settings);
        commandExecutionFacade = sessionService.setUpCommandExecutionFacade(automationInfo);

        return new Session(automationInfo, commandExecutionFacade);
    }
}
