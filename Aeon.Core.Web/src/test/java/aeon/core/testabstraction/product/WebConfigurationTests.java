package aeon.core.testabstraction.product;

import aeon.core.common.web.BrowserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class WebConfigurationTests {

    private WebConfiguration webConfiguration;

    @BeforeEach
    void setUp() throws IOException, IllegalAccessException {
        webConfiguration = new WebConfiguration();
    }

    @Test
    void getAndSetBrowserType_returnsBrowserType() {

        // Arrange

        // Act
        webConfiguration.setBrowserType(BrowserType.Chrome);

        // Assert
        assertEquals(BrowserType.Chrome, webConfiguration.getBrowserType());
    }

    @Test
    void getConfigurationFields_returnsKeys() throws IllegalAccessException {

        // Arrange

        // Act
        List<Field> keys = webConfiguration.getConfigurationFields().stream()
                .filter(field -> !field.isSynthetic())
                .collect(Collectors.toList());

        // Assert
        assertEquals("aeon.timeout", keys.get(0).get(webConfiguration));
        assertEquals("aeon.throttle", keys.get(1).get(webConfiguration));
        assertEquals("aeon.implicit_reporting", keys.get(2).get(webConfiguration));
        assertEquals("aeon.wait_for_ajax_responses", keys.get(3).get(webConfiguration));
        assertEquals("aeon.browser", keys.get(4).get(webConfiguration));
        assertEquals("aeon.environment", keys.get(5).get(webConfiguration));
        assertEquals("aeon.protocol", keys.get(6).get(webConfiguration));
        assertEquals("aeon.timeout.ajax", keys.get(7).get(webConfiguration));
        assertEquals("aeon.browser.maximize", keys.get(8).get(webConfiguration));
        assertEquals("aeon.scroll_element_into_view", keys.get(9).get(webConfiguration));
    }

    @Test
    void loadModuleSettings_completesSuccessfully() throws IOException {

        // Arrange
        webConfiguration.setBoolean("aeon.wait_for_ajax_responses", false);
        webConfiguration.setBoolean("aeon.scroll_element_into_view", true);
        webConfiguration.setString("aeon.timeout.ajax", "0");

        // Act
        webConfiguration.loadModuleSettings();

        // Assert
        assertEquals(true, webConfiguration.getBoolean("aeon.wait_for_ajax_responses", true));
        assertEquals(false, webConfiguration.getBoolean("aeon.scroll_element_into_view", false));
        assertEquals("20", webConfiguration.getString("aeon.timeout.ajax", "20"));
    }

    @Test
    void loadModuleSettings_throwsIOException() throws IOException {

        // Arrange
        WebConfiguration spyConfig = org.mockito.Mockito.spy(webConfiguration);
        when(spyConfig.getAeonCoreInputStream()).thenThrow(IOException.class);

        // Act
        Executable executable = () -> spyConfig.loadModuleSettings();

        // Assert
        assertThrows(IOException.class, executable);
    }
}
