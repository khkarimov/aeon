package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SelectFileDialogCommandTests {

    private SelectFileDialogCommand selectFileDialogCommandObject;
    private String path = "Path Test";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IByWeb selector;
    @Mock
    private ICommandInitializer initializer;
    @Mock
    private IWebDriver driver;
    @Mock
    private Consumer<IDriver> action;

    @BeforeEach
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

    @Test
    public void driverDelegateSelectFileDialogCommandWithNullDriver() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> selectFileDialogCommandObject.driverDelegate(null));
    }
}