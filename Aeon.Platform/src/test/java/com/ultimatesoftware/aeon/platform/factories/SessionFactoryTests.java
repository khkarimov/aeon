package com.ultimatesoftware.aeon.platform.factories;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.command.execution.WebCommandExecutionFacade;
import com.ultimatesoftware.aeon.core.common.Capabilities;
import com.ultimatesoftware.aeon.core.common.exceptions.AeonLaunchException;
import com.ultimatesoftware.aeon.core.common.exceptions.UnableToCreateDriverException;
import com.ultimatesoftware.aeon.core.extensions.IProductTypeExtension;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapterExtension;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IWebAdapter;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.AeonWebDriver;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import com.ultimatesoftware.aeon.platform.session.ISession;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.*;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SessionFactoryTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IWebAdapter adapterMock;
    @Mock
    private Configuration configurationMock;

    @Mock
    private Supplier<List<IAdapterExtension>> adapterSupplierMock;

    @Mock
    private Supplier<List<IProductTypeExtension>> productSupplierMock;

    @Mock
    private IAdapterExtension pluginMock;

    @Mock
    private IProductTypeExtension extensionMock;

    @Mock
    private WebCommandExecutionFacade commandExecutionFacadeMock;

    private SessionFactory sessionFactory;
    private List<IAdapterExtension> adapterExtensions;
    private List<IProductTypeExtension> productExtensions;
    private Map<String, String> settings;

    @Before
    public void setUp() {
        sessionFactory = new SessionFactory(adapterSupplierMock, productSupplierMock);

        settings = new HashMap<>();
        settings.put("aeon.browser", "Firefox");

        adapterExtensions = new ArrayList<>();
        adapterExtensions.add(pluginMock);

        productExtensions = new ArrayList<>();
        productExtensions.add(extensionMock);
    }

    @Test
    public void getSession_requestedCapabilityIsProvided_createsSessionSuccessfully() throws Exception {

        // Arrange
        when(adapterSupplierMock.get()).thenReturn(adapterExtensions);
        when(pluginMock.getProvidedCapability()).thenReturn(Capabilities.WEB);

        when(pluginMock.createAdapter(configurationMock)).thenReturn(adapterMock);

        when(pluginMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getDriver()).thenReturn(AeonWebDriver.class);

        when(productSupplierMock.get()).thenReturn(productExtensions);
        when(extensionMock.createCommandExecutionFacade(any(AutomationInfo.class))).thenReturn(commandExecutionFacadeMock);

        // Act
        ISession session = sessionFactory.getSession(settings);

        // Assert
        verify(adapterSupplierMock, times(1)).get();
        verify(pluginMock, times(1)).getProvidedCapability();

        verify(pluginMock, times(1)).createAdapter(configurationMock);

        verify(pluginMock, times(1)).getConfiguration();
        verify(configurationMock, times(1)).setProperties(any(Properties.class));
        verify(configurationMock, times(1)).getDriver();

        verify(productSupplierMock, times(1)).get();
        verify(extensionMock, times(1)).createCommandExecutionFacade(any(AutomationInfo.class));

        assertNotNull(session);
    }

    @Test
    public void getSession_requestedCapabilityIsNotProvided_throwsException() throws Exception {

        // Arrange
        when(adapterSupplierMock.get()).thenReturn(adapterExtensions);
        when(pluginMock.getProvidedCapability()).thenReturn(Capabilities.IMAGE);

        when(pluginMock.createAdapter(configurationMock)).thenReturn(adapterMock);

        when(pluginMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getDriver()).thenReturn(AeonWebDriver.class);

        when(productSupplierMock.get()).thenReturn(productExtensions);
        when(extensionMock.createCommandExecutionFacade(any(AutomationInfo.class))).thenReturn(commandExecutionFacadeMock);

        // Act
        Executable action = () -> sessionFactory.getSession(settings);

        // Assert
        Exception exception = assertThrows(AeonLaunchException.class, action);
        assertEquals("No valid adapter found. Please check whether at least one matching adapter plugin is installed.", exception.getMessage());
    }


    @Test
    public void getSession_settingsAreNull_createsSessionSuccessfully() throws Exception {

        // Arrange
        when(adapterSupplierMock.get()).thenReturn(adapterExtensions);
        when(pluginMock.getProvidedCapability()).thenReturn(Capabilities.WEB);

        when(pluginMock.createAdapter(configurationMock)).thenReturn(adapterMock);

        when(pluginMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getDriver()).thenReturn(AeonWebDriver.class);

        when(productSupplierMock.get()).thenReturn(productExtensions);
        when(extensionMock.createCommandExecutionFacade(any(AutomationInfo.class))).thenReturn(commandExecutionFacadeMock);

        // Act
        ISession session = sessionFactory.getSession(null);

        // Assert
        verify(adapterSupplierMock, times(1)).get();
        verify(pluginMock, times(1)).getProvidedCapability();

        verify(pluginMock, times(1)).createAdapter(configurationMock);

        verify(pluginMock, times(1)).getConfiguration();
        verify(configurationMock, times(0)).setProperties(null);
        verify(configurationMock, times(1)).getDriver();

        verify(productSupplierMock, times(1)).get();
        verify(extensionMock, times(1)).createCommandExecutionFacade(any(AutomationInfo.class));

        assertNotNull(session);
    }

    @Test
    public void getSession_commandExecutionFacadeUnsuccessful_throwsException() throws Exception {

        // Arrange
        when(adapterSupplierMock.get()).thenReturn(adapterExtensions);
        when(pluginMock.getProvidedCapability()).thenReturn(Capabilities.WEB);

        when(pluginMock.createAdapter(configurationMock)).thenReturn(adapterMock);

        when(pluginMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getDriver()).thenReturn(AeonWebDriver.class);

        when(productSupplierMock.get()).thenReturn(productExtensions);
        when(extensionMock.createCommandExecutionFacade(any(AutomationInfo.class))).thenReturn(null);

        // Act
        Executable action = () -> sessionFactory.getSession(null);

        // Assert
        Exception exception = assertThrows(UnableToCreateDriverException.class, action);
        assertEquals("Unable to create adapter: Could not create CommandExecutionFacade.", exception.getMessage());
    }
}
