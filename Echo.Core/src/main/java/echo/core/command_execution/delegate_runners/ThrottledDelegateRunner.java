package echo.core.command_execution.delegate_runners;

import echo.core.command_execution.delegate_runners.interfaces.IDelegateRunner;
import echo.core.common.helpers.Sleep;
import org.joda.time.Duration;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public class ThrottledDelegateRunner extends DelegateRunner {
    private Duration throttleFactor;

    public ThrottledDelegateRunner(UUID guid, IDelegateRunner successor, Duration throttleFactor) {
        super(guid, successor);
        this.throttleFactor = throttleFactor;
    }

    @Override
    public void Execute(Consumer<IFrameworkAbstractionFacade> commandDelegate) {
        Sleep.WaitDuration(throttleFactor);
        successor.Execute(commandDelegate);
    }

    @Override
    public Object Execute(Function<IFrameworkAbstractionFacade, Object> commandDelegate) {
        Sleep.WaitDuration(throttleFactor);
        return successor.Execute(commandDelegate);
    }
}
