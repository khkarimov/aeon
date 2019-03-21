package com.ultimatesoftware.aeon.core.testabstraction.product;

import com.ultimatesoftware.aeon.core.extensions.DefaultSessionIdProvider;
import com.ultimatesoftware.aeon.core.extensions.ISessionIdProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class AeonTests {

    @Test
    void testSetAndGetSessionIdProvider() {

        // Arrange
        DefaultSessionIdProvider sessionIdProvider = new DefaultSessionIdProvider();

        // Act
        Aeon.setSessionIdProvider(sessionIdProvider);
        ISessionIdProvider retrievedSessionIdProvider = Aeon.getSessionIdProvider();

        // Assert
        assertEquals(sessionIdProvider, retrievedSessionIdProvider);
    }
}
