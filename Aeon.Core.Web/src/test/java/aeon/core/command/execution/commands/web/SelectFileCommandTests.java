package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SelectFileCommandTests {

    private SelectFileCommand selectFileCommand;
    private String path = "";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IWebDriver driver;

    @Mock
    private IByWeb selector;

    @Mock
    private ICommandInitializer initializer;

    @Mock
    private WebControl control;

    @BeforeEach
    public void setUp() {
        selectFileCommand = new SelectFileCommand(selector, initializer, path);
    }

    @Test
    public void commandDelegateSelectFileCommand() {
        //Arrange

        //Act
        selectFileCommand.commandDelegate(driver, control);

        //Assert
        verify(driver,times(1)).selectFile(control, path);
    }

    @Test
    public void driverNullThrowsException(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> selectFileCommand.commandDelegate(null, null));
    }
}
