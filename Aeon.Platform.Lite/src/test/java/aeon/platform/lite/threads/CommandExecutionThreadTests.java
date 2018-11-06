package aeon.platform.lite.threads;

import aeon.core.common.exceptions.CommandExecutionException;
import aeon.platform.lite.models.ResponseBody;
import aeon.platform.session.ISession;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommandExecutionThreadTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private CommandExecutionThread commandExecutionThread;

    private ResponseBody response;
    private ResponseBody nullResponse;
    private ResponseBody exceptionResponse;

    private Throwable e;

    @Mock
    private ISession sessionMock;

    @Mock
    private List<Object> argsMock;

    @Mock
    private Client clientMock;

    @Mock
    private WebTarget webTargetMock;

    @Mock
    private Invocation.Builder invocationBuilderMock;

    @Captor
    ArgumentCaptor<Entity> entityArgumentCaptor = ArgumentCaptor.forClass(Entity.class);

    @Before
    public void setUp() {
        ObjectId sessionId = new ObjectId();
        commandExecutionThread = new CommandExecutionThread(sessionId, sessionMock, "GoToUrlCommand", argsMock, "callbackUrl", clientMock);

        e = new CommandExecutionException("Command is invalid.");

        response = new ResponseBody(sessionId.toString(), true, "Success", null);
        nullResponse = new ResponseBody(sessionId.toString(), true, null, null);
        exceptionResponse = new ResponseBody(sessionId.toString(), false, null, e.getMessage());
    }

    @Test
    public void runTest() throws CommandExecutionException {
        when(sessionMock.executeCommand("GoToUrlCommand", argsMock)).thenReturn("Success");
        when(clientMock.target("callbackUrl")).thenReturn(webTargetMock);
        when(webTargetMock.request(MediaType.APPLICATION_JSON)).thenReturn(invocationBuilderMock);

        commandExecutionThread.run();

        verify(sessionMock, times(1)).executeCommand("GoToUrlCommand", argsMock);
        verify(clientMock, times(1)).target("callbackUrl");
        verify(webTargetMock, times(1)).request(MediaType.APPLICATION_JSON);
        verify(invocationBuilderMock, times(1)).post(entityArgumentCaptor.capture());

        ResponseBody body = (ResponseBody) entityArgumentCaptor.getValue().getEntity();

        Assert.assertEquals(response.getSessionId(), body.getSessionId());
        Assert.assertEquals(response.getSuccess(), body.getSuccess());
        Assert.assertEquals(response.getData(), body.getData());
        Assert.assertEquals(response.getFailureMessage(), body.getFailureMessage());
    }

    @Test
    public void runNullResultTest() throws CommandExecutionException {
        when(sessionMock.executeCommand("GoToUrlCommand", argsMock)).thenReturn(null);
        when(clientMock.target("callbackUrl")).thenReturn(webTargetMock);
        when(webTargetMock.request(MediaType.APPLICATION_JSON)).thenReturn(invocationBuilderMock);

        commandExecutionThread.run();

        verify(sessionMock, times(1)).executeCommand("GoToUrlCommand", argsMock);
        verify(clientMock, times(1)).target("callbackUrl");
        verify(webTargetMock, times(1)).request(MediaType.APPLICATION_JSON);
        verify(invocationBuilderMock, times(1)).post(entityArgumentCaptor.capture());

        ResponseBody body = (ResponseBody) entityArgumentCaptor.getValue().getEntity();

        Assert.assertEquals(nullResponse.getSessionId(), body.getSessionId());
        Assert.assertEquals(nullResponse.getSuccess(), body.getSuccess());
        Assert.assertEquals(nullResponse.getData(), body.getData());
        Assert.assertEquals(nullResponse.getFailureMessage(), body.getFailureMessage());
    }

    @Test
    public void runThrowsExceptionTest() throws CommandExecutionException {
        when(sessionMock.executeCommand("GoToUrlCommand", argsMock)).thenThrow(e);
        when(clientMock.target("callbackUrl")).thenReturn(webTargetMock);
        when(webTargetMock.request(MediaType.APPLICATION_JSON)).thenReturn(invocationBuilderMock);

        commandExecutionThread.run();

        verify(sessionMock, times(1)).executeCommand("GoToUrlCommand", argsMock);
        verify(clientMock, times(1)).target("callbackUrl");
        verify(webTargetMock, times(1)).request(MediaType.APPLICATION_JSON);
        verify(invocationBuilderMock, times(1)).post(entityArgumentCaptor.capture());

        ResponseBody body = (ResponseBody) entityArgumentCaptor.getValue().getEntity();

        Assert.assertEquals(exceptionResponse.getSessionId(), body.getSessionId());
        Assert.assertEquals(exceptionResponse.getSuccess(), body.getSuccess());
        Assert.assertEquals(exceptionResponse.getData(), body.getData());
        Assert.assertEquals(exceptionResponse.getFailureMessage(), body.getFailureMessage());
    }
}
