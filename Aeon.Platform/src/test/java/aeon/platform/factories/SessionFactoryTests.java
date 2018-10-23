package aeon.platform.factories;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.command.execution.WebCommandExecutionFacade;
import aeon.core.common.Capability;
import aeon.core.common.exceptions.UnableToCreateDriverException;
import aeon.core.extensions.IProductTypeExtension;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import aeon.core.framework.abstraction.adapters.IWebAdapter;
import aeon.core.framework.abstraction.drivers.AeonWebDriver;
import aeon.core.testabstraction.product.Configuration;
import aeon.platform.session.ISession;
import net.bytebuddy.asm.Advice;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.*;
import java.util.function.Supplier;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SessionFactoryTests {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule public ExpectedException expectedException = ExpectedException.none();

    private SessionFactory sessionFactory;
    private List<IAdapterExtension> adapterExtensions;
    private List<IProductTypeExtension> productExtensions;
    private Map settings;

    @Mock private IWebAdapter adapterMock;
    @Mock private Configuration configurationMock;

    @Mock private Supplier<List<IAdapterExtension>> adapterSupplierMock;
    @Mock private Supplier<List<IProductTypeExtension>> productSupplierMock;
    @Mock private IAdapterExtension pluginMock;
    @Mock private IProductTypeExtension extensionMock;

    @Mock private AutomationInfo automationInfoMock;
    @Mock private WebCommandExecutionFacade commandExecutionFacadeMock;

    @Before
    public void setUp() {
        sessionFactory = new SessionFactory(adapterSupplierMock, productSupplierMock);

        settings = new HashMap();
        settings.put("aeon.browser", "Firefox");

        adapterExtensions = new ArrayList<>();
        adapterExtensions.add(pluginMock);

        productExtensions = new ArrayList<>();
        productExtensions.add(extensionMock);
    }

    @Test
    public void getSessionTest() throws Exception {
        // loadPlugins
        when(adapterSupplierMock.get()).thenReturn(adapterExtensions);
        when(pluginMock.getProvidedCapability()).thenReturn(Capability.WEB);

        // createAdapter
        when(pluginMock.createAdapter(configurationMock)).thenReturn(adapterMock);

        // setUpAutomationInfo
        when(pluginMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getDriver()).thenReturn(AeonWebDriver.class);

        // setUpCommandExecutionFacade
        when(productSupplierMock.get()).thenReturn(productExtensions);
        when(extensionMock.createCommandExecutionFacade(any(AutomationInfo.class))).thenReturn(commandExecutionFacadeMock);

        ISession session = sessionFactory.getSession(settings);

        // loadPlugins
        verify(adapterSupplierMock, times(1)).get();
        verify(pluginMock, times(1)).getProvidedCapability();

        // createAdapter
        verify(pluginMock, times(1)).createAdapter(configurationMock);

        // setUpAutomationInfo
        verify(pluginMock, times(1)).getConfiguration();
        verify(configurationMock, times(1)).setProperties(any(Properties.class));
        verify(configurationMock, times(1)).getDriver();

        // setUpCommandExecutionFacade
        verify(productSupplierMock, times(1)).get();
        verify(extensionMock, times(1)).createCommandExecutionFacade(any(AutomationInfo.class));

        Assert.assertNotNull(session);
    }


    @Test
    public void getSessionNullSettingsTest() throws Exception {
        // loadPlugins
        when(adapterSupplierMock.get()).thenReturn(adapterExtensions);
        when(pluginMock.getProvidedCapability()).thenReturn(Capability.WEB);

        // createAdapter
        when(pluginMock.createAdapter(configurationMock)).thenReturn(adapterMock);

        // setUpAutomationInfo
        when(pluginMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getDriver()).thenReturn(AeonWebDriver.class);

        // setUpCommandExecutionFacade
        when(productSupplierMock.get()).thenReturn(productExtensions);
        when(extensionMock.createCommandExecutionFacade(any(AutomationInfo.class))).thenReturn(commandExecutionFacadeMock);

        ISession session = sessionFactory.getSession(null);

        // loadPlugins
        verify(adapterSupplierMock, times(1)).get();
        verify(pluginMock, times(1)).getProvidedCapability();

        // createAdapter
        verify(pluginMock, times(1)).createAdapter(configurationMock);

        // setUpAutomationInfo
        verify(pluginMock, times(1)).getConfiguration();
        verify(configurationMock, times(0)).setProperties(null);
        verify(configurationMock, times(1)).getDriver();

        // setUpCommandExecutionFacade
        verify(productSupplierMock, times(1)).get();
        verify(extensionMock, times(1)).createCommandExecutionFacade(any(AutomationInfo.class));

        Assert.assertNotNull(session);
    }

    @Test
    public void getSessionExceptionTest() throws Exception {
        // loadPlugins
        when(adapterSupplierMock.get()).thenReturn(adapterExtensions);
        when(pluginMock.getProvidedCapability()).thenReturn(Capability.WEB);

        // createAdapter
        when(pluginMock.createAdapter(configurationMock)).thenReturn(adapterMock);

        // setUpAutomationInfo
        when(pluginMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getDriver()).thenReturn(AeonWebDriver.class);

        // setUpCommandExecutionFacade
        when(productSupplierMock.get()).thenReturn(productExtensions);
        when(extensionMock.createCommandExecutionFacade(any(AutomationInfo.class))).thenReturn(null);

        Assertions.assertThrows(UnableToCreateDriverException.class, () -> sessionFactory.getSession(null));
    }
}
