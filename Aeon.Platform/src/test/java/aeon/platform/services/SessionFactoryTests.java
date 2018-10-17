package aeon.platform.services;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.Capability;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import aeon.core.framework.abstraction.adapters.IWebAdapter;
import aeon.core.framework.abstraction.drivers.AeonWebDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import aeon.core.testabstraction.product.Configuration;
import aeon.core.testabstraction.product.WebConfiguration;
import aeon.platform.session.ISession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SessionFactoryTests {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule public ExpectedException expectedException = ExpectedException.none();

    private SessionFactory sessionFactory;

    @Mock private Properties settingsMock;
    @Mock private IWebAdapter adapterMock;
    @Mock private IWebDriver driverMock;
    @Mock private Configuration configurationMock;

    @Mock private Supplier<List<IAdapterExtension>> supplierMock;
    @Mock private List<IAdapterExtension> extensionsMock;
    @Mock private IAdapterExtension pluginMock;

    @Mock private AutomationInfo automationInfoMock;

    @Before
    public void setUp() {
        sessionFactory = new SessionFactory(supplierMock);
    }

    @Test
    public void getSessionTest() throws Exception {
        // loadPlugins
        when(supplierMock.get()).thenReturn(extensionsMock);
        when(extensionsMock.size()).thenReturn(1);
        when(extensionsMock.get(0)).thenReturn(pluginMock);
        when(pluginMock.getProvidedCapability()).thenReturn(Capability.WEB);

        // createAdapter
        when(pluginMock.createAdapter(configurationMock)).thenReturn(adapterMock);

        // setUpAutomationInfo
        when(pluginMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getDriver()).thenReturn(AeonWebDriver.class);

        // setUpCommandExecutionFacade
        when(automationInfoMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getDouble(Configuration.Keys.TIMEOUT, 10)).thenReturn(1.0);
        when(configurationMock.getDouble(Configuration.Keys.THROTTLE, 100)).thenReturn(1.0);
        when(configurationMock.getDouble(WebConfiguration.Keys.AJAX_TIMEOUT, 20)).thenReturn(1.0);
        when(automationInfoMock.getDriver()).thenReturn(driverMock);

        ISession session = sessionFactory.getSession(settingsMock);

        // loadPlugins
        verify(supplierMock, times(1)).get();
        verify(extensionsMock, times(1)).size();
        verify(extensionsMock, times(1)).get(0);
        verify(pluginMock, times(1)).getProvidedCapability();

        // createAdapter
        verify(pluginMock, times(1)).createAdapter(configurationMock);

        // setUpAutomationInfo
        verify(pluginMock, times(1)).getConfiguration();
        verify(configurationMock, times(1)).setProperties(settingsMock);
        verify(configurationMock, times(1)).getDriver();

        // setUpCommandExecutionFacade
        verify(configurationMock, times(1)).getDouble(Configuration.Keys.TIMEOUT, 10);
        verify(configurationMock, times(1)).getDouble(Configuration.Keys.THROTTLE, 100);
        verify(configurationMock, times(1)).getDouble(WebConfiguration.Keys.AJAX_TIMEOUT, 20);

        Assert.assertNotNull(session);
    }


    @Test
    public void setUpAutomationInfoNullSettingsTest() throws Exception {
        // loadPlugins
        when(supplierMock.get()).thenReturn(extensionsMock);
        when(extensionsMock.size()).thenReturn(1);
        when(extensionsMock.get(0)).thenReturn(pluginMock);
        when(pluginMock.getProvidedCapability()).thenReturn(Capability.WEB);

        // createAdapter
        when(pluginMock.createAdapter(configurationMock)).thenReturn(adapterMock);

        // setUpAutomationInfo
        when(pluginMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getDriver()).thenReturn(AeonWebDriver.class);

        // setUpCommandExecutionFacade
        when(automationInfoMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getDouble(Configuration.Keys.TIMEOUT, 10)).thenReturn(1.0);
        when(configurationMock.getDouble(Configuration.Keys.THROTTLE, 100)).thenReturn(1.0);
        when(configurationMock.getDouble(WebConfiguration.Keys.AJAX_TIMEOUT, 20)).thenReturn(1.0);
        when(automationInfoMock.getDriver()).thenReturn(driverMock);

        ISession session = sessionFactory.getSession(null);

        // loadPlugins
        verify(supplierMock, times(1)).get();
        verify(extensionsMock, times(1)).size();
        verify(extensionsMock, times(1)).get(0);
        verify(pluginMock, times(1)).getProvidedCapability();

        // createAdapter
        verify(pluginMock, times(1)).createAdapter(configurationMock);

        // setUpAutomationInfo
        verify(pluginMock, times(1)).getConfiguration();
        verify(configurationMock, times(0)).setProperties(null);
        verify(configurationMock, times(1)).getDriver();

        // setUpCommandExecutionFacade
        verify(configurationMock, times(1)).getDouble(Configuration.Keys.TIMEOUT, 10);
        verify(configurationMock, times(1)).getDouble(Configuration.Keys.THROTTLE, 100);
        verify(configurationMock, times(1)).getDouble(WebConfiguration.Keys.AJAX_TIMEOUT, 20);

        Assert.assertNotNull(session);
    }
}
