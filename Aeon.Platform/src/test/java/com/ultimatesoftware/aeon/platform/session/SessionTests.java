package com.ultimatesoftware.aeon.platform.session;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.command.execution.ICommandExecutionFacade;
import com.ultimatesoftware.aeon.core.command.execution.commands.CommandWithReturn;
import com.ultimatesoftware.aeon.core.command.execution.commands.QuitCommand;
import com.ultimatesoftware.aeon.core.common.exceptions.CommandExecutionException;
import com.ultimatesoftware.aeon.core.extensions.IProductTypeExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class SessionTests {

    private Session session;

    @Mock
    private AutomationInfo automationInfoMock;
    @Mock
    private ICommandExecutionFacade commandExecutionFacadeMock;
    @Mock
    private Supplier<List<IProductTypeExtension>> supplierMock;

    @Mock
    private List<Object> argsMock;


    private List<IProductTypeExtension> extensions;


    @Mock
    private IProductTypeExtension extensionMock;
    @Mock
    private CommandWithReturn commandMock;

    @BeforeEach
    public void setUp() {
        session = new Session(automationInfoMock, commandExecutionFacadeMock, supplierMock);

        extensions = new ArrayList<>();
        extensions.add(extensionMock);
    }

    @Test
    public void executeCommandTest() throws CommandExecutionException {
        when(supplierMock.get()).thenReturn(extensions);
        when(extensionMock.createCommand("GoToUrlCommand", argsMock)).thenReturn(commandMock);
        when(commandExecutionFacadeMock.execute(automationInfoMock, commandMock)).thenReturn("GoToUrlCommand Successful");

        Object object = session.executeCommand("GoToUrlCommand", argsMock);

        verify(supplierMock, times(1)).get();
        verify(extensionMock, times(1)).createCommand("GoToUrlCommand", argsMock);
        verify(commandExecutionFacadeMock, times(1)).execute(automationInfoMock, commandMock);

        assertEquals("GoToUrlCommand Successful", object);
    }

    @Test
    public void executeNullCommandTest() {
        when(supplierMock.get()).thenReturn(extensions);
        when(extensionMock.createCommand("GoToUrlCommand", argsMock)).thenReturn(null);

        Assertions.assertThrows(CommandExecutionException.class, () -> session.executeCommand("GoToUrlCommand", argsMock));

        verify(supplierMock, times(1)).get();
        verify(extensionMock, times(1)).createCommand("GoToUrlCommand", argsMock);
        verify(commandExecutionFacadeMock, times(0)).execute(automationInfoMock, commandMock);
    }

    @Test
    public void executeNullCommandStringTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> session.executeCommand(null, null));
    }

    @Test
    public void quitSessionTest() {
        session.quitSession();

        verify(commandExecutionFacadeMock, times(1)).execute(eq(automationInfoMock), any(QuitCommand.class));
    }
}
