package com.ultimatesoftware.aeon.core.command.execution.consumers;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.command.execution.consumers.interfaces.IDelegateRunner;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.exceptions.TimeoutExpiredException;
import com.ultimatesoftware.aeon.core.common.helpers.Sleep;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Class to handle the timeout delegate runner.
 */
public class TimeoutDelegateRunner extends DelegateRunner {

    private static Logger log = LoggerFactory.getLogger(TimeoutDelegateRunner.class);
    private IDriver driver;
    private Duration timeout;
    private AutomationInfo automationInfo;

    /**
     * Constructor for {@link TimeoutDelegateRunner} class.
     *
     * @param successor      the delegate runner.
     * @param driver         the web driver.
     * @param timeout        the duration time.
     * @param automationInfo The automation info.
     */
    public TimeoutDelegateRunner(IDelegateRunner successor, IDriver driver, Duration timeout, AutomationInfo automationInfo) {
        super(successor);
        this.driver = driver;
        this.timeout = timeout;
        this.automationInfo = automationInfo;
    }

    @Override
    public void execute(Consumer<IDriver> commandDelegate) {
        executeDelegate(() -> successor.execute(commandDelegate));
    }

    @Override
    public Object execute(Function<IDriver, Object> commandDelegate) {
        return executeDelegateWithReturn(() -> successor.execute(commandDelegate));
    }

    private void executeDelegate(Runnable commandDelegate) {
        executeDelegateWithReturn(() -> {
            commandDelegate.run();
            return null;
        });
    }

    private Object executeDelegateWithReturn(Supplier<Object> commandDelegateWrapper) {
        RuntimeException lastCaughtException = null;
        int tries = 0;

        LocalDateTime end = LocalDateTime.now().plus(timeout);
        while (LocalDateTime.now().isBefore(end)) {
            try {
                tries++;
                Object returnValue = commandDelegateWrapper.get();
                log.debug(Resources.getString("TimWtr_Success_Debug"), tries);
                return returnValue;
            } catch (RuntimeException e) {
                lastCaughtException = e;

                log.debug(Resources.getString("TimWtr_Exception_Debug"), tries,
                        lastCaughtException.getClass().getSimpleName(), lastCaughtException.getMessage());
            }

            // Wait before retrying. Excessive attempts may cause WebDriver's client to lose connection with the server.
            Sleep.getInstance().waitInternal();
        }

        RuntimeException ex = new TimeoutExpiredException(
                Resources.getString("TimWtr_TimeoutExpired_DefaultMessage"), timeout);

        // If we have a last caught exception use that one
        // as the main exception for logging
        if (lastCaughtException != null) {
            lastCaughtException.addSuppressed(ex);
            ex = lastCaughtException;
        }

        Image screenshot = null;
        try {
            screenshot = driver.getScreenshot();

            automationInfo.screenshotTaken(screenshot);
        } catch (RuntimeException e) {
            log.warn(e.getMessage(), e);
        }

        if (screenshot == null) {
            log.error(ex.getMessage(), lastCaughtException);
        } else {
            log.error(ex.getMessage(), screenshot, lastCaughtException);
        }
        if (automationInfo.getConfiguration().getBoolean(Configuration.Keys.REPORTING, true)) {
            automationInfo.testFailed(ex.getMessage(), ex);
        }
        throw ex;
    }
}
