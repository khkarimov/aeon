package com.ultimatesoftware.aeon.extensions.continuum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pf4j.PluginWrapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ContinuumPluginTests {

    @Mock
    private PluginWrapper pluginWrapper;

    @Test
    void testConstructorAndGetWrapper() {

        // Arrange

        // Act
        ContinuumPlugin continuumPlugin = new ContinuumPlugin(this.pluginWrapper);

        // Assert
        assertEquals(this.pluginWrapper, continuumPlugin.getWrapper());
    }
}
