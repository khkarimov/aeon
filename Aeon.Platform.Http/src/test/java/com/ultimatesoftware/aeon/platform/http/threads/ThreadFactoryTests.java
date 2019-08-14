package com.ultimatesoftware.aeon.platform.http.threads;

import com.ultimatesoftware.aeon.platform.http.HttpSessionIdProvider;
import com.ultimatesoftware.aeon.platform.session.ISession;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ThreadFactoryTests {

    @Mock
    private ISession sessionMock;

    @Mock
    private List<Object> argsMock;

    @Mock
    private HttpSessionIdProvider sessionIdProvider;

    @Test
    public void getCommandExecutionThread_returnsThread() {
        // Arrange
        ThreadFactory threadFactory = new ThreadFactory();
        ObjectId sessionId = new ObjectId();

        // Act
        Object thread = threadFactory.getCommandExecutionThread(
                sessionId,
                sessionMock,
                "GoToUrlCommand",
                argsMock,
                "callbackUrl",
                sessionIdProvider
        );

        // Assert
        assertNotNull(thread);
    }
}
