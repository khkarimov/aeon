package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.common.exceptions.NoSuchElementException;
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
public class NotExistsCommandTests {

    @Mock
    WebControl control;

    @Mock
    IByWeb selector;

    @Mock
    IWebDriver driver;

    private NotExistsCommand command;

    @BeforeEach
    public void setUp() {
        command = new NotExistsCommand(selector);
    }

    @Test
    public void notExists_FindElementSuccessfulTry() {
        // Arrange
        when(driver.findElement(selector)).thenReturn(control);

        // Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        // Assert
        verify(driver, times(1)).findElement(selector);
    }

    @Test
    public void notExists_FindElementCatch() {
        // Arrange
        NoSuchElementException e = new NoSuchElementException(new Exception(), selector);
        when(driver.findElement(selector)).thenThrow(e);

        // Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        // Assert
        verify(driver, times(1)).findElement(selector);
        verify(driver, times(0)).notExists(control);
    }

    @Test
    public void notExists_CallsExecute() {
        // Arrange
        when(driver.findElement(selector)).thenReturn(control);

        // Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        // Assert
        verify(driver, times(1)).notExists(control);
    }
}
