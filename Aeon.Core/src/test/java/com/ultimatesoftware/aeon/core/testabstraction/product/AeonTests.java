package com.ultimatesoftware.aeon.core.testabstraction.product;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.Capabilities;
import com.ultimatesoftware.aeon.core.common.Capability;
import com.ultimatesoftware.aeon.core.common.exceptions.AeonLaunchException;
import com.ultimatesoftware.aeon.core.common.exceptions.AeonSinglePluginRequestedException;
import com.ultimatesoftware.aeon.core.extensions.DefaultSessionIdProvider;
import com.ultimatesoftware.aeon.core.extensions.ISessionIdProvider;
import com.ultimatesoftware.aeon.core.extensions.ITestExecutionExtension;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapter;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapterExtension;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.pf4j.PluginManager;
import org.slf4j.Logger;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class AeonTests {

    @Mock
    private PluginManager pluginManager;

    @Mock
    private IAdapterExtension adapterExtension1;

    @Mock
    private IAdapterExtension adapterExtension2;

    @Mock
    private ITestExecutionExtension testExecutionExtension1;

    @Mock
    private ITestExecutionExtension testExecutionExtension2;

    @Mock
    private Configuration configuration;

    @Mock
    private IAdapter adapter;

    @Mock
    private Properties properties;

    @Mock
    private Logger log;

    static class ProductWithoutAnnotation extends Product {
        public ProductWithoutAnnotation(AutomationInfo automationInfo) {
            super(automationInfo);
        }
    }

    @Capability(Capabilities.IMAGE)
    static class ProductWithAnnotation extends Product {
        private boolean afterLaunchWasCalled = false;
        private Exception launchFailureException;

        public ProductWithAnnotation(AutomationInfo automationInfo) {
            super(automationInfo);
        }

        @Override
        public void afterLaunch() {
            this.afterLaunchWasCalled = true;
        }

        boolean getAfterLaunchWasCalled() {
            return this.afterLaunchWasCalled;
        }

        @Override
        public void onLaunchFailure(Exception e) {
            this.launchFailureException = e;
        }

        public Exception getLaunchFailureException() {
            return this.launchFailureException;
        }
    }

    @Capability(Capabilities.IMAGE)
    static class ProductWithAfterLaunchFailure extends Product {
        private Exception launchFailureException;

        public ProductWithAfterLaunchFailure(AutomationInfo automationInfo) {
            super(automationInfo);
        }

        @Override
        public void afterLaunch() {
            throw new IllegalStateException("after launch failure");
        }

        @Override
        public void onLaunchFailure(Exception e) {
            this.launchFailureException = e;
        }

        public Exception getLaunchFailureException() {
            return this.launchFailureException;
        }
    }

    static class TestDriver implements IDriver {

        private IAdapter adapter;
        private Configuration configuration;

        @Override
        public IDriver configure(IAdapter adapter, Configuration configuration) {
            this.adapter = adapter;
            this.configuration = configuration;
            return null;
        }

        @Override
        public void quit() {

        }

        @Override
        public String getSource() {
            return null;
        }

        @Override
        public java.awt.Image getScreenshot() {
            return null;
        }

        IAdapter getAdapter() {
            return this.adapter;
        }

        Configuration getConfiguration() {
            return this.configuration;
        }
    }

    @Test
    void launch_productHasNoAnnotatedCapability_throwsException() {

        // Arrange

        // Act
        Executable action = () -> Aeon.launch(ProductWithoutAnnotation.class);

        // Assert
        Exception exception = assertThrows(AeonLaunchException.class, action);
        assertEquals("Product class is not requesting a capability by carrying a 'Capability' annotation", exception.getMessage());
    }

    @Test
    void launch_noPluginProvidesRequestedCapability_throwsException() {

        // Arrange
        when(this.adapterExtension1.getProvidedCapability()).thenReturn(Capabilities.WEB);
        when(this.adapterExtension2.getProvidedCapability()).thenReturn(Capabilities.MOBILE);
        when(this.pluginManager.getExtensions(IAdapterExtension.class))
                .thenReturn(Arrays.asList(this.adapterExtension1, this.adapterExtension2));
        Aeon.setPluginManager(this.pluginManager);

        // Act
        Executable action = () -> Aeon.launch(ProductWithAnnotation.class);

        // Assert
        Exception exception = assertThrows(AeonLaunchException.class, action);
        assertEquals("No valid adapter found. Please check whether at least one matching adapter plugin is installed.", exception.getMessage());
    }

    @Test
    void launch_happyPath_productIsLaunched() throws Exception {

        // Arrange
        when(this.adapterExtension1.getProvidedCapability()).thenReturn(Capabilities.WEB);
        when(this.adapterExtension2.getProvidedCapability()).thenReturn(Capabilities.IMAGE);
        when(this.adapterExtension2.getConfiguration()).thenReturn(this.configuration);
        when(this.adapterExtension2.createAdapter(this.configuration)).thenReturn(this.adapter);
        when(this.configuration.getDriver()).thenReturn(TestDriver.class);
        doReturn(Arrays.asList(this.adapterExtension1, this.adapterExtension2))
                .when(this.pluginManager).getExtensions(IAdapterExtension.class);
        doReturn(Arrays.asList(this.testExecutionExtension1, this.testExecutionExtension2))
                .when(this.pluginManager).getExtensions(ITestExecutionExtension.class);
        Aeon.setPluginManager(this.pluginManager);
        Aeon.log = this.log;

        // Act
        ProductWithAnnotation product = Aeon.launch(ProductWithAnnotation.class);

        // Assert
        assertEquals(this.adapter, ((TestDriver) product.getAutomationInfo().getDriver()).getAdapter());
        assertEquals(this.configuration, ((TestDriver) product.getAutomationInfo().getDriver()).getConfiguration());
        verify(this.testExecutionExtension1, times(1)).onBeforeLaunch(this.configuration);
        verify(this.testExecutionExtension2, times(1)).onBeforeLaunch(this.configuration);
        verify(this.log, times(1)).info("Launching product");
        assertTrue(product.getAfterLaunchWasCalled());
    }

    @Test
    void launch_settingsAreProvided_productIsLaunchedWithAdditionalSettings() throws Exception {

        // Arrange
        when(this.adapterExtension1.getProvidedCapability()).thenReturn(Capabilities.WEB);
        when(this.adapterExtension2.getProvidedCapability()).thenReturn(Capabilities.IMAGE);
        when(this.adapterExtension2.getConfiguration()).thenReturn(this.configuration);
        when(this.adapterExtension2.createAdapter(this.configuration)).thenReturn(this.adapter);
        when(this.configuration.getDriver()).thenReturn(TestDriver.class);
        doReturn(Arrays.asList(this.adapterExtension1, this.adapterExtension2))
                .when(this.pluginManager).getExtensions(IAdapterExtension.class);
        doReturn(Arrays.asList(this.testExecutionExtension1, this.testExecutionExtension2))
                .when(this.pluginManager).getExtensions(ITestExecutionExtension.class);
        Aeon.setPluginManager(this.pluginManager);
        Aeon.log = this.log;

        // Act
        ProductWithAnnotation product = Aeon.launch(ProductWithAnnotation.class, this.properties);

        // Assert
        assertEquals(this.adapter, ((TestDriver) product.getAutomationInfo().getDriver()).getAdapter());
        assertEquals(this.configuration, ((TestDriver) product.getAutomationInfo().getDriver()).getConfiguration());
        verify(this.configuration, times(1)).setProperties(this.properties);
        verify(this.testExecutionExtension1, times(1)).onBeforeLaunch(this.configuration);
        verify(this.testExecutionExtension2, times(1)).onBeforeLaunch(this.configuration);
        verify(this.log, times(1)).info("Launching product");
        assertTrue(product.getAfterLaunchWasCalled());
    }

    @Test
    void launch_launchingFailsBeforeProductIsInstantiated_throwsException() throws Exception {

        // Arrange
        when(this.adapterExtension1.getProvidedCapability()).thenReturn(Capabilities.WEB);
        when(this.adapterExtension2.getProvidedCapability()).thenReturn(Capabilities.IMAGE);
        when(this.adapterExtension2.getConfiguration()).thenThrow(new IllegalArgumentException("error message"));
        doReturn(Arrays.asList(this.adapterExtension1, this.adapterExtension2))
                .when(this.pluginManager).getExtensions(IAdapterExtension.class);
        Aeon.setPluginManager(this.pluginManager);
        Aeon.log = this.log;

        // Act
        Executable action = () -> Aeon.launch(ProductWithAnnotation.class);

        // Assert
        Exception exception = assertThrows(AeonLaunchException.class, action);
        assertEquals("java.lang.IllegalArgumentException: error message", exception.getMessage());
    }

    @Test
    void launch_launchingFailsBeforeProductIsInstantiated_throwsExceptionAndCallsOnLaunchFailure() throws Exception {

        // Arrange
        when(this.adapterExtension1.getProvidedCapability()).thenReturn(Capabilities.WEB);
        when(this.adapterExtension2.getProvidedCapability()).thenReturn(Capabilities.IMAGE);
        when(this.adapterExtension2.getConfiguration()).thenReturn(this.configuration);
        when(this.configuration.getDriver()).thenReturn(TestDriver.class);
        doReturn(Arrays.asList(this.adapterExtension1, this.adapterExtension2))
                .when(this.pluginManager).getExtensions(IAdapterExtension.class);
        Aeon.setPluginManager(this.pluginManager);
        Aeon.log = this.log;

        // Act
        Executable action = () -> Aeon.launch(ProductWithAfterLaunchFailure.class);

        // Assert
        Exception exception = assertThrows(AeonLaunchException.class, action);
        assertEquals("java.lang.IllegalStateException: after launch failure", exception.getMessage());
    }

    @Test
    void done_multipleExtensionsAvailable_callsOnDoneOnExtensions() {

        // Arrange
        when(this.pluginManager.getExtensions(ITestExecutionExtension.class))
                .thenReturn(Arrays.asList(this.testExecutionExtension1, this.testExecutionExtension2));
        Aeon.setPluginManager(this.pluginManager);

        // Act
        Aeon.done();

        // Assert
        verify(this.testExecutionExtension1, times(1)).onDone();
        verify(this.testExecutionExtension2, times(1)).onDone();
    }

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
        when(this.pluginManager.getExtensions(IAdapterExtension.class))
                .thenReturn(Arrays.asList(this.adapterExtension1, this.adapterExtension2));

        // Act
        Aeon.setPluginManager(this.pluginManager);

        // Assert
        List<IAdapterExtension> extensions = Aeon.getExtensions(IAdapterExtension.class);
        assertEquals(2, extensions.size());
        assertEquals(this.adapterExtension1, extensions.get(0));
        assertEquals(this.adapterExtension2, extensions.get(1));
    }

    @Test
    void testGetExtension_zeroExtensionsAvailable_throwsException() {

        // Arrange
        when(this.pluginManager.getExtensions(ITestExecutionExtension.class))
                .thenReturn(new ArrayList<>());
        Aeon.setPluginManager(this.pluginManager);

        // Act
        Executable action = () -> Aeon.getExtension(ITestExecutionExtension.class);

        // Assert
        Exception thrownException = assertThrows(AeonSinglePluginRequestedException.class, action);
        assertEquals("Single extension for \"ITestExecutionExtension\" requested, but found 0.", thrownException.getMessage());
    }

    @Test
    void testGetExtension_multipleExtensionsAvailable_throwsException() {

        // Arrange
        when(this.pluginManager.getExtensions(ITestExecutionExtension.class))
                .thenReturn(Arrays.asList(this.testExecutionExtension1, this.testExecutionExtension2));
        Aeon.setPluginManager(this.pluginManager);

        // Act
        Executable action = () -> Aeon.getExtension(ITestExecutionExtension.class);

        // Assert
        Exception thrownException = assertThrows(AeonSinglePluginRequestedException.class, action);
        assertEquals("Single extension for \"ITestExecutionExtension\" requested, but found 2.", thrownException.getMessage());
    }

    @Test
    void testGetExtension_oneExtensionsAvailable_returnsExtension() {

        // Arrange
        when(this.pluginManager.getExtensions(IAdapterExtension.class))
                .thenReturn(Collections.singletonList(this.adapterExtension2));
        Aeon.setPluginManager(this.pluginManager);

        // Act
        IAdapterExtension extension = Aeon.getExtension(IAdapterExtension.class);

        // Assert
        assertEquals(this.adapterExtension2, extension);
    }
}
