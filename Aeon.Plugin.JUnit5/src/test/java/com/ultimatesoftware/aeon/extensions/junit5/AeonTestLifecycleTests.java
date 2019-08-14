package com.ultimatesoftware.aeon.extensions.junit5;

import com.ultimatesoftware.aeon.core.extensions.ITestExecutionExtension;
import com.ultimatesoftware.aeon.core.testabstraction.product.Aeon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.pf4j.PluginManager;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class AeonTestLifecycleTests {

    @Mock
    private ExtensionContext extensionContext;

    @Mock
    private ExtensionContext rootExtensionContext;

    @Mock
    private ExtensionContext parentExtensionContext;

    @Mock
    private ExtensionContext.Store rootExtensionContextStore;

    @Mock
    private PluginManager pluginManager;

    @Mock
    private ITestExecutionExtension testExecutionExtension1;

    @Mock
    private ITestExecutionExtension testExecutionExtension2;

    @Captor
    private ArgumentCaptor<Function<String, AeonTestLifecycle.AeonCloseableResource>> functionCaptor;

    private static final String STORE_KEY = "aeon-test-lifecycle";

    private AeonTestLifecycle aeonTestLifecycle;

    @BeforeEach
    void setup() {
        this.aeonTestLifecycle = new AeonTestLifecycle();

        when(this.pluginManager.getExtensions(ITestExecutionExtension.class))
                .thenReturn(Arrays.asList(this.testExecutionExtension1, this.testExecutionExtension2));
        Aeon.setPluginManager(this.pluginManager);
    }

    @Test
    void beforeAll_AeonCloseableResourceIsAddedToRootStore() {

        // Arrange
        when(this.extensionContext.getRoot()).thenReturn(this.rootExtensionContext);
        when(this.rootExtensionContext.getStore(ExtensionContext.Namespace.GLOBAL))
                .thenReturn(this.rootExtensionContextStore);

        // Act
        this.aeonTestLifecycle.beforeAll(this.extensionContext);

        // Assert
        verify(this.rootExtensionContextStore, times(1))
                .getOrComputeIfAbsent(eq(STORE_KEY), this.functionCaptor.capture());
        assertNotNull(this.functionCaptor.getValue().apply("key"));
    }

    @Test
    void beforeEach_isCalledWithNoParent_retrievesTheNameOfTheTestAndTriggersTheOnBeforeTestEvent() {

        // Arrange
        when(this.extensionContext.getDisplayName()).thenReturn("test_name");
        when(this.parentExtensionContext.getDisplayName()).thenReturn("class_name");
        Optional<ExtensionContext> parent = Optional.of(this.parentExtensionContext);
        when(this.extensionContext.getParent()).thenReturn(parent);

        // Act
        this.aeonTestLifecycle.beforeEach(this.extensionContext);

        // Assert
        verify(this.testExecutionExtension1, times(1)).onBeforeTest("class_name.test_name");
        verify(this.testExecutionExtension2, times(1)).onBeforeTest("class_name.test_name");
    }

    @Test
    void beforeEach_isCalledWithParent_retrievesTheNameOfTheTestAndParentAndTriggersTheOnBeforeTestEvent() {

        // Arrange
        when(this.extensionContext.getDisplayName()).thenReturn("test_name");
        Optional<ExtensionContext> parent = Optional.empty();
        when(this.extensionContext.getParent()).thenReturn(parent);

        // Act
        this.aeonTestLifecycle.beforeEach(this.extensionContext);

        // Assert
        verify(this.testExecutionExtension1, times(1)).onBeforeTest("test_name");
        verify(this.testExecutionExtension2, times(1)).onBeforeTest("test_name");
    }

    @Test
    void beforeEach_isCalledForFailedTest_triggersTheOnFailedTestEvent() {

        // Arrange
        Exception exception = new Exception("exception-message");
        Optional<Throwable> optionalThrowable = Optional.of(exception);
        when(this.extensionContext.getExecutionException()).thenReturn(optionalThrowable);

        // Act
        this.aeonTestLifecycle.afterEach(this.extensionContext);

        // Assert
        verify(this.testExecutionExtension1, times(1)).onFailedTest("exception-message", exception);
        verify(this.testExecutionExtension2, times(1)).onFailedTest("exception-message", exception);
    }

    @Test
    void beforeEach_isCalledForSucceededTest_triggersTheOnSucceededTestEvent() {

        // Arrange
        Optional<Throwable> optionalThrowable = Optional.empty();
        when(this.extensionContext.getExecutionException()).thenReturn(optionalThrowable);

        // Act
        this.aeonTestLifecycle.afterEach(this.extensionContext);

        // Assert
        verify(this.testExecutionExtension1, times(1)).onSucceededTest();
        verify(this.testExecutionExtension2, times(1)).onSucceededTest();
    }

    @Test
    void close_isCalled_triggersTheOnDoneEvent() {

        // Arrange
        AeonTestLifecycle.AeonCloseableResource resource = new AeonTestLifecycle.AeonCloseableResource();

        // Act
        resource.close();

        // Assert
        verify(this.testExecutionExtension1, times(1)).onDone();
        verify(this.testExecutionExtension2, times(1)).onDone();
    }
}
