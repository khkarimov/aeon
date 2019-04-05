package com.ultimatesoftware.aeon.extensions.saucelabs;

import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.openqa.selenium.MutableCapabilities;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyZeroInteractions;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class SauceLabsExtensionTests {

    @Mock
    private IConfiguration configuration;

    @Mock
    private Configuration aeonConfiguration;

    @Mock
    private AndroidDriver androidDriver;

    @Mock
    private Logger log;

    private MutableCapabilities mutableCapabilities = new MutableCapabilities();

    private SauceLabsExtension sauceLabsExtension;

    @BeforeEach
    void setUp() {
        this.sauceLabsExtension = new SauceLabsExtension(this.configuration);
        SauceLabsExtension.log = this.log;
    }

    @Test
    void createInstance_createsAnInstanceSuccessfully() {

        // Arrange

        // Act
        Object extension = SauceLabsExtension.createInstance();

        // Assert
        assertEquals(SauceLabsExtension.class, extension.getClass());
    }

    @Test
    void onGenerateCapabilities_usernameIsSet_setsUsernameInCapabilities() {

        // Arrange
        doReturn("test-username")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.USERNAME, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.ACCESS_KEY, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.API_KEY, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.APP_ID, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.APPIUM_VERSION, "");

        // Act
        this.sauceLabsExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        // Assert
        assertEquals("test-username", this.mutableCapabilities.getCapability("username"));
    }

    @Test
    void onGenerateCapabilities_usernameIsNotSet_doesNotSetUsernameInCapabilities() {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.USERNAME, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.ACCESS_KEY, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.API_KEY, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.APP_ID, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.APPIUM_VERSION, "");

        // Act
        this.sauceLabsExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        // Assert
        assertNull(this.mutableCapabilities.getCapability("username"));
    }

    @Test
    void onGenerateCapabilities_accessKeyIsSet_setsAccessKeyInCapabilities() {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.USERNAME, "");
        doReturn("access-key")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.ACCESS_KEY, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.API_KEY, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.APP_ID, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.APPIUM_VERSION, "");

        // Act
        this.sauceLabsExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        // Assert
        assertEquals("access-key", this.mutableCapabilities.getCapability("accessKey"));
    }

    @Test
    void onGenerateCapabilities_accessKeyIsNotSet_doesNotSetAccessKeyInCapabilities() {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.USERNAME, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.ACCESS_KEY, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.API_KEY, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.APP_ID, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.APPIUM_VERSION, "");

        // Act
        this.sauceLabsExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        // Assert
        assertNull(this.mutableCapabilities.getCapability("accessKey"));
    }

    @Test
    void onGenerateCapabilities_apiKeyIsSet_setsApiKeyInCapabilities() {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.USERNAME, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.ACCESS_KEY, "");
        doReturn("api-key")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.API_KEY, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.APP_ID, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.APPIUM_VERSION, "");

        // Act
        this.sauceLabsExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        // Assert
        assertEquals("api-key", this.mutableCapabilities.getCapability("testobject_api_key"));
    }

    @Test
    void onGenerateCapabilities_apiKeyIsNotSet_doesNotSetApiKeyInCapabilities() {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.USERNAME, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.ACCESS_KEY, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.API_KEY, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.APP_ID, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.APPIUM_VERSION, "");

        // Act
        this.sauceLabsExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        // Assert
        assertNull(this.mutableCapabilities.getCapability("testobject_api_key"));
    }

    @Test
    void onGenerateCapabilities_appIdIsSet_setsAppIdInCapabilities() {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.USERNAME, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.ACCESS_KEY, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.API_KEY, "");
        doReturn("app-id")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.APP_ID, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.APPIUM_VERSION, "");

        // Act
        this.sauceLabsExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        // Assert
        assertEquals("app-id", this.mutableCapabilities.getCapability("testobject_api_id"));
    }

    @Test
    void onGenerateCapabilities_appIdIsNotSet_doesNotSetAppIdInCapabilities() {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.USERNAME, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.ACCESS_KEY, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.API_KEY, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.APP_ID, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.APPIUM_VERSION, "");

        // Act
        this.sauceLabsExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        // Assert
        assertNull(this.mutableCapabilities.getCapability("testobject_app_id"));
    }

    @Test
    void onGenerateCapabilities_appiumVersionIsSet_setsAppiumVersionInCapabilities() {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.USERNAME, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.ACCESS_KEY, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.API_KEY, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.APP_ID, "");
        doReturn("appium-version")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.APPIUM_VERSION, "");

        // Act
        this.sauceLabsExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        // Assert
        assertEquals("appium-version", this.mutableCapabilities.getCapability("appiumVersion"));
    }

    @Test
    void onGenerateCapabilities_appiumVersionIsNotSet_doesNotSetAppiumVersionInCapabilities() {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.USERNAME, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.ACCESS_KEY, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.API_KEY, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.APP_ID, "");
        doReturn("")
                .when(this.configuration)
                .getString(SauceLabsConfiguration.Keys.APPIUM_VERSION, "");

        // Act
        this.sauceLabsExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        // Assert
        assertNull(this.mutableCapabilities.getCapability("appiumVersion"));
    }

    @Test
    void onAfterLaunch_isCalled_doesNotDoAnything() {

        // Arrange

        // Act
        this.sauceLabsExtension.onAfterLaunch(this.aeonConfiguration, this.androidDriver);

        // Assert
        verifyZeroInteractions(this.aeonConfiguration);
        verifyZeroInteractions(this.androidDriver);
    }
}
