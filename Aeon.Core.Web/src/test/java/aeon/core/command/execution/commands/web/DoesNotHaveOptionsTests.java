package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DoesNotHaveOptionsTests {

    private WebSelectOption select = null;
    private String[] options = {};

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private IByWeb selector;
    @Mock
    private ICommandInitializer commandInitializer;
    @Mock
    private IWebDriver driver;
    @Mock
    private WebControl control;

    @Test
    public void commandDelegateExecutesDoesNotHaveOptions(){
        // Arrange
        DoesNotHaveOptionsCommand doesNotHaveOptionsCommand = new DoesNotHaveOptionsCommand(selector, commandInitializer, options, select);

        // Act
        doesNotHaveOptionsCommand.commandDelegate(driver, control);

        // Assert
        verify(driver, times(1)).doesNotHaveOptions(control, options, null, select);
    }

    @Test
    public void commandDelegateExecutesDoesNotHaveOptionsSecondConstructor(){
        // Arrange
        String optGroup = "Test";
        DoesNotHaveOptionsCommand doesNotHaveOptionsCommandSecond = new DoesNotHaveOptionsCommand(selector, commandInitializer, options, optGroup, select);

        // Act
        doesNotHaveOptionsCommandSecond.commandDelegate(driver, control);

        // Assert
        verify(driver, times(1)).doesNotHaveOptions(control, options, optGroup, select);
    }
}