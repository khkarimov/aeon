package aeon.core.command.execution.commands.mobile;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IMobileDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class SetGeoLocationCommandTests {
    private SetGeoLocationCommand command;

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

    @BeforeEach
    public void setup() {
        command = new SetGeoLocationCommand(latitude, longitude, altitude);
    }

    @Test
    public void commandDelegate() {
        // Arrange

        // Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        // Assert
        verify(driver, times(1)).mobileSetGeoLocation(latitude, longitude, altitude);
    }
}
