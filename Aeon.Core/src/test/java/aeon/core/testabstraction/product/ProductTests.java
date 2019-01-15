package aeon.core.testabstraction.product;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import aeon.core.framework.abstraction.drivers.AeonWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ProductTests {

    @Mock
    private AutomationInfo automationInfo;

    @Mock
    private Configuration configuration;

    @Mock
    private IAdapterExtension plugin;

    private Product product;

    private String key = null;

    @BeforeEach
    public void setUp() {
        product = mock(Product.class, Mockito.CALLS_REAL_METHODS);
    }

    @Test
    public void Product_noArguments_instantiateClass() {

        //Arrange

        //Act
        product = mock(Product.class, Mockito.withSettings().useConstructor());

        //Assert
        assertNotNull(product);
    }

    @Test
    public void Product_withArguments_instantiateClassWithParameters() {

        //Arrange

        //Act
        product = mock(Product.class, Mockito.withSettings().useConstructor(automationInfo));

        //Assert
        assertNotNull(product);
        assertEquals(product.automationInfo, automationInfo);
    }

    @Test
    public void getAutomationInfo_returnsAutomationInfoCorrectly() {

        //Arrange

        //Act
        product.automationInfo = automationInfo;

        //Assert
        assertEquals(product.getAutomationInfo(), automationInfo);
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

        //Act
        product.configuration = configuration;

        //Assert
        assertEquals(product.getConfiguration(), configuration);
    }

    @Test
    public void Launch_verifyCreateAdapterCall() throws Exception {

        // Arrange
        product.setConfiguration(configuration);
        when(configuration.getDriver()).thenReturn(AeonWebDriver.class);

        // Act
        product.launch(plugin);

        // Assert
        verify(plugin, times(1)).createAdapter(configuration);
    }

    @Test
    public void Launch_verifyAfterLaunchCall() throws Exception {

        // Arrange
        product.setConfiguration(configuration);
        when(configuration.getDriver()).thenReturn(AeonWebDriver.class);

        // Act
        product.launch(plugin);

        // Assert
        verify(product, times(1)).afterLaunch();
    }

    @Test
    public void Launch_automationInfoChange_newAutomationInfo() throws Exception {

        // Arrange
        product.setConfiguration(configuration);
        when(configuration.getDriver()).thenReturn(AeonWebDriver.class);

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
        when(configuration.getString(key, key)).thenReturn("Test Passed");

        // Act
        String call = product.getConfig(key, key);

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
