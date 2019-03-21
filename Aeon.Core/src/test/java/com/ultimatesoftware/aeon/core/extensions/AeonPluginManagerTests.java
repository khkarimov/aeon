package com.ultimatesoftware.aeon.core.extensions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.pf4j.ExtensionFactory;

import static org.junit.Assert.assertTrue;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class AeonPluginManagerTests {

    private AeonPluginManager aeonPluginManager;

    @Mock
    private ISessionIdProvider sessionIdProvider;

    @BeforeEach
    void setUp() {
        aeonPluginManager = new AeonPluginManager(this.sessionIdProvider);
    }

    @Test
    void testCreateExtensionFactory_ReturnsAeonExtensionFactory() {

        // Arrange

        // Act
        ExtensionFactory extensionFactory = aeonPluginManager.createExtensionFactory();

        // Assert
        assertTrue(extensionFactory instanceof AeonExtensionFactory);
    }
}
