package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.web.WebSelectOption;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class DoesNotHaveOptionsCommandTests {

    private WebSelectOption select = null;
    private String[] options = {};

    @Mock
    private IByWeb selector;
    @Mock
    private ICommandInitializer commandInitializer;
    @Mock
    private IWebDriver driver;
    @Mock
    private WebControl control;

    @Test
    public void commandDelegateExecutesDoesNotHaveOptions() {
        // Arrange
        DoesNotHaveOptionsCommand doesNotHaveOptionsCommand = new DoesNotHaveOptionsCommand(selector, commandInitializer, options, select);

        // Act
        doesNotHaveOptionsCommand.commandDelegate(driver, control);

        // Assert
        verify(driver, times(1)).doesNotHaveOptions(control, options, null, select);
    }

    @Test
    public void commandDelegateExecutesDoesNotHaveOptionsSecondConstructor() {
        // Arrange
        String optGroup = "Test";
        DoesNotHaveOptionsCommand doesNotHaveOptionsCommandSecond = new DoesNotHaveOptionsCommand(selector, commandInitializer, options, optGroup, select);

        // Act
        doesNotHaveOptionsCommandSecond.commandDelegate(driver, control);

        // Assert
        verify(driver, times(1)).doesNotHaveOptions(control, options, optGroup, select);
    }
}
