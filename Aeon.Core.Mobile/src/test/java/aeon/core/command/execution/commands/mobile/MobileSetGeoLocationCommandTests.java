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

public class MobileSetGeoLocationCommandTests {
    private MobileSetGeoLocationCommand command;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IByWeb selector;
    @Mock
    private ICommandInitializer initializer;
    @Mock
    private IMobileDriver driver;
    @Mock
    private WebControl control;
    @Mock
    private Consumer<IDriver> action;

    private int latitude = 1;
    private int longitude = 2;
    private int altitude = 3;

    @Before
    public void setup() {
        command = new MobileSetGeoLocationCommand(latitude, longitude, altitude);
    }

    @Test
    public void commandDelegate() {
        // Arrange
        when(initializer.setContext()).thenReturn(action);
        when(initializer.findElement(driver, selector)).thenReturn(control);

        // Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        // Assert
        verify(driver, times(1)).mobileSetGeoLocation(latitude, longitude, altitude);
    }
}