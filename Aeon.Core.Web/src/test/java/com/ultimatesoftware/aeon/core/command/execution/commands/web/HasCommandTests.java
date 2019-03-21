package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.ComparisonOption;
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
public class HasCommandTests {

    @Mock
    private WebControl control;
    @Mock
    private Consumer<IDriver> action;
    private String[] messages = {"test", "test1", "test2", "test3"};
    private String str = "teststes";
    private String atr = "testeests";
    @Mock
    private IWebDriver driver;
    @Mock
    private IByWeb selector;
    @Mock
    private ICommandInitializer commandInitializer;
    private HasCommand command;

    @BeforeEach
    public void setUp() {
        command = new HasCommand(selector, commandInitializer, messages, str, ComparisonOption.RAW, atr);
    }

    @Test
    public void commandDelegateHasCommand() {
        // Arrange
        when(commandInitializer.setContext()).thenReturn(action);
        when(commandInitializer.findElement(driver, selector)).thenReturn(control);

        // Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).has(control, messages, str, ComparisonOption.RAW, atr);
    }
}
