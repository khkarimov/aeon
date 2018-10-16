package aeon.platform;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.platform.services.SessionService;

import java.util.Properties;

public class SessionFactory {

    //private SessionService sessionService = new SessionService();
    private SessionService sessionService;
    private AutomationInfo automationInfo;
    private ICommandExecutionFacade commandExecutionFacade;

    public SessionFactory(SessionService sessionService) {
        this.sessionService = sessionService;
    }
//
//    public static void createSession(Properties settings) throws Exception {
//        automationInfo = sessionService.setUpAutomationInfo(settings);
//        commandExecutionFacade = sessionService.setUpCommandExecutionFacade(automationInfo);
//    }

    public ISession getSession(Properties settings) throws Exception {
        automationInfo = sessionService.setUpAutomationInfo(settings);
        commandExecutionFacade = sessionService.setUpCommandExecutionFacade(automationInfo);

        return new Session(automationInfo, commandExecutionFacade);
    }
}
