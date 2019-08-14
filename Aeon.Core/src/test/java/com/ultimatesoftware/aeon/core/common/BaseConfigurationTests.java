package com.ultimatesoftware.aeon.core.common;

import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
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
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class BaseConfigurationTests {

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

    /**
     * Enum for the Configuration keys.
     */
    public enum TestKeys implements AeonConfigKey {

        PASSWORD("passwordkey1"),
        TOKEN("tokenkey1");

        private String key;

        TestKeys(String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return this.key;
        }
    }

    @BeforeEach
    void setUp() {
        config = new BaseConfiguration();
        spyConfig = org.mockito.Mockito.spy(config);
        BaseConfiguration.log = log;
    }

    @Test
    void testInvalidTestPropertiesFile() throws IOException {

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
    void testValidTestPropertiesFile() throws IOException {

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
    void testThrowsException() {

        // Arrange
        String errorMessage = "No aeon.properties file was found.";
        when(spyConfig.getAeonInputStream()).thenReturn(null);
        Exception exception;

        // Act
        exception = assertThrows(IOException.class,
                () -> spyConfig.loadConfiguration());

        // Assert
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testAeonConfigNotDefined() throws IOException {

        // Arrange
        when(spyConfig.getEnvironmentValue("AEON_CONFIG")).thenReturn(null);

        // Act
        spyConfig.loadConfiguration();

        // Assert
        verify(spyConfig, times(1)).getDefaultConfigInputStream();
    }

    @Test
    void testInvalidAeonConfigDefinition() {

        // Arrange
        when(spyConfig.getEnvironmentValue("AEON_CONFIG")).thenReturn("impossiblePath");

        // Act
        Executable action = () -> spyConfig.loadConfiguration();

        // Assert
        assertThrows(java.io.FileNotFoundException.class, action);

    }

    @Test
    void loadConfiguration_withAbsoluteAeonConfigDefinition_doesNotUseDefaultOrRelative() throws IOException {

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
    void loadConfiguration_withRelativeAeonConfigDefinition_usesRelative() throws IOException {

        // Arrange
        Path relativePath = Paths.get("src/main/resources/aeon.properties");
        when(spyConfig.getEnvironmentValue("AEON_CONFIG")).thenReturn(relativePath.toString());

        // Act
        spyConfig.loadConfiguration();

        // Assert
        verify(spyConfig, times(0)).getDefaultConfigInputStream();
        verify(spyConfig, times(1)).getRelativeAeonConfigProperties(any(Path.class));
    }

    @Test
    void testKeys() throws IOException {

        // Arrange
        List<AeonConfigKey> fieldList = Arrays.stream(Configuration.Keys.values())
                .collect(Collectors.toList());
        when(spyConfig.getConfigurationFields()).thenReturn(fieldList);

        // Act
        spyConfig.loadConfiguration();

        // Assert
        assertNotNull(fieldList.get(0));
        assertNotNull(fieldList.get(1));
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void testSetProperties() throws Exception {

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
    void testSetPropertiesWithUnderscoreEnvironmentVariable() throws Exception {

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
    void testSetPropertiesWithUnderscoreEnvironmentVariableInsteadOfDot() throws Exception {

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
    @MockitoSettings(strictness = Strictness.LENIENT)
    void loadConfiguration_withPasswordKey_doesNotLogPassword() throws Exception {

        // Arrange
        when(spyConfig.getEnvironmentValue("passwordkey1")).thenReturn("testpassword");
        when(spyConfig.getConfigurationFields()).thenReturn(Arrays.stream(TestKeys.values())
                .collect(Collectors.toList()));
        when(properties.propertyNames()).thenReturn((Enumeration) java.util.Collections.enumeration(Arrays.asList("passwordkey1")));

        spyConfig.properties = properties;

        // Act
        spyConfig.loadConfiguration();

        // Assert
        verify(properties, atLeastOnce()).setProperty("passwordkey1", "testpassword");
        verify(log, atLeastOnce()).info("These are the properties values currently in use for BaseConfiguration:\npasswordkey1 = *****\n");
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void loadConfiguration_withTokenKey_doesNotLogPassword() throws Exception {

        // Arrange
        when(spyConfig.getEnvironmentValue("tokenkey1")).thenReturn("testtoken");
        when(spyConfig.getConfigurationFields()).thenReturn(Arrays.stream(TestKeys.values())
                .collect(Collectors.toList()));
        when(properties.propertyNames()).thenReturn((Enumeration) java.util.Collections.enumeration(Arrays.asList("tokenkey1")));

        spyConfig.properties = properties;

        // Act
        spyConfig.loadConfiguration();

        // Assert
        verify(properties, atLeastOnce()).setProperty("tokenkey1", "testtoken");
        verify(log, atLeastOnce()).info("These are the properties values currently in use for BaseConfiguration:\ntokenkey1 = *****\n");
    }

    @Test
    void testEnumeration() throws IOException {

        // Arrange

        // Act
        config.loadConfiguration();

        // Assert
        verify(log, times(1)).info("These are the properties values currently in use for BaseConfiguration:\naeon.timeout = 10\naeon.implicit_reporting = true\naeon.throttle = 50\n");
    }

    @Test
    void setBoolean_withKeyObject_setsConfigValue() {

        // Arrange
        config.setBoolean(Configuration.Keys.TIMEOUT, true);

        // Act
        String testVar = config.getString(Configuration.Keys.TIMEOUT, "false");

        // Assert
        assertEquals("true", testVar);
    }

    @Test
    void setBoolean_withStringKey_setsConfigValue() {

        // Arrange
        config.setBoolean("aeon.timeout", true);

        // Act
        String testVar = config.getString(Configuration.Keys.TIMEOUT, "false");

        // Assert
        assertEquals("true", testVar);
    }

    @Test
    void setString_withKeyObject_setsConfigValue() {

        // Arrange
        config.setString(Configuration.Keys.TIMEOUT, "true");

        // Act
        Boolean testVar = config.getBoolean(Configuration.Keys.TIMEOUT, false);

        // Assert
        assertEquals(true, testVar);
    }

    @Test
    void setString_withStringKey_setsConfigValue() {

        // Arrange
        config.setString("aeon.timeout", "true");

        // Act
        Boolean testVar = config.getBoolean(Configuration.Keys.TIMEOUT, false);

        // Assert
        assertEquals(true, testVar);
    }

    @Test
    void setDouble_withKeyObject_setsConfigValue() {

        // Arrange
        config.setDouble(Configuration.Keys.TIMEOUT, 6.7);

        // Act
        String testVar = config.getString(Configuration.Keys.TIMEOUT, "4.58");

        // Assert
        assertEquals("6.7", testVar);
    }

    @Test
    void setDouble_withStringKey_setsConfigValue() {

        // Arrange
        config.setDouble("aeon.timeout", 6.7);

        // Act
        String testVar = config.getString(Configuration.Keys.TIMEOUT, "4.58");

        // Assert
        assertEquals("6.7", testVar);
    }

    @Test
    void getBoolean_withKeyObject_returnsCorrectValue() {

        // Arrange
        config.setString(Configuration.Keys.TIMEOUT, "true");

        // Act
        boolean testVar = config.getBoolean(Configuration.Keys.TIMEOUT, false);

        // Assert
        assertTrue(testVar, "Not returning a boolean value");
    }

    @Test
    void getBoolean_withStringKey_returnsCorrectValue() {

        // Arrange
        config.setString(Configuration.Keys.TIMEOUT, "true");

        // Act
        boolean testVar = config.getBoolean("aeon.timeout", false);

        // Assert
        assertTrue(testVar, "Not returning a boolean value");
    }

    @Test
    void getDouble_withKeyObject_returnsCorrectValue() {

        // Arrange
        config.setString(Configuration.Keys.TIMEOUT, "4.50");

        // Act
        double testVar = config.getDouble(Configuration.Keys.TIMEOUT, 1.58);

        // Assert
        assertEquals(4.5, testVar);
    }

    @Test
    void getDouble_withStringKey_returnsCorrectValue() {

        // Arrange
        config.setString(Configuration.Keys.TIMEOUT, "4.50");

        // Act
        double testVar = config.getDouble("aeon.timeout", 1.58);

        // Assert
        assertEquals(4.5, testVar);
    }

    @Test
    void getDouble_withParseError_returnsDefault() {

        // Arrange
        config.setString(Configuration.Keys.TIMEOUT, "not a double");

        // Act
        double testVar = config.getDouble(Configuration.Keys.TIMEOUT, 1.58);

        // Assert
        assertEquals(1.58, testVar);
    }

    @Test
    void getString_withKeyObject_returnsCorrectValue() {

        // Arrange
        config.setDouble(Configuration.Keys.TIMEOUT, 6.7);

        // Act
        String testVar = config.getString(Configuration.Keys.TIMEOUT, "5.67");

        // Assert
        assertEquals("6.7", testVar, "Not returning correct string value");
    }

    @Test
    void getString_withStringKey_returnsCorrectValue() {

        // Arrange
        config.setDouble(Configuration.Keys.TIMEOUT, 6.7);

        // Act
        String testVar = config.getString("aeon.timeout", "5.67");

        // Assert
        assertEquals("6.7", testVar, "Not returning correct string value");
    }

    @Test
    void getConfigurationKeys_givenValidKeys_returnsKeys() {

        // Arrange
        config.properties.setProperty("key1", "val1");
        config.properties.setProperty("key2", "val2");

        // Act
        List<String> props = config.getConfigurationKeys();

        // Assert
        assertLinesMatch(Arrays.asList("key1", "key2"), props);
    }

    @Test
    void getConfigurationKeys_withNoKeysSet_returnsEmptyList() {

        // Arrange

        // Act
        List<String> props = config.getConfigurationKeys();

        // Assert
        assertLinesMatch(props, new ArrayList<>());
    }
}
