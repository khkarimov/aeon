package com.ultimatesoftware.aeon.extensions.accessibility;

import com.ultimatesoftware.aeon.core.testabstraction.product.Aeon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pf4j.PluginManager;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccessibilityControllerExtensionTests {

    private AccessibilityControllerExtension accessibilityControllerExtension;

    @Mock
    private PluginManager pluginManager;

    @Mock
    private IAccessibilityExtension accessibilityExtension1;

    @Mock
    private IAccessibilityExtension accessibilityExtension2;

    @BeforeEach
    void setup() {
        this.accessibilityControllerExtension = new AccessibilityControllerExtension();

        Aeon.setPluginManager(this.pluginManager);
    }

    @Test
    void runAccessibilityTests_callsExtensions() {

        // Arrange
        doReturn(Arrays.asList(this.accessibilityExtension1, this.accessibilityExtension2))
                .when(this.pluginManager).getExtensions(IAccessibilityExtension.class);

        // Act
        this.accessibilityControllerExtension.runAccessibilityTests("pageName");

        // Assert
        verify(this.accessibilityExtension1, times(1)).runAccessibilityTests("pageName");
        verify(this.accessibilityExtension2, times(1)).runAccessibilityTests("pageName");
    }
}
