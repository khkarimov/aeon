package aeon.core.extensions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class AeonExtensionFactoryTests {

    private AeonExtensionFactory aeonExtensionFactory;

    @Mock
    private ISessionIdProvider sessionIdProvider;

    @BeforeEach
    void setUp() {
        aeonExtensionFactory = new AeonExtensionFactory(this.sessionIdProvider);

        when(this.sessionIdProvider.getCurrentSessionId()).thenReturn("sessionId");
    }

    @Test
    void testCreateSessionIdDoesNotExist() {

        // Arrange

        // Act
        Object extension = aeonExtensionFactory.create(Object.class);

        // Assert
        assertNotNull(extension);
    }

    @Test
    void testCreateSessionIdExistsButInstanceDoesNotExist() {

        // Arrange
        aeonExtensionFactory.create(String.class);

        // Act
        Object extension = aeonExtensionFactory.create(Object.class);

        // Assert
        assertNotNull(extension);
    }

    @Test
    void testCreateSessionIdAndInstanceExist() {

        // Arrange
        Object extension1 = aeonExtensionFactory.create(Object.class);

        // Act
        Object extension2 = aeonExtensionFactory.create(Object.class);

        // Assert
        assertEquals(extension1, extension2);
    }

    @Test
    void testCreateInstanceExistsForDifferentSession() {

        // Arrange
        Object extension1 = aeonExtensionFactory.create(Object.class);
        when(this.sessionIdProvider.getCurrentSessionId()).thenReturn("sessionId2");

        // Act
        Object extension2 = aeonExtensionFactory.create(Object.class);

        // Assert
        assertNotEquals(extension1, extension2);
    }
}
