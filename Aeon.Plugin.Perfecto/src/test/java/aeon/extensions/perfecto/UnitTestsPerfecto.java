package aeon.extensions.perfecto;

import aeon.core.testabstraction.product.Configuration;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aeon.selenium.SeleniumConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.apache.logging.log4j.Logger;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UnitTestsPerfecto {

    @Mock
    private Configuration aeonConfiguration;
    private Configuration spyAeonConfiguration;

    @Mock
    private PerfectoConfiguration perfectoConfiguration;
    private PerfectoConfiguration spyPerfectoConfiguration;

    @Mock
    private MutableCapabilities mutableCapabilities;

    @Mock
    private Logger log;

    @Mock
    private AndroidDriver aDriver;

    private PerfectoPlugin.PerfectoSeleniumExtension seleniumExtension;

    @BeforeEach
    public void setUp() {
        //instantiate configurations
        perfectoConfiguration = new PerfectoConfiguration();
        spyPerfectoConfiguration = Mockito.spy(perfectoConfiguration);
        aeonConfiguration = mock(Configuration.class);
        spyAeonConfiguration = Mockito.spy(aeonConfiguration);

        //plugin settings
        PerfectoPlugin.log = log;
        PerfectoPlugin.configuration = spyPerfectoConfiguration;

        //instantiate target - PerfectoSeleniumExtension to test methods
        seleniumExtension = new PerfectoPlugin.PerfectoSeleniumExtension();
    }

    @Test
    public void test_onGenerateCapabilities_ValidToken() {
        //Arrange
        when(spyPerfectoConfiguration.getString(PerfectoConfiguration.Keys.PERFECTO_TOKEN, "")).thenReturn("test_token");
        mutableCapabilities = new DesiredCapabilities();

        //Act
        seleniumExtension.onGenerateCapabilities(aeonConfiguration, mutableCapabilities);

        //Assert
        System.out.println(mutableCapabilities.getCapability("securityToken"));
        Assertions.assertEquals(mutableCapabilities.getCapability("securityToken"), "test_token");
        Assertions.assertNull(mutableCapabilities.getCapability("user"));
        Assertions.assertNull(mutableCapabilities.getCapability("password"));
    }

    @Test
    public void test_onGenerateCapabilities_EmptyTokenValidUserAndPass() {
        //Arrange
        when(spyPerfectoConfiguration.getString(PerfectoConfiguration.Keys.PERFECTO_USER, "")).thenReturn("test_user");
        when(spyPerfectoConfiguration.getString(PerfectoConfiguration.Keys.PERFECTO_PASS, "")).thenReturn("test_pass");
        when(spyPerfectoConfiguration.getString(PerfectoConfiguration.Keys.PERFECTO_TOKEN, "")).thenReturn("");
        mutableCapabilities = new DesiredCapabilities();

        //Act
        seleniumExtension.onGenerateCapabilities(aeonConfiguration, mutableCapabilities);

        //Assert
        Assertions.assertNull(mutableCapabilities.getCapability("securityToken"));
        Assertions.assertEquals(mutableCapabilities.getCapability("user"), "test_user");
        Assertions.assertEquals(mutableCapabilities.getCapability("password"), "test_pass");
    }

    @Test
    public void test_onGenerateCapabilities_EmptyTokenAndUser() {
        //Arrange
        when(spyPerfectoConfiguration.getString(PerfectoConfiguration.Keys.PERFECTO_USER, "")).thenReturn("");
        when(spyPerfectoConfiguration.getString(PerfectoConfiguration.Keys.PERFECTO_PASS, "")).thenReturn("test_pass");
        when(spyPerfectoConfiguration.getString(PerfectoConfiguration.Keys.PERFECTO_TOKEN, "")).thenReturn("");
        when(spyPerfectoConfiguration.getBoolean(PerfectoConfiguration.Keys.PERFECTO_AUTOINSTRUMENT,false)).thenReturn(true);
        when(spyPerfectoConfiguration.getBoolean(PerfectoConfiguration.Keys.PERFECTO_SENSORINSTRUMENT,false)).thenReturn(true);
        mutableCapabilities = new DesiredCapabilities();

        //Act
        seleniumExtension.onGenerateCapabilities(aeonConfiguration, mutableCapabilities);

        //Assert
        Assertions.assertNull(mutableCapabilities.getCapability("securityToken"));
        Assertions.assertNull(mutableCapabilities.getCapability("user"));
        Assertions.assertNull(mutableCapabilities.getCapability("password"));
    }

    @Test
    public void test_onGenerateCapabilities_NullPerfectoConfiguration() {
        //Arrange
        spyPerfectoConfiguration = null;
        mutableCapabilities = new DesiredCapabilities();

        //Act
        seleniumExtension.onGenerateCapabilities(aeonConfiguration, mutableCapabilities);

        //Assert
        Assertions.assertNotEquals(spyPerfectoConfiguration, PerfectoPlugin.configuration);
    }

    @Test
    public void test_onAfterLaunch_OK() {
        //Arrange
        when(spyAeonConfiguration.getBoolean(SeleniumConfiguration.Keys.ENSURE_CLEAN_ENVIRONMENT, true)).thenReturn(true);
        when(spyAeonConfiguration.getString(SeleniumConfiguration.Keys.APP_PACKAGE, "")).thenReturn("test_appPackage");
        aDriver = mock(AndroidDriver.class);

        //Act
        seleniumExtension.onAfterLaunch(spyAeonConfiguration,aDriver);

        //Assert
        verify(log, times(1)).info("Cleaning application environment...");
    }

    @Test
    public void test_onAfterLaunch_NotCleanEnvironment() {
        //Arrange
        when(spyAeonConfiguration.getBoolean(SeleniumConfiguration.Keys.ENSURE_CLEAN_ENVIRONMENT, true)).thenReturn(false);
        when(spyAeonConfiguration.getString(SeleniumConfiguration.Keys.APP_PACKAGE, "")).thenReturn("test_appPackage");
        aDriver = mock(AndroidDriver.class);

        //Act
        seleniumExtension.onAfterLaunch(spyAeonConfiguration,null);

        //Assert
        verifyZeroInteractions(log);
    }

    @Test
    public void test_onAfterLaunch_NoAppPackage() {
        //Arrange
        when(spyAeonConfiguration.getBoolean(SeleniumConfiguration.Keys.ENSURE_CLEAN_ENVIRONMENT, true)).thenReturn(true);
        when(spyAeonConfiguration.getString(SeleniumConfiguration.Keys.APP_PACKAGE, "")).thenReturn("");
        aDriver = mock(AndroidDriver.class);

        //Act
        seleniumExtension.onAfterLaunch(spyAeonConfiguration,null);

        //Assert
        verifyZeroInteractions(log);
    }
}
