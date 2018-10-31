package aeon.platform.http;

import aeon.core.common.exceptions.CommandExecutionException;
import aeon.platform.http.controllers.HttpSessionController;
import aeon.platform.http.models.ResponseBody;
import aeon.platform.session.ISession;
import aeon.platform.http.models.CreateSessionBody;
import aeon.platform.http.models.ExecuteCommandBody;
import aeon.platform.factories.SessionFactory;
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

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionControllerTests {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule public ExpectedException expectedException = ExpectedException.none();

    private HttpSessionController httpSessionController;
    private ObjectId sessionId;

    @Mock private ISession sessionMock;
    @Mock private Map<ObjectId, ISession> sessionTableMock;

    @Mock private Properties settingsMock;
    @Mock private List<Object> argsMock;

    @Mock private CreateSessionBody createSessionBodyMock;
    @Mock private ExecuteCommandBody executeCommandBodyMock;

    @Mock private SessionFactory sessionFactoryMock;
    @Mock private ThreadFactory threadFactoryMock;
    @Mock private ThreadClass threadMock;

    @Before
    public void setUp() {
        httpSessionController = new HttpSessionController(sessionFactoryMock, threadFactoryMock);
        httpSessionController.setSessionTable(sessionTableMock);

        sessionId = new ObjectId();
    }

    @Test
    public void createSessionTest() throws Exception {
        when(createSessionBodyMock.getSettings()).thenReturn(settingsMock);
        when(sessionFactoryMock.getSession(settingsMock)).thenReturn(sessionMock);

        ResponseEntity response = httpSessionController.createSession(createSessionBodyMock);

        verify(createSessionBodyMock, times(1)).getSettings();
        verify(sessionFactoryMock, times(1)).getSession(settingsMock);
        verify(sessionTableMock, times(1)).put(any(ObjectId.class), eq(sessionMock));

        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void createSessionNullSettingsTest() throws Exception {
        when(createSessionBodyMock.getSettings()).thenReturn(null);
        when(sessionFactoryMock.getSession(null)).thenReturn(sessionMock);

        ResponseEntity response = httpSessionController.createSession(createSessionBodyMock);

        verify(createSessionBodyMock, times(1)).getSettings();
        verify(sessionFactoryMock, times(1)).getSession(null);
        verify(sessionTableMock, times(1)).put(any(ObjectId.class), eq(sessionMock));

        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void executeCommandTest() {
        when(sessionTableMock.containsKey(sessionId)).thenReturn(true);
        when(sessionTableMock.get(sessionId)).thenReturn(sessionMock);
        when(executeCommandBodyMock.getCommand()).thenReturn("GoToUrlCommand");
        when(executeCommandBodyMock.getArgs()).thenReturn(argsMock);
        when(sessionMock.executeCommand("GoToUrlCommand", argsMock)).thenReturn("GoToUrlCommand Successful");

        ResponseEntity response = httpSessionController.executeCommand(sessionId, executeCommandBodyMock);
        ResponseBody body = (ResponseBody) response.getBody();

        verify(sessionTableMock, times(1)).containsKey(sessionId);
        verify(sessionTableMock, times(1)).get(sessionId);
        verify(executeCommandBodyMock, times(1)).getCommand();
        verify(executeCommandBodyMock, times(1)).getArgs();
        verify(sessionMock, times(1)).executeCommand("GoToUrlCommand", argsMock);

        Assert.assertEquals(sessionId.toString(), body.getSessionId());
        Assert.assertTrue(body.getSuccess());
        Assert.assertEquals("GoToUrlCommand Successful", body.getData());
        Assert.assertNull(body.getFailureMessage());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void executeNullCommandTest() {
        when(sessionTableMock.containsKey(sessionId)).thenReturn(true);
        when(sessionTableMock.get(sessionId)).thenReturn(sessionMock);
        when(executeCommandBodyMock.getCommand()).thenReturn(null);
        when(executeCommandBodyMock.getArgs()).thenReturn(null);
        when(sessionMock.executeCommand(null, null)).thenThrow(new CommandExecutionException("Invalid command."));

        ResponseEntity response = httpSessionController.executeCommand(sessionId, executeCommandBodyMock);
        ResponseBody body = (ResponseBody) response.getBody();

        verify(sessionTableMock, times(1)).containsKey(sessionId);
        verify(sessionTableMock, times(1)).get(sessionId);
        verify(executeCommandBodyMock, times(1)).getCommand();
        verify(executeCommandBodyMock, times(1)).getArgs();
        verify(sessionMock, times(1)).executeCommand(null, null);

        Assert.assertEquals(sessionId.toString(), body.getSessionId());
        Assert.assertFalse(body.getSuccess());
        Assert.assertNull(body.getData());
        Assert.assertEquals("Unable to execute command: Invalid command.", body.getFailureMessage());
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void executeCommandSessionNotFoundTest() {
        when(sessionTableMock.containsKey(sessionId)).thenReturn(false);

        ResponseEntity response = httpSessionController.executeCommand(sessionId, executeCommandBodyMock);

        verify(sessionTableMock, times(1)).containsKey(sessionId);
        verify(sessionTableMock, times(0)).get(sessionId);
        verify(executeCommandBodyMock, times(0)).getCommand();
        verify(executeCommandBodyMock, times(0)).getArgs();
        verify(sessionMock, times(0)).executeCommand(anyString(), anyList());

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void executeAsyncCommandTest() {
        when(sessionTableMock.containsKey(sessionId)).thenReturn(true);
        when(sessionTableMock.get(sessionId)).thenReturn(sessionMock);
        when(executeCommandBodyMock.getCommand()).thenReturn("GoToUrlCommand");
        when(executeCommandBodyMock.getArgs()).thenReturn(argsMock);
        when(threadFactoryMock.getThread(sessionId, sessionMock, "GoToUrlCommand", argsMock)).thenReturn(threadMock);

        ResponseEntity response = httpSessionController.executeAsyncCommand(sessionId, executeCommandBodyMock);

        verify(sessionTableMock, times(1)).containsKey(sessionId);
        verify(sessionTableMock, times(1)).get(sessionId);
        verify(executeCommandBodyMock, times(1)).getCommand();
        verify(executeCommandBodyMock, times(1)).getArgs();
        verify(threadFactoryMock, times(1)).getThread(sessionId, sessionMock, "GoToUrlCommand", argsMock);
        verify(threadMock, times(1)).start();

        Assert.assertNull(response);
    }

    @Test
    public void executeAsyncCommandSessionNotFoundTest() {
        when(sessionTableMock.containsKey(sessionId)).thenReturn(false);

        ResponseEntity response = httpSessionController.executeAsyncCommand(sessionId, executeCommandBodyMock);

        verify(sessionTableMock, times(1)).containsKey(sessionId);
        verify(sessionTableMock, times(0)).get(sessionId);
        verify(executeCommandBodyMock, times(0)).getCommand();
        verify(executeCommandBodyMock, times(0)).getArgs();
        verify(threadFactoryMock, times(0)).getThread(eq(sessionId), eq(sessionMock), anyString(), anyList());

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void quitSessionTest() throws IOException, TimeoutException {
        when(sessionTableMock.containsKey(sessionId)).thenReturn(true);
        when(sessionTableMock.get(sessionId)).thenReturn(sessionMock);

        ResponseEntity response = httpSessionController.quitSession(sessionId);

        verify(sessionTableMock, times(1)).containsKey(sessionId);
        verify(sessionTableMock, times(1)).get(sessionId);
        verify(sessionMock, times(1)).quitSession();
        verify(sessionTableMock, times(1)).remove(sessionId);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void quitSessionSessionNotFoundTest() throws IOException, TimeoutException {
        when(sessionTableMock.containsKey(sessionId)).thenReturn(false);

        ResponseEntity response = httpSessionController.quitSession(sessionId);

        verify(sessionTableMock, times(1)).containsKey(sessionId);
        verify(sessionTableMock, times(0)).get(sessionId);
        verify(sessionTableMock, times(0)).remove(sessionId);

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
