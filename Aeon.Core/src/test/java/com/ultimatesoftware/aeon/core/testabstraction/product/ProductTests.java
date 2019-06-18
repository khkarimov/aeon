package com.ultimatesoftware.aeon.core.testabstraction.product;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.AeonConfigKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ProductTests {

    private class ProductChild extends Product {
        ProductChild(AutomationInfo automationInfo) {
            super(automationInfo);
        }
    }

    @Mock
    private AutomationInfo automationInfo;

    @Mock
    private Configuration configuration;

    private Product product;

    private AeonConfigKey key = Configuration.Keys.TIMEOUT;

    @BeforeEach
    void setUp() {
        when(this.automationInfo.getConfiguration()).thenReturn(this.configuration);
        product = new ProductChild(this.automationInfo);
    }

    @Test
    void getAutomationInfo_returnsAutomationInfoCorrectly() {

        //Arrange

        //Act
        AutomationInfo currentAutomationInfo = product.getAutomationInfo();

        //Assert
        assertEquals(currentAutomationInfo, automationInfo);
    }

    @Test
    void getConfiguration_returnsConfigurationCorrectly() {

        //Arrange

        //Act
        Configuration currentConfiguration = product.getConfiguration();

        //Assert
        assertEquals(currentConfiguration, configuration);
    }

    @Test
    void afterLaunch_launchedIsCalled() {

        // Arrange

        // Act
        product.afterLaunch();

        //Assert
        verify(automationInfo, times(1)).launched();
    }

    @Test
    void getConfig_booleanArgument_returnsBoolean() {

        // Arrange
        when(configuration.getBoolean(key, false)).thenReturn(true);

        // Act
        boolean called = product.getConfig(key, false);

        //Assert
        assertTrue(called);
    }

    @Test
    void getConfig_stringArgument_returnsString() {

        // Arrange
        when(configuration.getString(key, "")).thenReturn("Test Passed");

        // Act
        String call = product.getConfig(key, "");

        // Assert
        assertEquals("Test Passed", call);
    }

    @Test
    void getConfig_doubleArgument_returnsDouble() {

        // Arrange
        double testSuccess = 5;
        double defaultValue = 0;
        when(configuration.getDouble(key, defaultValue)).thenReturn(testSuccess);

        // Act
        double testValue = product.getConfig(key, defaultValue);

        // Assert
        assertEquals(testValue, testSuccess);
    }

    @Test
    void getConfig_onCallWithBooleanDefaultValue_callsConfigurationGetBoolean() {

        // Arrange
        String key = "key";
        boolean value = false;

        // Act
        product.getConfig(key, value);

        // Assert
        verify(configuration, times(1)).getBoolean(key, value);
    }

    @Test
    void getConfig_onCallWithStringDefaultValue_callsConfigurationGetString() {

        // Arrange
        String key = "key";
        String value = "val";

        // Act
        product.getConfig(key, value);

        // Assert
        verify(configuration, times(1)).getString(key, value);
    }

    @Test
    void getConfig_onCallWithDoubleDefaultValue_callsConfigurationGetDouble() {

        // Arrange
        String key = "key";
        double value = 123456789;

        // Act
        product.getConfig(key, value);

        // Assert
        verify(configuration, times(1)).getDouble(key, value);
    }
}
