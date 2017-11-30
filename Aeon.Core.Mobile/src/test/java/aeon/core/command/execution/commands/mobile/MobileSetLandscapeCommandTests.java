package aeon.core.command.execution.commands.mobile;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IMobileDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class MobileSetLandscapeCommandTests {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private WebControl control;
    @Mock
    private Consumer<IDriver> driverConsumer;
    @Mock
    private IMobileDriver driver;
    @Mock
    private ICommandInitializer commandInitializer;

    @Mock
    private IByWeb selector;

    private MobileSetLandscapeCommand command;

    @Before
    public void setUp() {
        command = new MobileSetLandscapeCommand();
    }

    @Test
    public void notVisibleCommand_CallsExecute() {
        //Arrange
        when(commandInitializer.setContext()).thenReturn(driverConsumer);
        when(commandInitializer.findElement(driver, selector)).thenReturn(control);

        //Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).mobileSetLandscape();
    }
}
