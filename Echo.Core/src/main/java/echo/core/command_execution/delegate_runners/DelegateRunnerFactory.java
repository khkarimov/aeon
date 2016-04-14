package echo.core.command_execution.delegate_runners;

import echo.core.command_execution.delegate_runners.interfaces.IDelegateRunner;
import echo.core.command_execution.delegate_runners.interfaces.IDelegateRunnerFactory;
import echo.core.common.BrowserType;
import echo.core.common.helpers.Clock;
import echo.core.common.helpers.IClock;
import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.FrameworkAbstractionFacadeFactory;
import echo.core.framework_abstraction.IFrameworkAbstractionFacade;
import echo.core.framework_interaction.FrameworkAdapterFactory;
import echo.core.framework_interaction.selenium.SeleniumKeyboardMapper;
import echo.core.framework_interaction.selenium.SeleniumSelectElementFactory;
import org.joda.time.Duration;

/**
 * The Web delegate runner factory.
 */
public class DelegateRunnerFactory implements IDelegateRunnerFactory {
    private IClock clock;
    private Duration defaultTimeout;
    private boolean promptUserForContinueOnExceptionDecision;
    private Duration throttleFactor;
    private Duration throttleFactorForInternetExplorer;

    /**
     * Initializes a new instance of the <see cref="DelegateRunnerFactory"/> class.
     *
     * @param clock                                    The clock.
     * @param defaultTimeout                           The timeout to wait for.
     * @param promptUserForContinueOnExceptionDecision Instructs the framework to show a popup when an unhandled exception occurs prompting the users to decide if the test should attempt to continue.
     * @param throttleFactor                           TimeSpan used to slow down test execution. This value will be used as a pause between test actions.
     * @param throttleFactorForInternetExplorer        The throttle factor for Internet Explorer.
     */
    public DelegateRunnerFactory(IClock clock, Duration defaultTimeout, boolean promptUserForContinueOnExceptionDecision, Duration throttleFactor, Duration throttleFactorForInternetExplorer) {
        this.clock = clock;
        this.defaultTimeout = defaultTimeout;
        this.promptUserForContinueOnExceptionDecision = promptUserForContinueOnExceptionDecision;
        this.throttleFactor = throttleFactor;
        this.throttleFactorForInternetExplorer = throttleFactorForInternetExplorer;
    }

    /**
     * Initializes a new instance of the <see cref="DelegateRunnerFactory"/> class for bacho.
     *
     * @param throttleFactor The throttle factor.
     * @param defaultTimeout The default timeout.
     */
    public DelegateRunnerFactory(Duration throttleFactor, Duration defaultTimeout) {
        this.clock = new Clock();
        this.throttleFactor = throttleFactor;
        this.defaultTimeout = defaultTimeout;
    }

    /**
     * Creates an instance of the <see cref="IDelegateRunner"/> class which is used to run delegates.
     *
     * @param parameterObject Framework Parameter object.
     * @return A new <see cref="IDelegateRunner"/> object.
     */
    public final IDelegateRunner CreateInstance(ParameterObject parameterObject) {
        // TODO: JAVA_CONVERSION Use an IoC container to resolve the factory.
        int mouseDragSpeed = parameterObject.getAutomationInfo().getParameters().getInt("mouseDragSpeed");

        IFrameworkAbstractionFacade frameworkAbstractionFacade =
                new FrameworkAbstractionFacadeFactory(
                        new SeleniumKeyboardMapper(),
                        new SeleniumSelectElementFactory(),
                        new FrameworkAdapterFactory(mouseDragSpeed, Duration.standardSeconds(30)))
                        .CreateInstance(parameterObject);

        CommandDelegateRunner commandDelegateRunner = new CommandDelegateRunner(frameworkAbstractionFacade, parameterObject.getAutomationInfo().getLog());
        TimeoutDelegateRunner timeoutDelegateRunner = new TimeoutDelegateRunner(parameterObject.getGuid(), commandDelegateRunner, frameworkAbstractionFacade, clock, defaultTimeout);
        ExceptionHandlingDelegateRunner exceptionHandlingDelegateRunner = new ExceptionHandlingDelegateRunner(parameterObject.getGuid(), timeoutDelegateRunner, new SeleniumExceptionHandlerFactory(promptUserForContinueOnExceptionDecision));
        return new ThrottledDelegateRunner(parameterObject.getGuid(), exceptionHandlingDelegateRunner, parameterObject.getAutomationInfo().getParameters().getParameter(BrowserType.class, "browserType") == BrowserType.InternetExplorer ? throttleFactorForInternetExplorer : throttleFactor);
    }
}
