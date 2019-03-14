package aeon.core.command.execution.commands.mobile;

import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.drivers.IMobileDriver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class SwitchToWebViewCommandTests {

    @Mock
    IByWeb mockSelector;

    @Mock
    IMobileDriver mockDriver;

    private SwitchToWebViewCommand command;

    @Test
    void driverDelegate_HappyPath() {

        //Arrange
        command = new SwitchToWebViewCommand(mockSelector);

        //Act
        command.driverDelegate(mockDriver);

        //Assert
        verify(mockDriver, times(1)).switchToWebView(mockSelector);
    }
}
