package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;
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
public class HasNumberOfOptionsCommandTests {

    private int number = 3;

    @Mock
    private IByWeb selector;
    @Mock
    private ICommandInitializer initializer;
    @Mock
    private IWebDriver driver;
    @Mock
    private WebControl control;

    @Test
    public void HasAllOptionsInOrderCommandTestsInOrder(){
        // Arrange
        String optGroup = "Test";
        HasNumberOfOptionsCommand hasNumberOfOptionsCommand = new HasNumberOfOptionsCommand(selector, initializer, number, optGroup);

        // Act
        hasNumberOfOptionsCommand.commandDelegate(driver, control);

        // Verify
        verify(driver, times(1)).hasNumberOfOptions(control, number, optGroup);
    }

    @Test
    public void HasAllOptionsInOrderCommandTestsInOrderSecondConstructor(){
        // Arrange
        HasNumberOfOptionsCommand hasNumberOfOptionsCommandSecond = new HasNumberOfOptionsCommand(selector, initializer, number);

        // Act
        hasNumberOfOptionsCommandSecond.commandDelegate(driver, control);

        // Verify
        verify(driver, times(1)).hasNumberOfOptions(control, number, null);
    }
}