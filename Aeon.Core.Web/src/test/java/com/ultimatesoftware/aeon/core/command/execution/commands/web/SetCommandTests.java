package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.web.WebSelectOption;
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
public class SetCommandTests {

    private SetCommand setCommandObject;

    @Mock
    IByWeb selector;

    @Mock
    ICommandInitializer initializer;

    @Mock
    WebControl control;

    @Mock
    IWebDriver driver;

    @Mock
    Consumer<IDriver> driverConsumer;

    String value;

    @BeforeEach
    public void setUp() {
        setCommandObject = new SetCommand(selector, initializer, WebSelectOption.TEXT, value);
    }

    @Test
    public void commandDelegateSetCommand() {
        //Arrange
        when(initializer.setContext()).thenReturn(driverConsumer);
        when(initializer.findElement(driver, selector)).thenReturn(control);

        //Act
        Consumer<IDriver> action = setCommandObject.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).set(control, WebSelectOption.TEXT, value);
    }
}
