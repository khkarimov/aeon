package aeon.platform.http.threads;

import aeon.core.common.exceptions.CommandExecutionException;
import aeon.platform.http.models.ResponseBody;
import aeon.platform.session.ISession;
import com.rabbitmq.client.Channel;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommandThreadTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private CommandThread commandThread;
    private ObjectId sessionId;

    private ResponseBody response;
    private ResponseBody nullResponse;
    private ResponseBody exceptionResponse;

    private Throwable e;

    @Mock
    ISession sessionMock;
    @Mock
    List<Object> argsMock;
    @Mock
    Channel channelMock;

    @Before
    public void setUp() {
        sessionId = new ObjectId();
        commandThread = new CommandThread(sessionId, sessionMock, "GoToUrlCommand", argsMock, channelMock);

        e = new CommandExecutionException("Command is invalid.");

        response = new ResponseBody(sessionId.toString(), true, "Success", null);
        nullResponse = new ResponseBody(sessionId.toString(), true, null, null);
        exceptionResponse = new ResponseBody(sessionId.toString(), false, null, e.getMessage());
    }

    @Test
    public void runTest() throws IOException, CommandExecutionException {
        when(sessionMock.executeCommand("GoToUrlCommand", argsMock)).thenReturn("Success");

        commandThread.run();

        verify(sessionMock, times(1)).executeCommand("GoToUrlCommand", argsMock);
        verify(channelMock, times(1)).basicPublish("", "AeonApp", null, response.toString().getBytes());
    }

    @Test
    public void runNullResultTest() throws IOException, CommandExecutionException {
        when(sessionMock.executeCommand("GoToUrlCommand", argsMock)).thenReturn(null);

        commandThread.run();

        verify(sessionMock, times(1)).executeCommand("GoToUrlCommand", argsMock);
        verify(channelMock, times(1)).basicPublish("", "AeonApp", null, nullResponse.toString().getBytes());
    }

    @Test
    public void runThrowsExceptionTest() throws IOException, CommandExecutionException {
        when(sessionMock.executeCommand("GoToUrlCommand", argsMock)).thenThrow(e);

        commandThread.run();

        verify(sessionMock, times(1)).executeCommand("GoToUrlCommand", argsMock);
        verify(channelMock, times(1)).basicPublish("", "AeonApp", null, exceptionResponse.toString().getBytes());
    }
}
