package com.ultimatesoftware.aeon.platform.http;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionIdProviderTests {

    private HttpSessionIdProvider httpSessionIdProvider;

    @Before
    public void setUp() {
        this.httpSessionIdProvider = new HttpSessionIdProvider();
    }

    @Test
    public void testSetAndGetCurrentSessionId() {

        // Arrange
        String sessionId = "sessionId";

        // Act
        this.httpSessionIdProvider.setCurrentSessionId(sessionId);
        String currentSessionId = this.httpSessionIdProvider.getCurrentSessionId();

        // Assert
        assertEquals(sessionId, currentSessionId);
    }
}
