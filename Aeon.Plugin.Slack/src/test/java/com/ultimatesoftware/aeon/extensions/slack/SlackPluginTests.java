package com.ultimatesoftware.aeon.extensions.slack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pf4j.PluginWrapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SlackPluginTests {

    @Mock
    private PluginWrapper pluginWrapper;

    @Test
    void testConstructorAndGetWrapper() {

        // Arrange

        // Act
        SlackPlugin slackPlugin = new SlackPlugin(this.pluginWrapper);

        // Assert
        assertEquals(this.pluginWrapper, slackPlugin.getWrapper());
    }
}
