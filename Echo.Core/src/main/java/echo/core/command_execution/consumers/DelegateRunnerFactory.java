package echo.core.command_execution.consumers;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.consumers.interfaces.IDelegateRunner;
import echo.core.command_execution.consumers.interfaces.IDelegateRunnerFactory;
import echo.core.common.web.BrowserType;
import echo.core.common.helpers.Clock;
import echo.core.common.helpers.IClock;
import echo.core.framework_abstraction.drivers.IDriver;
import org.joda.time.Duration;

import java.util.UUID;

/**
 * The Web delegate runner factory.
 */
public class DelegateRunnerFactory implements IDelegateRunnerFactory {
    private IClock clock;
    private Duration defaultTimeout;
    private boolean promptUserForContinueOnExceptionDecision;
    private Duration throttleFactor;

    /**
     * Initializes a new instance of the <see cref="DelegateRunnerFactory"/> class.
     *
     * @param clock                                    The clock.
     * @param defaultTimeout                           The timeout to wait for.
     * @param promptUserForContinueOnExceptionDecision Instructs the framework to show a popup when an unhandled exception occurs prompting the users to decide if the test should attempt to continue.
     * @param throttleFactor                           TimeSpan used to slow down test execution. This value will be used as a pause between test actions.
     */
    public DelegateRunnerFactory(IClock clock, Duration defaultTimeout, boolean promptUserForContinueOnExceptionDecision, Duration throttleFactor) {
        this.clock = clock;
        this.defaultTimeout = defaultTimeout;
        this.promptUserForContinueOnExceptionDecision = promptUserForContinueOnExceptionDecision;
        this.throttleFactor = throttleFactor;
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
     */
    public final IDelegateRunner CreateInstance(UUID guid, AutomationInfo automationInfo) {
        // TODO: JAVA_CONVERSION Use an IoC container to resolve the factory.
        IDriver driver = automationInfo.getDriver();

        CommandDelegateRunner commandDelegateRunner = new CommandDelegateRunner(driver, automationInfo.getLog());
        TimeoutDelegateRunner timeoutDelegateRunner = new TimeoutDelegateRunner(guid, commandDelegateRunner, driver, clock, defaultTimeout);
        ExceptionHandlingDelegateRunner exceptionHandlingDelegateRunner = new ExceptionHandlingDelegateRunner(guid, timeoutDelegateRunner, new SeleniumExceptionHandlerFactory(promptUserForContinueOnExceptionDecision));
        return new ThrottledDelegateRunner(guid, exceptionHandlingDelegateRunner, throttleFactor);
    }
}
