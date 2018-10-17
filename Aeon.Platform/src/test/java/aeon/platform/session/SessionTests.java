package aeon.platform.session;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.WebCommandExecutionFacade;
import aeon.platform.models.ExecuteCommandBody;
import aeon.platform.services.CommandService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.lang.reflect.Constructor;

import static org.mockito.Mockito.*;

public class SessionTests {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule public ExpectedException expectedException = ExpectedException.none();

    private Session session;

    @Mock private AutomationInfo automationInfoMock;
    @Mock private WebCommandExecutionFacade commandExecutionFacadeMock;
    @Mock private CommandService commandServiceMock;

    @Mock private ExecuteCommandBody bodyMock;
    @Mock private Constructor constructorMock;

    @Before
    public void setUp() {
        session = new Session(automationInfoMock, commandExecutionFacadeMock);
        session.setCommandService(commandServiceMock);
    }

    @Test
    public void executeCommandTest() throws Exception {
        when(bodyMock.getCommand()).thenReturn("GoToUrlCommand");
        when(commandServiceMock.getCommandInstance("GoToUrlCommand")).thenReturn(constructorMock);
        when(commandServiceMock.executeCommand(constructorMock, bodyMock, automationInfoMock, commandExecutionFacadeMock))
                .thenReturn("GoToUrlCommand Successful");

        Object object = session.executeCommand(bodyMock);

        verify(bodyMock, times(2)).getCommand();
        verify(commandServiceMock, times(1)).getCommandInstance("GoToUrlCommand");
        verify(commandServiceMock, times(1))
                .executeCommand(constructorMock, bodyMock, automationInfoMock, commandExecutionFacadeMock);

        Assert.assertEquals("GoToUrlCommand Successful", object);
    }

    @Test
    public void executeNullCommandTest() throws Exception {
        when(bodyMock.getCommand()).thenReturn(null);

        Assertions.assertThrows(Exception.class, () -> session.executeCommand(bodyMock));
    }
}
