package aeon.platform.http.controllers;

import aeon.core.common.exceptions.CommandExecutionException;
import aeon.core.testabstraction.product.Aeon;
import aeon.platform.factories.SessionFactory;
import aeon.platform.http.HttpSessionIdProvider;
import aeon.platform.http.models.CreateSessionBody;
import aeon.platform.http.models.ExecuteCommandBody;
import aeon.platform.http.models.ResponseBody;
import aeon.platform.http.threads.CommandExecutionThread;
import aeon.platform.http.threads.ThreadFactory;
import aeon.platform.session.ISession;
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

import javax.ws.rs.core.Response;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionControllerTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private HttpSessionController httpSessionController;
    private ObjectId sessionId;

    @Mock
    private ISession sessionMock;

    @Mock
    private Map<ObjectId, ISession> sessionTableMock;

    @Mock
    private Properties settingsMock;

    @Mock
    private List<Object> argsMock;

    @Mock
    private CreateSessionBody createSessionBodyMock;

    @Mock
    private ExecuteCommandBody executeCommandBodyMock;

    @Mock
    private SessionFactory sessionFactoryMock;

    @Mock
    private ThreadFactory threadFactoryMock;

    @Mock
    private CommandExecutionThread threadMock;

    @Mock
    private HttpSessionIdProvider sessionIdProvider;

    @Before
    public void setUp() {
        Aeon.setSessionIdProvider(this.sessionIdProvider);
        httpSessionController = new HttpSessionController(sessionFactoryMock, threadFactoryMock, sessionTableMock);

        sessionId = new ObjectId();
    }

    @Test
    public void createSessionTest() throws Exception {
        when(createSessionBodyMock.getSettings()).thenReturn(settingsMock);
        when(sessionFactoryMock.getSession(settingsMock)).thenReturn(sessionMock);

        Response response = httpSessionController.createSession(createSessionBodyMock);

        verify(createSessionBodyMock, times(1)).getSettings();
        verify(sessionFactoryMock, times(1)).getSession(settingsMock);
        verify(sessionTableMock, times(1)).put(any(ObjectId.class), eq(sessionMock));

        Assert.assertNotNull(response.getEntity());
        Assert.assertEquals(201, response.getStatus());
    }

    @Test
    public void createSessionNullSettingsTest() throws Exception {
        when(createSessionBodyMock.getSettings()).thenReturn(null);
        when(sessionFactoryMock.getSession(null)).thenReturn(sessionMock);

        Response response = httpSessionController.createSession(createSessionBodyMock);

        verify(createSessionBodyMock, times(1)).getSettings();
        verify(sessionFactoryMock, times(1)).getSession(null);
        verify(sessionTableMock, times(1)).put(any(ObjectId.class), eq(sessionMock));

        Assert.assertNotNull(response.getEntity());
        Assert.assertEquals(201, response.getStatus());
    }

    @Test
    public void executeCommandTest() throws CommandExecutionException {
        when(sessionTableMock.containsKey(sessionId)).thenReturn(true);
        when(sessionTableMock.get(sessionId)).thenReturn(sessionMock);
        when(executeCommandBodyMock.getCommand()).thenReturn("GoToUrlCommand");
        when(executeCommandBodyMock.getArgs()).thenReturn(argsMock);
        when(sessionMock.executeCommand("GoToUrlCommand", argsMock)).thenReturn("GoToUrlCommand Successful");

        Response response = httpSessionController.executeCommand(sessionId, executeCommandBodyMock);
        ResponseBody body = (ResponseBody) response.getEntity();

        verify(sessionTableMock, times(1)).containsKey(sessionId);
        verify(sessionTableMock, times(1)).get(sessionId);
        verify(executeCommandBodyMock, times(1)).getCommand();
        verify(executeCommandBodyMock, times(1)).getArgs();
        verify(sessionMock, times(1)).executeCommand("GoToUrlCommand", argsMock);

        Assert.assertEquals(sessionId.toString(), body.getSessionId());
        Assert.assertTrue(body.getSuccess());
        Assert.assertEquals("GoToUrlCommand Successful", body.getData());
        Assert.assertNull(body.getFailureMessage());
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void executeNullCommandTest() throws CommandExecutionException {
        when(sessionTableMock.containsKey(sessionId)).thenReturn(true);
        when(sessionTableMock.get(sessionId)).thenReturn(sessionMock);
        when(executeCommandBodyMock.getCommand()).thenReturn(null);
        when(executeCommandBodyMock.getArgs()).thenReturn(null);
        when(sessionMock.executeCommand(null, null)).thenThrow(new CommandExecutionException("Invalid command."));

        Response response = httpSessionController.executeCommand(sessionId, executeCommandBodyMock);
        ResponseBody body = (ResponseBody) response.getEntity();

        verify(sessionTableMock, times(1)).containsKey(sessionId);
        verify(sessionTableMock, times(1)).get(sessionId);
        verify(executeCommandBodyMock, times(1)).getCommand();
        verify(executeCommandBodyMock, times(1)).getArgs();
        verify(sessionMock, times(1)).executeCommand(null, null);

        Assert.assertEquals(sessionId.toString(), body.getSessionId());
        Assert.assertFalse(body.getSuccess());
        Assert.assertNull(body.getData());
        Assert.assertEquals("Unable to execute command: Invalid command.", body.getFailureMessage());
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void executeCommandSessionNotFoundTest() throws CommandExecutionException {
        when(sessionTableMock.containsKey(sessionId)).thenReturn(false);

        Response response = httpSessionController.executeCommand(sessionId, executeCommandBodyMock);

        verify(sessionTableMock, times(1)).containsKey(sessionId);
        verify(sessionTableMock, times(0)).get(sessionId);
        verify(executeCommandBodyMock, times(0)).getCommand();
        verify(executeCommandBodyMock, times(0)).getArgs();
        verify(sessionMock, times(0)).executeCommand(anyString(), anyList());

        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void executeAsyncCommandTest() {
        when(sessionTableMock.containsKey(sessionId)).thenReturn(true);
        when(sessionTableMock.get(sessionId)).thenReturn(sessionMock);
        when(executeCommandBodyMock.getCommand()).thenReturn("GoToUrlCommand");
        when(executeCommandBodyMock.getArgs()).thenReturn(argsMock);
        when(executeCommandBodyMock.getCallbackUrl()).thenReturn("callbackUrl");
        when(threadFactoryMock.getCommandExecutionThread(sessionId, sessionMock, "GoToUrlCommand", argsMock, "callbackUrl", sessionIdProvider)).thenReturn(threadMock);

        Response response = httpSessionController.executeAsyncCommand(sessionId, executeCommandBodyMock);
        ResponseBody body = (ResponseBody) response.getEntity();

        verify(sessionTableMock, times(1)).containsKey(sessionId);
        verify(sessionTableMock, times(1)).get(sessionId);
        verify(executeCommandBodyMock, times(1)).getCommand();
        verify(executeCommandBodyMock, times(1)).getArgs();
        verify(threadFactoryMock, times(1)).getCommandExecutionThread(sessionId, sessionMock, "GoToUrlCommand", argsMock, "callbackUrl", sessionIdProvider);
        verify(threadMock, times(1)).start();

        Assert.assertEquals(sessionId.toString(), body.getSessionId());
        Assert.assertTrue(body.getSuccess());
        Assert.assertEquals("The asynchronous command was successfully scheduled.", body.getData());
        Assert.assertNull(body.getFailureMessage());
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void executeAsyncCommandSessionNotFoundTest() {
        when(sessionTableMock.containsKey(sessionId)).thenReturn(false);

        Response response = httpSessionController.executeAsyncCommand(sessionId, executeCommandBodyMock);

        verify(sessionTableMock, times(1)).containsKey(sessionId);
        verify(sessionTableMock, times(0)).get(sessionId);
        verify(executeCommandBodyMock, times(0)).getCommand();
        verify(executeCommandBodyMock, times(0)).getArgs();
        verify(threadFactoryMock, times(0)).getCommandExecutionThread(eq(sessionId), eq(sessionMock), anyString(), anyList(), anyString(), any());

        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void quitSessionTest() {
        when(sessionTableMock.containsKey(sessionId)).thenReturn(true);
        when(sessionTableMock.get(sessionId)).thenReturn(sessionMock);

        Response response = httpSessionController.quitSession(sessionId);

        verify(sessionTableMock, times(1)).containsKey(sessionId);
        verify(sessionTableMock, times(1)).get(sessionId);
        verify(sessionMock, times(1)).quitSession();
        verify(sessionTableMock, times(1)).remove(sessionId);

        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void quitSessionSessionNotFoundTest() {
        when(sessionTableMock.containsKey(sessionId)).thenReturn(false);

        Response response = httpSessionController.quitSession(sessionId);

        verify(sessionTableMock, times(1)).containsKey(sessionId);
        verify(sessionTableMock, times(0)).get(sessionId);
        verify(sessionTableMock, times(0)).remove(sessionId);

        Assert.assertEquals(404, response.getStatus());
    }
}
