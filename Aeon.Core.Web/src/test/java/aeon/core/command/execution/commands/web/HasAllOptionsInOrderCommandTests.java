package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.CompareType;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class HasAllOptionsInOrderCommandTests {

    private HasAllOptionsInOrderCommand hasAllOptionsInOrderCommand;

    private CompareType compare = CompareType.AscendingByText;
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
    public void setup(){ hasAllOptionsInOrderCommand = new HasAllOptionsInOrderCommand(selector, initializer, compare, optGroup); }

    @Test
    public void HasAllOptionsInOrderCommandTestsInOrder(){

        // Arrange

        // Act
        hasAllOptionsInOrderCommand.commandDelegate(driver, control);

        // Verify
        verify(driver, times(1)).hasAllOptionsInOrder(control, compare, optGroup);

    }
}
