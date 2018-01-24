package aeon.core.testabstraction.product;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Field;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import aeon.core.common.web.BrowserType;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.drivers.IDriver;
import org.apache.logging.log4j.Logger;

import org.junit.*;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.*;

public class ConfigurationTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IDriver driver;

    @Mock
    private IAdapter adapter;

    @Mock
    private Properties properties;

    @Mock
    private InputStream inputStream;

    @Mock
    private Enumeration enumerationList;

    @Mock
    private Logger log;

    private Configuration config;

    private Configuration spyConfig;

    @Before
    public void setUp() {
        when(properties.propertyNames()).thenReturn(enumerationList);
        config = new Configuration(driver.getClass(), adapter.getClass());
        spyConfig = org.mockito.Mockito.spy(config);
        Configuration.log = log;
    }

    @Test
    public void testInvalidTestPropertiesFile() throws IOException, IllegalAccessException {
        //Arrange
        when(spyConfig.getDefaultConfigInputStream()).thenReturn(null);
        spyConfig.properties = properties;

        //Act
        spyConfig.loadConfiguration();

        //Assert
        verify(properties, times(1)).load(any(java.io.InputStream.class));
        verify(log, times(1)).info("No config file in use, using default values.");
    }

    @Test
    public void testValidTestPropertiesFile() throws IOException, IllegalAccessException {
        //Arrange
        when(spyConfig.getDefaultConfigInputStream()).thenReturn(inputStream);
        spyConfig.properties = properties;

        //Act
        spyConfig.loadConfiguration();

        //Assert
        verify(properties, times(2)).load(any(InputStream.class));
    }

    @Test (expected = IOException.class)
    public void testThrowsException() throws IllegalAccessException, IOException {
        //Arrange
        when(spyConfig.getAeonInputStream()).thenReturn(null);

        //Act
        spyConfig.loadConfiguration();
    }

    @Test
    public void testAeonConfigNotDefined() throws IOException, IllegalAccessException {
        //Arrange
        when(spyConfig.getEnvironmentValue("AEON_CONFIG")).thenReturn(null);

        //Act
        spyConfig.loadConfiguration();

        //Assert
        verify(spyConfig, times(1)).getDefaultConfigInputStream();
    }

    @Test (expected = java.io.FileNotFoundException.class)
    public void testInvalidAeonConfigDefinition() throws IOException, IllegalAccessException {
        //Arrange
        when(spyConfig.getEnvironmentValue("AEON_CONFIG")).thenReturn("impossiblePath");

        //Act
        spyConfig.loadConfiguration();
    }

    @Test
    public void testValidAbsoluteAeonConfigDefinition() throws IOException, IllegalAccessException {
        //Arrange
        Path relativePath = Paths.get(".").toAbsolutePath().getParent();
        Path absolutePath = Paths.get(relativePath + "/src/main/resources/aeon.properties").toAbsolutePath();
        when(spyConfig.getEnvironmentValue("AEON_CONFIG")).thenReturn(absolutePath.toString());

        //Act
        spyConfig.loadConfiguration();

        //Assert
        verify(spyConfig, times(0)).getDefaultConfigInputStream();
        verify(spyConfig, times(0)).getRelativeAeonConfigProperties(any(Path.class));
    }

    @Test
    public void testKeys() throws IOException, IllegalAccessException {
        List<Field> fieldList = new ArrayList<>();
        //Arrange
        when(spyConfig.getConfigurationFields()).thenReturn(fieldList);

        //Act
        spyConfig.loadConfiguration();

        //Assert
        Assert.assertTrue(fieldList.get(0).isAccessible());
        Assert.assertTrue(fieldList.get(1).isAccessible());
    }

    @Test
    public void testSetProperties() throws Exception {
        //Arrange
        when(spyConfig.getEnvironmentValue("aeon.timeout")).thenReturn("testEnv");
        when(spyConfig.getEnvironmentValue("aeon.browser")).thenReturn("testEnv2");

        spyConfig.properties = properties;

        //Act
        spyConfig.loadConfiguration();

        //Assert
        verify(properties, times(1)).setProperty("aeon.timeout", "testEnv");
        verify(properties, times(1)).setProperty("aeon.browser", "testEnv2");
    }

    @Test
    public void testEnumeration() throws IOException, IllegalAccessException {
        //Arrange
        when(enumerationList.hasMoreElements()).thenReturn(true, false);

        //Act
        config.loadConfiguration();

        //Assert
        verify(log, times(1)).info("These are the properties values currently in use:\naeon.timeout = 10\naeon.throttle = 50\n");
    }

    @Test
    public void testGetDriver() {
        //Assert
        Assert.assertEquals(driver.getClass(), config.getDriver());
    }

    @Test
    public void testSetDriver(){
        //Act
        config.setDriver(IDriver.class);
        //Assert
        assert(IDriver.class.equals(config.getDriver()));
    }

    @Test
    public void testGetAdapter() {
        //Assert
        Assert.assertEquals(adapter.getClass(), config.getAdapter());
    }

    @Test
    public void testSetAdapter() {
        //Act
        config.setAdapter(IAdapter.class);
        //Assert
        Assert.assertEquals(IAdapter.class, config.getAdapter());
    }

    @Test
    public void testGetBrowserType()  {
        //Act
        config.setBrowserType(BrowserType.Chrome);
        //Assert
        assert(BrowserType.Chrome).equals(config.getBrowserType());
    }

    @Test
    public void testSetBrowserType()  {
        //Act
        config.setBrowserType(BrowserType.Firefox);
        //Assert
        assert(BrowserType.Firefox).equals(config.getBrowserType());
    }

    @Test
    public void testSetBoolean() {
        //Arrange
        config.setBoolean("Configuration.Keys.WAIT_FOR_AJAX_RESPONSES",true);
        //Act
        String testVar = config.getString("Configuration.Keys.WAIT_FOR_AJAX_RESPONSES","false");
        //Assert
        Assert.assertEquals("true", testVar);
    }

    @Test
    public void testSetString()  {
        //Arrange
        config.setString("Configuration.Keys.WAIT_FOR_AJAX_RESPONSES","true");
        //Act
        Boolean testVar = config.getBoolean("Configuration.Keys.WAIT_FOR_AJAX_RESPONSES",false);
        //Assert
        Assert.assertEquals(true, testVar);
    }

    @Test
    public void testSetDouble(){
        //Arrange
        config.setDouble("Configuration.Keys.WAIT_FOR_AJAX_RESPONSES", 6.7);
        //Act
        String testVar = config.getString("Configuration.Keys.WAIT_FOR_AJAX_RESPONSES","4.58");
        //Assert
        Assert.assertEquals("6.7", testVar);
    }

    @Test
    public void testGetBoolean() {
        //Arrange
        config.setString("Configuration.Keys.WAIT_FOR_AJAX_RESPONSES","true");
        //Act
        boolean testVar = config.getBoolean("Configuration.Keys.WAIT_FOR_AJAX_RESPONSES",false);
        //Assert
        Assert.assertEquals("Not returning a boolean value", true,testVar);
    }

    @Test
    public void testGetDouble()  {
        //Arrange
        config.setString("Configuration.Keys.WAIT_FOR_AJAX_RESPONSES","4.50");
        //Act
        double testVar = config.getDouble("Configuration.Keys.WAIT_FOR_AJAX_RESPONSES", 1.58);
        //Assert
        Assert.assertEquals(4.5,testVar,0);
    }

    @Test
    public void testGetString() {
        //Arrange
        config.setDouble("Configuration.Keys.WAIT_FOR_AJAX_RESPONSES",6.7);
        //Act
        String testVar = config.getString("Configuration.Keys.WAIT_FOR_AJAX_RESPONSES", "5.67");
        //Assert
        Assert.assertEquals("Not returning correct string value","6.7",testVar);
    }
}
