package com.ultimatesoftware.aeon.extensions.headspin;

import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import com.ultimatesoftware.aeon.extensions.selenium.SeleniumConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyZeroInteractions;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class HeadSpinExtensionTests {

    @Mock
    private IConfiguration configuration;

    @Mock
    private Configuration aeonConfiguration;

    @Mock
    private RemoteWebDriver webDriver;

    @Mock
    private Logger log;

    private MutableCapabilities mutableCapabilities = new MutableCapabilities();

    private HeadSpinExtension testMagoExtension;

    @BeforeEach
    void setUp() {
        this.testMagoExtension = new HeadSpinExtension(this.configuration);
        HeadSpinExtension.log = this.log;
    }

    @Test
    void createInstance_createsAnInstanceSuccessfully() {

        // Arrange

        // Act
        Object extension = HeadSpinExtension.createInstance();

        // Assert
        assertEquals(HeadSpinExtension.class, extension.getClass());
    }

    @Test
    void onGenerateCapabilities_udidIsSet_setsUdidInCapabilities() {

        // Arrange
        doReturn("udid")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.UDID, "");

        // Act
        this.testMagoExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        // Assert
        assertEquals("udid", this.mutableCapabilities.getCapability("udid"));
    }

    @Test
    void onGenerateCapabilities_udidIsNotSet_doesNotSetUdidInCapabilities() {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.UDID, "");

        // Act
        this.testMagoExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        // Assert
        assertNull(this.mutableCapabilities.getCapability("udid"));
    }

    @Test
    void onGenerateCapabilities_captureIsSet_setsCaptureInCapabilities() {

        // Arrange
        doReturn(true)
                .when(this.configuration)
                .getBoolean(HeadSpinConfiguration.Keys.CAPTURE, false);
        doReturn("")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.UDID, "");

        // Act
        this.testMagoExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        // Assert
        assertEquals(true, this.mutableCapabilities.getCapability("headspin.capture"));
    }

    @Test
    void onGenerateCapabilities_captureIsNotSetOrSetToFalse_doesNotSetCaptureInCapabilities() {

        // Arrange
        doReturn(false)
                .when(this.configuration)
                .getBoolean(HeadSpinConfiguration.Keys.CAPTURE, false);
        doReturn("")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.UDID, "");

        // Act
        this.testMagoExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        // Assert
        assertNull(this.mutableCapabilities.getCapability("headspin.capture"));
    }

    @Test
    void onAfterLaunch_isCalled_doesNotDoAnything() {

        // Arrange

        // Act
        this.testMagoExtension.onAfterLaunch(this.aeonConfiguration, this.webDriver);

        // Assert
        verifyZeroInteractions(this.aeonConfiguration);
        verifyZeroInteractions(this.webDriver);
    }
}
