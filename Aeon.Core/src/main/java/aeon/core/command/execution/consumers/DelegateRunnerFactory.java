package aeon.core.command.execution.consumers;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.consumers.interfaces.IDelegateRunner;
import aeon.core.command.execution.consumers.interfaces.IDelegateRunnerFactory;
import aeon.core.framework.abstraction.drivers.IDriver;

import java.time.Duration;

/**
 * The Web delegate runner factory.
 */
public class DelegateRunnerFactory implements IDelegateRunnerFactory {

    private Duration defaultTimeout;
    private boolean promptUserForContinueOnExceptionDecision;
    private Duration throttleFactor;

    /**
     * Initializes a new instance of the {@link DelegateRunnerFactory} class.
     *
     * @param defaultTimeout                           The timeout to wait for.
     * @param promptUserForContinueOnExceptionDecision Instructs the framework to show a popup when an unhandled exception occurs prompting the users to decide if the test should attempt to continue.
     * @param throttleFactor                           TimeSpan used to slow down test execution. This value will be used as a pause between test actions.
     */
    public DelegateRunnerFactory(Duration defaultTimeout, boolean promptUserForContinueOnExceptionDecision, Duration throttleFactor) {
        this.defaultTimeout = defaultTimeout;
        this.promptUserForContinueOnExceptionDecision = promptUserForContinueOnExceptionDecision;
        this.throttleFactor = throttleFactor;
    }

    /**
     * Initializes a new instance of the {@link DelegateRunnerFactory} class.
     *
     * @param throttleFactor The throttle factor.
     * @param defaultTimeout The default timeout.
     */
    public DelegateRunnerFactory(Duration throttleFactor, Duration defaultTimeout) {
        this.throttleFactor = throttleFactor;
        this.defaultTimeout = defaultTimeout;
    }

    /**
     * Creates an instance of the {@link IDelegateRunner} class which is used to run delegates.
     *
     * @param automationInfo The automation Info.
     * @return a new instance of throttled delegate runner.
     */
    public final IDelegateRunner createInstance(AutomationInfo automationInfo) {
        // TODO(DionnyS): JAVA_CONVERSION Use an IoC container to resolve the factory.
        IDriver driver = automationInfo.getDriver();

        CommandDelegateRunner commandDelegateRunner = new CommandDelegateRunner(driver);
        TimeoutDelegateRunner timeoutDelegateRunner = new TimeoutDelegateRunner(commandDelegateRunner, driver, defaultTimeout, automationInfo);
        ExceptionHandlingDelegateRunner exceptionHandlingDelegateRunner = new ExceptionHandlingDelegateRunner(timeoutDelegateRunner, new SeleniumExceptionHandlerFactory(promptUserForContinueOnExceptionDecision));
        return new ThrottledDelegateRunner(exceptionHandlingDelegateRunner, throttleFactor);
    }
}
