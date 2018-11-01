package aeon.platform.session;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.command.execution.commands.QuitCommand;
import aeon.core.common.exceptions.CommandExecutionException;
import aeon.core.extensions.IProductTypeExtension;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static org.mockito.Mockito.*;

public class SessionTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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

    @Before
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

        Assert.assertEquals("GoToUrlCommand Successful", object);
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
