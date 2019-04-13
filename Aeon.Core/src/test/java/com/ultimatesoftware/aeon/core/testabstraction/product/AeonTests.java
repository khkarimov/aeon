package com.ultimatesoftware.aeon.core.testabstraction.product;

import com.ultimatesoftware.aeon.core.extensions.DefaultSessionIdProvider;
import com.ultimatesoftware.aeon.core.extensions.ISessionIdProvider;
import com.ultimatesoftware.aeon.core.extensions.ITestExecutionExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.pf4j.PluginManager;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class AeonTests {

    @Mock
    private PluginManager pluginManager;

    @Mock
    private ITestExecutionExtension extension1;

    @Mock
    private ITestExecutionExtension extension2;

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

    @Test
    void testSetAndGetPluginManager() {

        // Arrange
        when(this.pluginManager.getExtensions(ITestExecutionExtension.class))
                .thenReturn(Arrays.asList(this.extension1, this.extension2));

        // Act
        Aeon.setPluginManager(this.pluginManager);

        // Assert
        List<ITestExecutionExtension> extensions = Aeon.getExtensions(ITestExecutionExtension.class);
        assertEquals(2, extensions.size());
        assertEquals(this.extension1, extensions.get(0));
        assertEquals(this.extension2, extensions.get(1));
    }
}
