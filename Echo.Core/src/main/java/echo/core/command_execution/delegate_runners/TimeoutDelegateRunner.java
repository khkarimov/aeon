package echo.core.command_execution.delegate_runners;

import com.sun.prism.Image;
import echo.core.command_execution.delegate_runners.interfaces.IDelegateRunner;
import echo.core.common.Resources;
import echo.core.common.exceptions.TimeoutExpiredException;
import echo.core.common.helpers.IClock;
import echo.core.common.helpers.Sleep;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TimeoutDelegateRunner extends DelegateRunner {
    private IFrameworkAbstractionFacade frameworkAbstractionFacade;
    private IClock clock;
    private Duration timeout;

    public TimeoutDelegateRunner(UUID guid, IDelegateRunner successor, IFrameworkAbstractionFacade frameworkAbstractionFacade, IClock clock, Duration timeout) {
        super(guid, successor);
        this.frameworkAbstractionFacade = frameworkAbstractionFacade;
        this.clock = clock;
        this.timeout = timeout;
    }

    @Override
    public void Execute(Consumer<IFrameworkAbstractionFacade> commandDelegate) {
        ExecuteDelegate(() -> successor.Execute(commandDelegate));
    }

    @Override
    public Object Execute(Function<IFrameworkAbstractionFacade, Object> commandDelegate) {
        return ExecuteDelegateWithReturn(() -> successor.Execute(commandDelegate));
    }

    private void ExecuteDelegate(Runnable commandDelegate) {
        ExecuteDelegateWithReturn(() ->
        {
            commandDelegate.run();
            return null;
        });
    }

    private Object ExecuteDelegateWithReturn(Supplier<Object> commandDelegateWrapper) {
        RuntimeException lastCaughtException = null;
        int tries = 0;

        DateTime end = clock.getUtcNow().withDurationAdded(timeout.getMillis(), 1);
        while (clock.getUtcNow().isBefore(end.toInstant())) {
            try {
                tries++;
                Object returnValue = commandDelegateWrapper.get();
                getLog().Debug(guid, String.format(Resources.getString("TimWtr_Success_Debug"), tries));
                return returnValue;
            } catch (OutOfMemoryError e) {
                getLog().Error(guid, Resources.getString("TimWtr_OutOfMemoryException_Error"));
                getLog().Error(guid, Resources.getString("StackTraceMessage") + e.getMessage() + e.getStackTrace());
                throw e;
            } catch (RuntimeException e) {
                lastCaughtException = e;
                getLog().Debug(guid, String.format(Resources.getString("TimWtr_Exception_Debug"), tries,
                        lastCaughtException.getClass().getSimpleName(), lastCaughtException.getMessage()));
            }

            // Wait before retrying. Excessive attempts may cause WebDriver's client to lose connection with the server.
            Sleep.WaitInternal();
        }

        TimeoutExpiredException ex = new TimeoutExpiredException(
                Resources.getString("TimWtr_TimeoutExpired_DefaultMessage"), timeout, lastCaughtException);

        // TODO: JAVA_CONVERSION Get running processes.
        //var processList = ProcessCollector.GetProcesses();

        Image screenshot = null;
        try {
            screenshot = frameworkAbstractionFacade.GetScreenshot();
        } catch (OutOfMemoryError e) {
            throw e;
        } catch (RuntimeException e2) {
        }

        if (screenshot == null) {
            getLog().Error(guid, ex.getMessage(), /* TODO: JAVA_CONVERSION processList */ new ArrayList<>());
            getLog().Error(Resources.getString("StackTraceMessage"), lastCaughtException);
        } else {
            getLog().Error(guid, ex.getMessage(), screenshot, /* TODO: JAVA_CONVERSION processList */ new ArrayList<>());
            getLog().Error(Resources.getString("StackTraceMessage"), lastCaughtException);
        }

        String pageSource = null;
        try {
            pageSource = frameworkAbstractionFacade.GetPageSource();
        } catch (OutOfMemoryError e3) {
            throw e3;
        } catch (RuntimeException e4) {
        }

        if (pageSource != null) {
            getLog().Trace(guid, ex.getMessage(), pageSource);
        }

        throw ex;
    }
}