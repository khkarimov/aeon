package aeon.platform.http;

import aeon.platform.session.ISession;
import aeon.platform.controllers.http.HttpSessionController;
import aeon.platform.models.CreateSessionBody;
import aeon.platform.models.ExecuteCommandBody;
import aeon.platform.services.SessionFactory;
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

import java.util.*;

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

    @Mock private CreateSessionBody createSessionBodyMock;
    @Mock private ExecuteCommandBody executeCommandBodyMock;

    @Mock private SessionFactory sessionFactoryMock;

    @Before
    public void setUp() {
        httpSessionController = new HttpSessionController(sessionFactoryMock);
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
    public void executeCommandTest() throws Exception {
        when(sessionTableMock.containsKey(sessionId)).thenReturn(true);
        when(sessionTableMock.get(sessionId)).thenReturn(sessionMock);
        when(sessionMock.executeCommand(executeCommandBodyMock)).thenReturn("GoToUrlCommand Successful");

        ResponseEntity response = httpSessionController.executeCommand(sessionId, executeCommandBodyMock);

        verify(sessionTableMock, times(1)).containsKey(sessionId);
        verify(sessionTableMock, times(1)).get(sessionId);
        verify(sessionTableMock, times(0)).remove(sessionId);
        verify(sessionMock, times(1)).executeCommand(executeCommandBodyMock);

        Assert.assertEquals("GoToUrlCommand Successful", response.getBody());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void executeNullCommandTest() throws Exception {
        when(sessionTableMock.containsKey(sessionId)).thenReturn(true);
        when(sessionTableMock.get(sessionId)).thenReturn(sessionMock);
        when(sessionMock.executeCommand(executeCommandBodyMock)).thenThrow(new Exception());

        ResponseEntity response = httpSessionController.executeCommand(sessionId, executeCommandBodyMock);

        verify(sessionTableMock, times(1)).containsKey(sessionId);
        verify(sessionTableMock, times(1)).get(sessionId);
        verify(sessionTableMock, times(0)).remove(sessionId);
        verify(sessionMock, times(1)).executeCommand(executeCommandBodyMock);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void executeQuitCommandTest() throws Exception {
        when(sessionTableMock.containsKey(sessionId)).thenReturn(true);
        when(sessionTableMock.get(sessionId)).thenReturn(sessionMock);
        when(sessionMock.executeCommand(executeCommandBodyMock)).thenReturn(null);

        ResponseEntity response = httpSessionController.executeCommand(sessionId, executeCommandBodyMock);

        verify(sessionTableMock, times(1)).containsKey(sessionId);
        verify(sessionTableMock, times(1)).get(sessionId);
        verify(sessionMock, times(1)).executeCommand(executeCommandBodyMock);

        Assert.assertNull(response.getBody());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void executeCommandSessionNotFoundTest() {
        when(sessionTableMock.containsKey(sessionId)).thenReturn(false);

        ResponseEntity response = httpSessionController.executeCommand(sessionId, executeCommandBodyMock);

        verify(sessionTableMock, times(1)).containsKey(sessionId);
        verify(sessionTableMock, times(0)).get(sessionId);
        verify(executeCommandBodyMock, times(0)).getCommand();
        verify(sessionTableMock, times(0)).remove(sessionId);

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void quitSessionTest() {
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
    public void quitSessionSessionNotFoundTest() {
        when(sessionTableMock.containsKey(sessionId)).thenReturn(false);

        ResponseEntity response = httpSessionController.quitSession(sessionId);

        verify(sessionTableMock, times(1)).containsKey(sessionId);
        verify(sessionTableMock, times(0)).get(sessionId);
        verify(sessionTableMock, times(0)).remove(sessionId);

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
