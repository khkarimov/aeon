package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DoesNotHaveOptionsTests {
    private DoesNotHaveOptionsCommand doesNotHaveOptionsCommandObject;
    private WebSelectOption select;
    private String[] options;
    private String optgroup;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IByWeb selector;

    @Mock
    private ICommandInitializer commandInitializer;

    @Mock
    private IWebDriver driver;

    @Mock
    private WebControl control;

    @Before
    public void setup(){
        doesNotHaveOptionsCommandObject = new DoesNotHaveOptionsCommand(selector, commandInitializer, options, optgroup, select);
    }

    @Test
    public void commandDelegateExecutesDoesNotHaveOptions(){
        // Arrange
        
        // Act
        doesNotHaveOptionsCommandObject.commandDelegate(driver, control);

        // Assert
        verify(driver, times(1)).doesNotHaveOptions(control, options, optgroup, select);
    }
}
