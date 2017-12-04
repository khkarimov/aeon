package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class HasNumberOfOptionsCommandTests {

    private HasNumberOfOptionsCommand hasNumberOfOptionsCommand;

    private int number = 3;
    private String optGroup = "Test";

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IByWeb selector;
    @Mock
    private ICommandInitializer initializer;
    @Mock
    private IWebDriver driver;
    @Mock
    private WebControl control;

    @Before
    public void setup(){ hasNumberOfOptionsCommand = new HasNumberOfOptionsCommand(selector, initializer, number, optGroup); }

    @Test
    public void HasAllOptionsInOrderCommandTestsInOrder(){

        // Arrange

        // Act
        hasNumberOfOptionsCommand.commandDelegate(driver, control);

        // Verify
        verify(driver, times(1)).hasNumberOfOptions(control, number, optGroup);

    }
}