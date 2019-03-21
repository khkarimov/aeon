package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.function.Consumer;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class SelectedCommandTests {

    @Mock
    private Consumer<IDriver> driverConsumer;

    @Mock
    private IWebDriver driver;

    @Mock
    WebControl control;

    @Mock
    private IByWeb selector;

    @Mock
    private ICommandInitializer commandInitializer;

    private SelectedCommand command;

    @BeforeEach
    public void setUp() {
        command = new SelectedCommand(selector, commandInitializer);
    }

    @Test
    public void selectedCommand_CallsExecute() {
        when(commandInitializer.setContext()).thenReturn(driverConsumer);
        when(commandInitializer.findElement(driver, selector)).thenReturn(control);
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);
        verify(driver, times(1)).selected(control);
    }
}
