package com.ultimatesoftware.aeon.core.command.execution.commands.mobile;

import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IMobileDriver;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class AcceptOrDenyPermissionDialogIfPresentCommandTests {
    @Mock
    private IMobileDriver iMobileDriverMock;

    private AcceptOrDenyPermissionDialogIfPresentCommand acceptOrDenyPermissionDialogIfPresentCommand;

    @ParameterizedTest
    @CsvSource({"true", "false"})
    public void driverDelegate_happyPath_callsAcceptOrDismissPermissionDialog(boolean accept) {

        //Arrange

        //Act
        this.acceptOrDenyPermissionDialogIfPresentCommand = new AcceptOrDenyPermissionDialogIfPresentCommand(accept);
        acceptOrDenyPermissionDialogIfPresentCommand.driverDelegate(iMobileDriverMock);

        //Assert
        verify(iMobileDriverMock, times(1)).acceptOrDismissPermissionDialog(accept);

    }
}
