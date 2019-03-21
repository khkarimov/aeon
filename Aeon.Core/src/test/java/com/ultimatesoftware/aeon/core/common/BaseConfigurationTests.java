package com.ultimatesoftware.aeon.core.common;

import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class BaseConfigurationTests {

    @Mock
    private Properties properties;

    @Mock
    private InputStream inputStream;

    @Mock
    private Enumeration enumerationList;

    @Mock
    private Logger log;

    private BaseConfiguration config;

    private BaseConfiguration spyConfig;

    @BeforeEach
    public void setUp() {
        config = new BaseConfiguration();
        spyConfig = org.mockito.Mockito.spy(config);
        BaseConfiguration.log = log;
    }

    @Test
    public void testInvalidTestPropertiesFile() throws IOException {
        // Arrange
        when(spyConfig.getDefaultConfigInputStream()).thenReturn(null);
        when(properties.propertyNames()).thenReturn(enumerationList);
        spyConfig.properties = properties;

        // Act
        spyConfig.loadConfiguration();

        // Assert
        verify(properties, times(1)).load(any(java.io.InputStream.class));
        verify(log, times(1)).info("No config file in use, using default values.");
    }

    @Test
    public void testValidTestPropertiesFile() throws IOException {
        // Arrange
        when(spyConfig.getDefaultConfigInputStream()).thenReturn(inputStream);
        when(properties.propertyNames()).thenReturn(enumerationList);
        spyConfig.properties = properties;

        // Act
        spyConfig.loadConfiguration();

        // Assert
        verify(properties, times(2)).load(any(InputStream.class));
    }

    @Test
    public void testThrowsException() {
        // Arrange
        String errorMessage = "No aeon.properties file was found.";
        when(spyConfig.getAeonInputStream()).thenReturn(null);
        Exception exception;

        // Act
        exception = Assertions.assertThrows(IOException.class,
                () -> spyConfig.loadConfiguration());

        // Assert
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testAeonConfigNotDefined() throws IOException {
        // Arrange
        when(spyConfig.getEnvironmentValue("AEON_CONFIG")).thenReturn(null);

        // Act
        spyConfig.loadConfiguration();

        // Assert
        verify(spyConfig, times(1)).getDefaultConfigInputStream();
    }

    @Test
    public void testInvalidAeonConfigDefinition() {
        // Arrange
        when(spyConfig.getEnvironmentValue("AEON_CONFIG")).thenReturn("impossiblePath");

        // Act
        Assertions.assertThrows(java.io.FileNotFoundException.class,
                () -> spyConfig.loadConfiguration());
    }

    @Test
    public void testValidAbsoluteAeonConfigDefinition() throws IOException {
        // Arrange
        Path relativePath = Paths.get(".").toAbsolutePath().getParent();
        Path absolutePath = Paths.get(relativePath + "/src/main/resources/aeon.properties").toAbsolutePath();
        when(spyConfig.getEnvironmentValue("AEON_CONFIG")).thenReturn(absolutePath.toString());

        // Act
        spyConfig.loadConfiguration();

        // Assert
        verify(spyConfig, times(0)).getDefaultConfigInputStream();
        verify(spyConfig, times(0)).getRelativeAeonConfigProperties(any(Path.class));
    }

    @Test
    public void testKeys() throws IOException {
        List<AeonConfigKey> fieldList = Arrays.stream(Configuration.Keys.values())
                .collect(Collectors.toList());

        // Arrange
        when(spyConfig.getConfigurationFields()).thenReturn(fieldList);

        // Act
        spyConfig.loadConfiguration();

        // Assert
        Assertions.assertNotNull(fieldList.get(0));
        Assertions.assertNotNull(fieldList.get(1));
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void testSetProperties() throws Exception {
        // Arrange
        when(spyConfig.getEnvironmentValue("aeon.timeout")).thenReturn("testEnv");
        when(spyConfig.getEnvironmentValue("aeon.throttle")).thenReturn("testEnv2");
        when(spyConfig.getConfigurationFields()).thenReturn(Arrays.stream(Configuration.Keys.values())
                .collect(Collectors.toList()));
        when(properties.propertyNames()).thenReturn(enumerationList);

        spyConfig.properties = properties;

        // Act
        spyConfig.loadConfiguration();

        // Assert
        verify(properties, times(1)).setProperty("aeon.timeout", "testEnv");
        verify(properties, times(1)).setProperty("aeon.throttle", "testEnv2");
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void testSetPropertiesWithUnderscoreEnvironmentVariable() throws Exception {
        // Arrange
        when(spyConfig.getEnvironmentValue("aeon.timeout")).thenReturn("testEnv");
        when(spyConfig.getEnvironmentValue("aeon.throttle")).thenReturn("testEnv2");
        when(spyConfig.getEnvironmentValue("aeon_throttle")).thenReturn("testEnv3");
        when(spyConfig.getConfigurationFields()).thenReturn(Arrays.stream(Configuration.Keys.values())
                .collect(Collectors.toList()));
        when(properties.propertyNames()).thenReturn(enumerationList);

        spyConfig.properties = properties;

        // Act
        spyConfig.loadConfiguration();

        // Assert
        InOrder inOrder = inOrder(properties);
        inOrder.verify(properties, times(1)).setProperty("aeon.timeout", "testEnv");
        inOrder.verify(properties, times(1)).setProperty("aeon.throttle", "testEnv3");
        inOrder.verify(properties, times(1)).setProperty("aeon.throttle", "testEnv2");
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void testSetPropertiesWithUnderscoreEnvironmentVariableInsteadOfDot() throws Exception {
        // Arrange
        when(spyConfig.getEnvironmentValue("aeon.timeout")).thenReturn("testEnv");
        when(spyConfig.getEnvironmentValue("aeon_throttle")).thenReturn("testEnv3");
        when(spyConfig.getConfigurationFields()).thenReturn(Arrays.stream(Configuration.Keys.values())
                .collect(Collectors.toList()));
        when(properties.propertyNames()).thenReturn(enumerationList);

        spyConfig.properties = properties;

        // Act
        spyConfig.loadConfiguration();

        // Assert
        InOrder inOrder = inOrder(properties);
        inOrder.verify(properties, times(1)).setProperty("aeon.timeout", "testEnv");
        inOrder.verify(properties, times(1)).setProperty("aeon.throttle", "testEnv3");
        inOrder.verify(properties, times(0)).setProperty("aeon.throttle", "testEnv2");
    }

    @Test
    public void testEnumeration() throws IOException {
        // Arrange

        // Act
        config.loadConfiguration();

        // Assert
        verify(log, times(1)).info("These are the properties values currently in use for BaseConfiguration:\naeon.timeout = 10\naeon.implicit_reporting = true\naeon.throttle = 50\n");
    }

    @Test
    public void testSetBoolean() {
        // Arrange
        config.setBoolean(Configuration.Keys.TIMEOUT, true);
        // Act
        String testVar = config.getString(Configuration.Keys.TIMEOUT, "false");
        // Assert
        Assertions.assertEquals("true", testVar);
    }

    @Test
    public void testSetString() {
        // Arrange
        config.setString(Configuration.Keys.TIMEOUT, "true");
        // Act
        Boolean testVar = config.getBoolean(Configuration.Keys.TIMEOUT, false);
        // Assert
        Assertions.assertEquals(true, testVar);
    }

    @Test
    public void testSetDouble() {
        // Arrange
        config.setDouble(Configuration.Keys.TIMEOUT, 6.7);
        // Act
        String testVar = config.getString(Configuration.Keys.TIMEOUT, "4.58");
        // Assert
        Assertions.assertEquals("6.7", testVar);
    }

    @Test
    public void testGetBoolean() {
        // Arrange
        config.setString(Configuration.Keys.TIMEOUT, "true");
        // Act
        boolean testVar = config.getBoolean(Configuration.Keys.TIMEOUT, false);
        // Assert
        Assertions.assertEquals(true, testVar, "Not returning a boolean value");
    }

    @Test
    public void testGetDouble() {
        // Arrange
        config.setString(Configuration.Keys.TIMEOUT, "4.50");
        // Act
        double testVar = config.getDouble(Configuration.Keys.TIMEOUT, 1.58);
        // Assert
        Assertions.assertEquals(4.5, testVar);
    }

    @Test
    public void testGetString() {
        // Arrange
        config.setDouble(Configuration.Keys.TIMEOUT, 6.7);
        // Act
        String testVar = config.getString(Configuration.Keys.TIMEOUT, "5.67");
        // Assert
        Assertions.assertEquals("6.7", testVar, "Not returning correct string value");
    }
}
