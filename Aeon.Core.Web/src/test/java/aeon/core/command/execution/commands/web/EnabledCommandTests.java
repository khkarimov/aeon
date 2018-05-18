package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class EnabledCommandTests {

    private EnabledCommand command;

    @Mock
    IWebDriver driver;

    @Mock
    WebControl control;

    @Mock
    private Consumer<IDriver> driverConsumer;

    @Mock
    private ICommandInitializer commandInitializer;

    @Mock
    IByWeb selector;

    @BeforeEach
    public void setUp() {
        command = new EnabledCommand(selector, commandInitializer);
    }

    @Test
    public void enabledCommand_CallsExecute() {
        when(commandInitializer.setContext()).thenReturn(driverConsumer);
        when(commandInitializer.findElement(driver, selector)).thenReturn(control);
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);
        verify(driver, times(1)).isElementEnabled(control);
    }
}
