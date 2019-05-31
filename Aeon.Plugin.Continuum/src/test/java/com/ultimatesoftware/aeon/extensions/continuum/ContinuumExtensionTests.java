package com.ultimatesoftware.aeon.extensions.continuum;

import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
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
import static org.mockito.Mockito.verifyZeroInteractions;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ContinuumExtensionTests {

    @Mock
    private IConfiguration configuration;

    @Mock
    private Configuration aeonConfiguration;

    @Mock
    private RemoteWebDriver webDriver;

    @Mock
    private Logger log;

    private MutableCapabilities mutableCapabilities = new MutableCapabilities();

    private ContinuumExtension continuumExtension;

    @BeforeEach
    void setUp() {
        this.continuumExtension = new ContinuumExtension(this.configuration);
        ContinuumExtension.log = this.log;
    }

    @Test
    void createInstance_createsAnInstanceSuccessfully() {

        // Arrange

        // Act
        Object extension = ContinuumExtension.createInstance();

        // Assert
        assertEquals(ContinuumExtension.class, extension.getClass());
    }

    @Test
    void onGenerateCapabilities_usernameIsSet_setsUsernameInCapabilities() {

        // Arrange

        // Act
        this.continuumExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        // Assert
    }

    @Test
    void onAfterLaunch_isCalled_doesNotDoAnything() {

        // Arrange

        // Act
        this.continuumExtension.onAfterLaunch(this.aeonConfiguration, this.webDriver);

        // Assert
        verifyZeroInteractions(this.aeonConfiguration);
        verifyZeroInteractions(this.webDriver);
    }
}
