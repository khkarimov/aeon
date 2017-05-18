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

public class UploadFileDialogCommandTests {

    private UploadFileDialogCommand uploadFileDialogCommandObject;
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
        uploadFileDialogCommandObject = new UploadFileDialogCommand(selector, initializer, path);
    }

    @Test
    public void driverDelegateUploadFileDialogCommand() {
        // Arrange
        when(initializer.setContext()).thenReturn(action);

        // Act
        uploadFileDialogCommandObject.driverDelegate(driver);
        action.accept(driver);

        // Assert
        verify(driver, times(1)).uploadFileDialog(selector, path);
    }

    @Test(expected = IllegalArgumentException.class)
    public void driverDelegateUploadFileDialogCommandWithNullDriver() {
        // Arrange
        // nothing to do

        // Act
        uploadFileDialogCommandObject.driverDelegate(null);
    }
}