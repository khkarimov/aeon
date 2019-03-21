package com.ultimatesoftware.aeon.core.testabstraction.product;

import com.ultimatesoftware.aeon.core.common.AeonConfigKey;
import com.ultimatesoftware.aeon.core.common.exceptions.ConfigurationException;
import com.ultimatesoftware.aeon.core.common.web.BrowserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class WebConfigurationTests {

    @Mock
    InputStream inputStream;

    private WebConfiguration webConfiguration;

    @BeforeEach
    void setUp() {
        webConfiguration = new WebConfiguration();
    }

    @Test
    void setBrowserType_validBrowser_returnsBrowserType() {

        // Arrange

        // Act
        webConfiguration.setBrowserType("Chrome");

        // Assert
        assertEquals(BrowserType.CHROME, webConfiguration.getBrowserType());
    }

    @Test
    void setBrowserType_invalidBrowser_throwsConfigurationException() {

        // Arrange

        // Act

        // Assert
        Assertions.assertThrows(ConfigurationException.class, () -> webConfiguration.setBrowserType("Invalid Browser"));
    }

    @Test
    void getConfigurationFields_returnsKeys() {

        // Arrange

        // Act
        List<AeonConfigKey> keys = new ArrayList<>(webConfiguration.getConfigurationFields());

        // Assert
        assertEquals("aeon.timeout", keys.get(0).getKey());
        assertEquals("aeon.throttle", keys.get(1).getKey());
        assertEquals("aeon.implicit_reporting", keys.get(2).getKey());
        assertEquals("aeon.wait_for_ajax_responses", keys.get(3).getKey());
        assertEquals("aeon.browser", keys.get(4).getKey());
        assertEquals("aeon.environment", keys.get(5).getKey());
        assertEquals("aeon.protocol", keys.get(6).getKey());
        assertEquals("aeon.timeout.ajax", keys.get(7).getKey());
        assertEquals("aeon.browser.maximize", keys.get(8).getKey());
        assertEquals("aeon.scroll_element_into_view", keys.get(9).getKey());
    }

    @Test
    void loadModuleSettings_completesSuccessfully() throws IOException {

        // Arrange
        webConfiguration.setBoolean(WebConfiguration.Keys.WAIT_FOR_AJAX_RESPONSES, false);
        webConfiguration.setBoolean(WebConfiguration.Keys.SCROLL_ELEMENT_INTO_VIEW, true);
        webConfiguration.setString(WebConfiguration.Keys.AJAX_TIMEOUT, "0");

        // Act
        webConfiguration.loadModuleSettings();

        // Assert
        assertTrue(webConfiguration.getBoolean(WebConfiguration.Keys.WAIT_FOR_AJAX_RESPONSES, true));
        assertFalse(webConfiguration.getBoolean(WebConfiguration.Keys.SCROLL_ELEMENT_INTO_VIEW, false));
        assertEquals("20", webConfiguration.getString(WebConfiguration.Keys.AJAX_TIMEOUT, "20"));
    }

    @Test
    void loadModuleSettings_throwsIOException() throws IOException {

        // Arrange
        WebConfiguration spyConfig = org.mockito.Mockito.spy(webConfiguration);
        when(spyConfig.getAeonCoreInputStream()).thenReturn(inputStream);
        when(inputStream.read(any())).thenThrow(IOException.class);

        // Act
        Executable executable = spyConfig::loadModuleSettings;

        // Assert
        assertThrows(IOException.class, executable);
    }
}
