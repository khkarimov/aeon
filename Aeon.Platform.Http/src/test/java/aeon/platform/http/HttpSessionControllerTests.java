package aeon.platform.http;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.WebCommandExecutionFacade;
import aeon.platform.ISession;
import aeon.platform.controllers.http.HttpSessionController;
import aeon.platform.models.CreateSessionBody;
import aeon.platform.models.ExecuteCommandBody;
import aeon.platform.services.SessionService;
import aeon.platform.services.CommandService;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Constructor;
import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionControllerTests {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule public ExpectedException expectedException = ExpectedException.none();

    private HttpSessionController httpSessionController;

    @Mock private ISession sessionMock;

    @Mock private AutomationInfo automationInfoMock;
    @Mock private WebCommandExecutionFacade commandExecutionFacadeMock;
    @Mock private Map<ObjectId, ISession> sessionTableMock;

    @Mock private CreateSessionBody createSessionBodyMock;
    @Mock private ExecuteCommandBody executeCommandBodyMock;
    @Mock private ObjectId sessionIdMock;
    @Mock private Constructor constructorMock;

    @Mock private SessionService sessionServiceMock;
    @Mock private CommandService commandServiceMock;

    @Before
    public void setUp() {
        httpSessionController = new HttpSessionController(sessionServiceMock, commandServiceMock);
        httpSessionController.setSessionTable(sessionTableMock);
    }

    @Test
    public void createSessionTest() throws Exception {
        when(sessionServiceMock.createSessionId()).thenReturn(sessionIdMock);
        when(sessionServiceMock.setUpAutomationInfo(null)).thenReturn(automationInfoMock);
        when(sessionServiceMock.setUpCommandExecutionFacade(automationInfoMock)).thenReturn(commandExecutionFacadeMock);

        ResponseEntity response = httpSessionController.createSession(createSessionBodyMock);

        verify(sessionServiceMock, times(1)).setUpAutomationInfo(null);
        verify(sessionServiceMock, times(1)).setUpCommandExecutionFacade(automationInfoMock);
        verify(sessionTableMock, times(1)).put(sessionIdMock, sessionMock);
        //verify(sessionTableMock, times(1)).put(sessionIdMock, automationInfoMock);

        Assert.assertEquals(sessionIdMock.toString(), response.getBody());
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void executeNullCommandTest() throws Exception {
        when(executeCommandBodyMock.getCommand()).thenReturn(null);

        ResponseEntity response = httpSessionController.executeCommand(sessionIdMock, executeCommandBodyMock);

        verify(executeCommandBodyMock, times(1)).getCommand();

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void executeGoToUrlCommandTest() throws Exception {
        when(executeCommandBodyMock.getCommand()).thenReturn("GoToUrlCommand");
        when(sessionTableMock.get(sessionIdMock)).thenReturn(sessionMock);
        //when(sessionTableMock.get(sessionIdMock)).thenReturn(automationInfoMock);
        when(automationInfoMock.getCommandExecutionFacade()).thenReturn(commandExecutionFacadeMock);
        when(commandServiceMock.createConstructor("GoToUrlCommand")).thenReturn(constructorMock);
        when(constructorMock.getName()).thenReturn("aeon.core.command.execution.commands.web.GoToUrlCommand");
        when(commandServiceMock.executeCommand(constructorMock, executeCommandBodyMock, automationInfoMock, commandExecutionFacadeMock))
                .thenReturn(new ResponseEntity<>(new Object(), HttpStatus.OK));

        ResponseEntity response = httpSessionController.executeCommand(sessionIdMock, executeCommandBodyMock);

        verify(executeCommandBodyMock, times(2)).getCommand();
        verify(sessionTableMock, times(1)).get(sessionIdMock);
        verify(automationInfoMock, times(1)).getCommandExecutionFacade();
        verify(commandServiceMock, times(1)).createConstructor("GoToUrlCommand");
        verify(constructorMock, times(1)).getName();
        verify(sessionTableMock, times(0)).remove(sessionIdMock);
        verify(commandServiceMock, times(1))
                .executeCommand(constructorMock, executeCommandBodyMock, automationInfoMock, commandExecutionFacadeMock);

        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void executeQuitCommandTest() throws Exception {
        when(executeCommandBodyMock.getCommand()).thenReturn("QuitCommand");
        when(sessionTableMock.get(sessionIdMock)).thenReturn(sessionMock);
        //when(sessionTableMock.get(sessionIdMock)).thenReturn(automationInfoMock);
        when(automationInfoMock.getCommandExecutionFacade()).thenReturn(commandExecutionFacadeMock);
        when(commandServiceMock.createConstructor("QuitCommand")).thenReturn(constructorMock);
        when(constructorMock.getName()).thenReturn("aeon.core.command.execution.commands.QuitCommand");
        when(commandServiceMock.executeCommand(constructorMock, executeCommandBodyMock, automationInfoMock, commandExecutionFacadeMock))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity response = httpSessionController.executeCommand(sessionIdMock, executeCommandBodyMock);

        verify(executeCommandBodyMock, times(2)).getCommand();
        verify(sessionTableMock, times(1)).get(sessionIdMock);
        verify(automationInfoMock, times(1)).getCommandExecutionFacade();
        verify(commandServiceMock, times(1)).createConstructor("QuitCommand");
        verify(constructorMock, times(1)).getName();
        verify(sessionTableMock, times(1)).remove(sessionIdMock);
        verify(commandServiceMock, times(1))
                .executeCommand(constructorMock, executeCommandBodyMock, automationInfoMock, commandExecutionFacadeMock);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void executeCommandExceptionTest() throws Exception {
        when(executeCommandBodyMock.getCommand()).thenReturn("GoToUrlCommand");
        when(sessionTableMock.get(sessionIdMock)).thenReturn(sessionMock);
        //when(sessionTableMock.get(sessionIdMock)).thenReturn(automationInfoMock);
        when(automationInfoMock.getCommandExecutionFacade()).thenReturn(commandExecutionFacadeMock);
        when(commandServiceMock.createConstructor("GoToUrlCommand")).thenReturn(constructorMock);
        when(constructorMock.getName()).thenReturn("aeon.core.command.execution.commands.web.GoToUrlCommand");
        when(commandServiceMock.executeCommand(constructorMock, executeCommandBodyMock, automationInfoMock, commandExecutionFacadeMock))
                .thenThrow(new Exception());

        ResponseEntity response = httpSessionController.executeCommand(sessionIdMock, executeCommandBodyMock);

        verify(executeCommandBodyMock, times(2)).getCommand();
        verify(sessionTableMock, times(1)).get(sessionIdMock);
        verify(automationInfoMock, times(1)).getCommandExecutionFacade();
        verify(commandServiceMock, times(1)).createConstructor("GoToUrlCommand");
        verify(constructorMock, times(1)).getName();
        verify(sessionTableMock, times(0)).remove(sessionIdMock);
        verify(commandServiceMock, times(1))
                .executeCommand(constructorMock, executeCommandBodyMock, automationInfoMock, commandExecutionFacadeMock);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
