package com.ultimatesoftware.aeon.core.extensions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultSessionIdProviderTests {

    private DefaultSessionIdProvider defaultSessionIdProvider;

    @BeforeEach
    void setUp() {
        defaultSessionIdProvider = new DefaultSessionIdProvider();
    }

    @Test
    void testGetCurrentSessionId_ReturnsCurrentThreadId() {

        // Arrange
        long threadId = Thread.currentThread().getId();

        // Act
        String currentThreadId = defaultSessionIdProvider.getCurrentSessionId();

        // Assert
        assertEquals(Long.toString(threadId), currentThreadId);
    }
}
