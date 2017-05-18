package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SelectFileDialogCommandTests {

    private SelectFileDialogCommand selectFileDialogCommandObject;
    private String path = "Path Test";

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IBy selector;
    @Mock
    private ICommandInitializer initializer;
    @Mock
    private IWebDriver driver;
    @Mock
    private Consumer<IDriver> action;

    @Before
    public void setup() {
        selectFileDialogCommandObject = new SelectFileDialogCommand(selector, initializer, path);
    }

    @Test
    public void driverDelegateSelectFileDialogCommand() {
        // Arrange
        // nothing to do

        // Act
        selectFileDialogCommandObject.driverDelegate(driver);
        action.accept(driver);

        // Assert
        verify(driver, times(1)).selectFileDialog(selector, path);
    }

    @Test(expected = IllegalArgumentException.class)
    public void driverDelegateSelectFileDialogCommandWithNullDriver() {
        // Arrange
        // nothing to do

        // Act
        selectFileDialogCommandObject.driverDelegate(null);
    }
}