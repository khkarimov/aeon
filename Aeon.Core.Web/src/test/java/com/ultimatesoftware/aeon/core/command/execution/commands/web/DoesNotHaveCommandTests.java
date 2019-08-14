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
public class DoesNotHaveCommandTests {

    DoesNotHaveCommand command;

    @Mock
    private IByWeb selector;

    @Mock
    private ICommandInitializer initializer;

    private String[] messages = {"test", "test1", "test2", "test3"};

    private String str = "teststes";

    private String atr = "testeests";

    @Mock
    private IWebDriver driver;

    @Mock
    private Consumer<IDriver> action;

    @Mock
    private WebControl control;

    @BeforeEach
    public void setUp() {
        command = new DoesNotHaveCommand(selector, initializer, messages, str, ComparisonOption.TEXT, atr);
    }

    @Test
    public void commandDelegateDoesNotHaveCommand() {
        // Arrange
        when(initializer.setContext()).thenReturn(action);
        when(initializer.findElement(driver, selector)).thenReturn(control);

        // Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        // Assert
        verify(driver, times(1)).doesNotHave(control, messages, str, ComparisonOption.TEXT, atr);
    }

}
