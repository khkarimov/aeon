package aeon.core.extensions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

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
