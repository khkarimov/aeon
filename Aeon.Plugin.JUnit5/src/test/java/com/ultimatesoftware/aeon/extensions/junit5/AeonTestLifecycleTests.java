package com.ultimatesoftware.aeon.extensions.junit5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class AeonTestLifecycleTests {

    @Mock
    private ExtensionContext extensionContext;

    @Mock
    private ExtensionContext rootExtensionContext;

    @Mock
    private ExtensionContext.Store rootExtensionContextStore;

    private static final String STORE_KEY = "aeon-test-lifecycle";

    private AeonTestLifecycle aeonTestLifecycle;

    @BeforeEach
    public void setup() {
        aeonTestLifecycle = new AeonTestLifecycle();

        when(extensionContext.getRoot()).thenReturn(rootExtensionContext);
        when(rootExtensionContext.getStore(ExtensionContext.Namespace.GLOBAL))
                .thenReturn(rootExtensionContextStore);
    }

    @Test
    public void beforeAll_AeonCloseableResourceIsAddedToRootStore() {

        // Arrange

        // Act
        aeonTestLifecycle.beforeAll(extensionContext);

        // Assert
        verify(rootExtensionContextStore, times(1))
                .put(eq(STORE_KEY), any(AeonTestLifecycle.AeonCloseableResource.class));
    }

    @Test
    public void beforeAll_AeonCloseableResourceIsNotAddedTwice() {

        // Arrange
        when(rootExtensionContextStore.get(STORE_KEY))
                .thenReturn(new AeonTestLifecycle.AeonCloseableResource());

        // Act
        aeonTestLifecycle.beforeAll(extensionContext);

        // Assert
        verify(rootExtensionContextStore, times(0))
                .put(eq(STORE_KEY), any());
    }
}
