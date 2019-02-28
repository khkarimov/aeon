package aeon.core.testabstraction.product;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.AeonConfigKey;
import aeon.core.common.Capability;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import aeon.core.framework.abstraction.drivers.IDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ProductTests {

    private class ProductChild extends Product {
        @Override
        public Capability getRequestedCapability() {
            return null;
        }
    }

    @Mock
    private IDriver driver;

    @Mock
    private AutomationInfo automationInfo;

    @Mock
    private Configuration configuration;

    @Mock
    private IAdapterExtension plugin;

    private Product product;

    private Product spyproduct;

    private AeonConfigKey key = Configuration.Keys.TIMEOUT;

    @BeforeEach
    public void setUp() {
        product = new ProductChild();
    }

    @Test
    public void getAutomationInfo_returnsAutomationInfoCorrectly() {

        //Arrange
        product.automationInfo = automationInfo;

        //Act
        AutomationInfo currentAutomationInfo = product.getAutomationInfo();

        //Assert
        assertEquals(currentAutomationInfo, automationInfo);
    }

    @Test
    public void setAutomationInfo_setsAutomationInfoCorrectly() {

        //Arrange

        //Act
        product.setAutomationInfo(automationInfo);

        //Assert
        assertEquals(product.automationInfo, automationInfo);
    }


    @Test
    public void setConfiguration_setsConfigurationCorrectly() {

        //Arrange

        //Act
        product.setConfiguration(configuration);

        //Assert
        assertEquals(product.configuration, configuration);
    }

    @Test
    public void getConfiguration_returnsConfigurationCorrectly() {

        //Arrange
        product.configuration = configuration;

        //Act
        Configuration currentConfiguration = product.getConfiguration();

        //Assert
        assertEquals(currentConfiguration, configuration);
    }

    @Test
    public void Launch_verifyCreateAdapterCall() throws Exception {

        // Arrange
        product.setConfiguration(configuration);
        when(configuration.getDriver()).thenReturn(driver.getClass());

        // Act
        product.launch(plugin);

        // Assert
        verify(plugin, times(1)).createAdapter(configuration);
    }

    @Test
    public void Launch_verifyAfterLaunchCall() throws Exception {

        // Arrange
        spyproduct = org.mockito.Mockito.spy(product);
        spyproduct.setConfiguration(configuration);
        when(configuration.getDriver()).thenReturn(driver.getClass());

        // Act
        spyproduct.launch(plugin);

        // Assert
        verify(spyproduct, times(1)).afterLaunch();
    }

    @Test
    public void Launch_automationInfoChange_newAutomationInfo() throws Exception {

        // Arrange
        product.setConfiguration(configuration);
        when(configuration.getDriver()).thenReturn(driver.getClass());

        // Act
        product.launch(plugin);

        // Assert
        assertNotEquals(automationInfo, product.getAutomationInfo());
    }

    @Test
    public void AfterLaunch_launchedIsCalled() {

        // Arrange
        product.setAutomationInfo(automationInfo);

        // Act
        product.afterLaunch();

        //Assert
        verify(automationInfo, times(1)).launched();
    }

    @Test
    public void getConfig_booleanArgument_returnsBoolean() {

        // Arrange
        boolean defaultValue = false;
        product.setConfiguration(configuration);
        when(configuration.getBoolean(key, defaultValue)).thenReturn(true);

        // Act
        boolean called = product.getConfig(key, defaultValue);

        //Assert
        assertTrue(called);
    }

    @Test
    public void getConfig_stringArgument_returnsString() {

        // Arrange
        product.setConfiguration(configuration);
        when(configuration.getString(key, "")).thenReturn("Test Passed");

        // Act
        String call = product.getConfig(key, "");

        // Assert
        assertEquals(call, "Test Passed");
    }

    @Test
    public void getConfig_doubleArgument_returnsDouble() {

        // Arrange
        double testSuccess = 5;
        double defaultValue = 0;
        product.setConfiguration(configuration);
        when(configuration.getDouble(key, defaultValue)).thenReturn(testSuccess);

        // Act
        double testValue = product.getConfig(key, defaultValue);

        // Assert
        assertEquals(testValue, testSuccess);
    }
}
