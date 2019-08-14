package com.ultimatesoftware.aeon.extensions.perfecto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pf4j.PluginWrapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PerfectoPluginTests {

    @Mock
    private PluginWrapper pluginWrapper;

    @Test
    void testConstructorAndGetWrapper() {

        // Arrange

        // Act
        PerfectoPlugin perfectoPlugin = new PerfectoPlugin(this.pluginWrapper);

        // Assert
        assertEquals(this.pluginWrapper, perfectoPlugin.getWrapper());
    }
}
