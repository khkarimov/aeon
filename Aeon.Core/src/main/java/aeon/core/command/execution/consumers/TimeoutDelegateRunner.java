package aeon.core.command.execution.consumers;

import aeon.core.command.execution.consumers.interfaces.IDelegateRunner;
import aeon.core.common.Resources;
import aeon.core.common.exceptions.TimeoutExpiredException;
import aeon.core.common.helpers.IClock;
import aeon.core.common.helpers.Sleep;
import aeon.core.framework.abstraction.drivers.IDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TimeoutDelegateRunner extends DelegateRunner {
    private IDriver driver;
    private IClock clock;
    private Duration timeout;
    private static Logger log = LogManager.getLogger(TimeoutDelegateRunner.class);

    public TimeoutDelegateRunner(IDelegateRunner successor, IDriver driver, IClock clock, Duration timeout) {
        super(successor);
        this.driver = driver;
        this.clock = clock;
        this.timeout = timeout;
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

        DateTime end = clock.getUtcNow().withDurationAdded(timeout.getMillis(), 1);
        while (clock.getUtcNow().isBefore(end.toInstant())) {
            try {
                tries++;
                Object returnValue = commandDelegateWrapper.get();
                log.debug(String.format(Resources.getString("TimWtr_Success_Debug"), tries));
                return returnValue;
            } catch (OutOfMemoryError e) {
                log.error(Resources.getString("TimWtr_OutOfMemoryException_Error"));
                log.error(Resources.getString("StackTraceMessage") + e.getMessage() + e.getStackTrace());
                throw e;
            } catch (RuntimeException e) {
                lastCaughtException = e;
                log.debug(String.format(Resources.getString("TimWtr_Exception_Debug"), tries,
                        lastCaughtException.getClass().getSimpleName(), lastCaughtException.getMessage()));
            }

            // wait before retrying. Excessive attempts may cause WebDriver's client to lose connection with the server.
            Sleep.waitInternal();
        }

        TimeoutExpiredException ex = new TimeoutExpiredException(
                Resources.getString("TimWtr_TimeoutExpired_DefaultMessage"), timeout, lastCaughtException);

        // TODO(DionnyS): JAVA_CONVERSION Get running processes.
        //var processList = ProcessCollector.GetProcesses();

        Image screenshot = null;
        try {
            screenshot = driver.getScreenshot();
        } catch (OutOfMemoryError e) {
            throw e;
        } catch (RuntimeException e2) {
        }

        if (screenshot == null) {
            log.error(ex.getMessage(), /* TODO(DionnyS): JAVA_CONVERSION processList */ new ArrayList<>());
            log.error(Resources.getString("StackTraceMessage"), lastCaughtException);
        } else {
            log.error(ex.getMessage(), screenshot, /* TODO(DionnyS): JAVA_CONVERSION processList */ new ArrayList<>());
            log.error(Resources.getString("StackTraceMessage"), lastCaughtException);
        }

        String pageSource = null;
        try {
            pageSource = driver.getSource();
        } catch (OutOfMemoryError e3) {
            throw e3;
        } catch (RuntimeException e4) {
        }

        if (pageSource != null) {
            log.trace(ex.getMessage(), pageSource);
        }

        throw ex;
    }
}
