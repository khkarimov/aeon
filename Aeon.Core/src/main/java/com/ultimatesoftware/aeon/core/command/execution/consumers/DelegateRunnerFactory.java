package com.ultimatesoftware.aeon.core.command.execution.consumers;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.command.execution.consumers.interfaces.IDelegateRunner;
import com.ultimatesoftware.aeon.core.command.execution.consumers.interfaces.IDelegateRunnerFactory;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;

import java.time.Duration;

/**
 * The Web delegate runner factory.
 */
public class DelegateRunnerFactory implements IDelegateRunnerFactory {

    private Duration defaultTimeout;
    private Duration throttleFactor;

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
        IDriver driver = automationInfo.getDriver();

        CommandDelegateRunner commandDelegateRunner = new CommandDelegateRunner(driver);
        TimeoutDelegateRunner timeoutDelegateRunner = new TimeoutDelegateRunner(commandDelegateRunner, driver, defaultTimeout, automationInfo);
        ExceptionHandlingDelegateRunner exceptionHandlingDelegateRunner = new ExceptionHandlingDelegateRunner(timeoutDelegateRunner, new ExceptionHandlerFactory());

        return new ThrottledDelegateRunner(exceptionHandlingDelegateRunner, throttleFactor);
    }
}
