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
public class DoesNotHaveLikeCommandTests {

    private DoesNotHaveLikeCommand doesNotHaveLikeCommand;

    @Mock
    private IByWeb selector;
    @Mock
    private ICommandInitializer initializer;
    @Mock
    private IWebDriver driver;
    @Mock
    private WebControl control;
    @Mock
    private Consumer<IDriver> action;

    private String[] messages = {"Test", "Test2"};
    private String childSelector = "Child";
    private String attribute = "attribute";

    @BeforeEach
    public void setup() {

        doesNotHaveLikeCommand = new DoesNotHaveLikeCommand(selector, initializer, messages, childSelector, ComparisonOption.TEXT, attribute);
    }

    @Test
    public void DoesNotHaveLikeCommandDelegate() {
        // Arrange
        when(initializer.setContext()).thenReturn(action);
        when(initializer.findElement(driver, selector)).thenReturn(control);

        // Act
        Consumer<IDriver> action = doesNotHaveLikeCommand.getCommandDelegate();
        action.accept(driver);

        // Verify
        verify(driver, times(1)).doesNotHaveLike(control, messages, childSelector, ComparisonOption.TEXT, attribute);
    }
}
