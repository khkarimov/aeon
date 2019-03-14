package aeon.core.command.execution.commands.mobile;

import aeon.core.framework.abstraction.drivers.IMobileDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class NativeSetDateCommandTest {
    @Mock
    IMobileDriver iMobileDriverMock;

    private NativeSetDateCommand nativeSetDateCommand;

    @BeforeEach
    public void setUp() {
        this.nativeSetDateCommand = new NativeSetDateCommand("1972-11-11");
    }

    @Test
    public void driverDelegate_happyPath_setsDate() {
        // Arrange
        LocalDate expecedDate = LocalDate.parse("1972-11-11");

        // Act
        nativeSetDateCommand.driverDelegate(iMobileDriverMock);

        // Assert
        verify(iMobileDriverMock, times(1)).setDate(expecedDate);
    }
}
