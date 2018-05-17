package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class OpenFileDialogCommandTests {

    private OpenFileDialogCommand openFileDialogCommandObject;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IByWeb selector;

    @Mock
    private ICommandInitializer initializer;

    @Mock
    private IWebDriver driver;

    @Mock
    private IWebDriver Nulldriver;

    @Mock
    private WebControl control;

    @Mock
    private Consumer<IDriver> action;

    @BeforeEach
    public void setup(){
        openFileDialogCommandObject= new OpenFileDialogCommand(selector,initializer);
        Nulldriver=null;
    }

    @Test
    public void commandDelegateOpenFileDialogCommand(){
        //Arrange
        when(initializer.setContext()).thenReturn(action);
        when(initializer.findElement(driver,selector)).thenReturn(control);

        //Act
        Consumer<IDriver> action = openFileDialogCommandObject.getCommandDelegate();
        action.accept( driver);

        //Assert
        verify( driver, times(1) ).openFileDialog(control);

    }

    @Test
    public void commandDelegateOpenFileDialogThrowIfNullDriver(){
        //Arrange
        when(initializer.setContext()).thenReturn(action);
        when(initializer.findElement(driver,selector)).thenReturn(control);

        //Act
        Consumer<IDriver> action = openFileDialogCommandObject.getCommandDelegate();
        action.accept(driver);

        //Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> openFileDialogCommandObject.driverDelegate(Nulldriver));
    }
}
