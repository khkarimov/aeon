package aeon.core.command.execution.commands.mobile;

import aeon.core.framework.abstraction.drivers.IMobileDriver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class NativeSetDateCommandTests {
    @Mock
    IMobileDriver iMobileDriverMock;

    private NativeSetDateCommand nativeSetDateCommand;

    @Test
    public void driverDelegate_happyPath_setsDate() {
        // Arrange
        LocalDate expectedDate = LocalDate.parse("1972-11-11");

        // Act
        this.nativeSetDateCommand = new NativeSetDateCommand("1972-11-11");
        nativeSetDateCommand.driverDelegate(iMobileDriverMock);

        // Assert
        verify(iMobileDriverMock, times(1)).setDate(expectedDate);
    }

    @Test
    public void driverDelegate_notADate_throwsDateTimeParseException() {
        // Arrange

        // Act
        Executable action = () -> this.nativeSetDateCommand = new NativeSetDateCommand("bad date");

        // Assert
        Assertions.assertThrows(DateTimeParseException.class, action);
    }
}
