package com.ultimatesoftware.aeon.extensions.appium;

import com.ultimatesoftware.aeon.core.common.AeonConfigKey;
import com.ultimatesoftware.aeon.core.common.exceptions.ConfigurationException;
import com.ultimatesoftware.aeon.core.common.web.BrowserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class AppiumConfigurationTests {

    @Mock
    InputStream inputStream;

    private AppiumConfiguration appiumConfiguration;

    @BeforeEach
    void setUp() {
        appiumConfiguration = new AppiumConfiguration();
    }


    @Test
    void getConfigurationFields_returnsKeys() {

        //Arrange

        //Act
        List<AeonConfigKey> keys = new ArrayList<>(appiumConfiguration.getConfigurationFields());

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
        assertEquals("aeon.selenium.language", keys.get(10).getKey());
        assertEquals("aeon.selenium.use_mobile_user_agent", keys.get(11).getKey());
        assertEquals("aeon.selenium.proxy_location", keys.get(12).getKey());
        assertEquals("aeon.selenium.grid.url", keys.get(13).getKey());
        assertEquals("aeon.selenium.ie.ensure_clean_environment", keys.get(14).getKey());
        assertEquals("aeon.browser.maximize.fallback", keys.get(15).getKey());
        assertEquals("aeon.selenium.chrome.driver", keys.get(16).getKey());
        assertEquals("aeon.selenium.chrome.binary", keys.get(17).getKey());
        assertEquals("aeon.selenium.chrome.headless", keys.get(18).getKey());
        assertEquals("aeon.selenium.chrome.mobile.emulation.device", keys.get(19).getKey());
        assertEquals("aeon.selenium.firefox.driver", keys.get(20).getKey());
        assertEquals("aeon.selenium.firefox.binary", keys.get(21).getKey());
        assertEquals("aeon.selenium.opera.driver", keys.get(22).getKey());
        assertEquals("aeon.selenium.opera.binary", keys.get(23).getKey());
        assertEquals("aeon.selenium.ie.driver", keys.get(24).getKey());
        assertEquals("aeon.selenium.ie.logging.level", keys.get(25).getKey());
        assertEquals("aeon.selenium.ie.logging.path", keys.get(26).getKey());
        assertEquals("aeon.selenium.edge.driver", keys.get(27).getKey());
        assertEquals("aeon.appium.device_name", keys.get(28).getKey());
        assertEquals("aeon.appium.platform_version", keys.get(29).getKey());
        assertEquals("aeon.appium.udid", keys.get(30).getKey());
        assertEquals("aeon.selenium.logging.type.browser", keys.get(31).getKey());
        assertEquals("aeon.selenium.logging.type.client", keys.get(32).getKey());
        assertEquals("aeon.selenium.logging.type.driver", keys.get(33).getKey());
        assertEquals("aeon.selenium.logging.type.performance", keys.get(34).getKey());
        assertEquals("aeon.selenium.logging.type.server", keys.get(35).getKey());
        assertEquals("aeon.selenium.logging.directory", keys.get(36).getKey());
        assertEquals("aeon.appium.app", keys.get(37).getKey());
        assertEquals("aeon.appium.device_name", keys.get(38).getKey());
        assertEquals("aeon.appium.platform_version", keys.get(39).getKey());
        assertEquals("aeon.appium.driver_context", keys.get(40).getKey());
        assertEquals("aeon.appium.webview.timeout", keys.get(41).getKey());
        assertEquals("aeon.appium.crosswalkpatch", keys.get(42).getKey());
        assertEquals("aeon.appium.automation_name", keys.get(43).getKey());
        assertEquals("aeon.appium.android.app_package", keys.get(44).getKey());
        assertEquals("aeon.appium.android.app_activity", keys.get(45).getKey());
        assertEquals("aeon.appium.android.avd_name", keys.get(46).getKey());
        assertEquals("aeon.appium.ios.bundle_id", keys.get(47).getKey());
        assertEquals("aeon.appium.ios.wda_port", keys.get(48).getKey());
        assertEquals("aeon.appium.udid", keys.get(49).getKey());
    }

    @Test
    void setBrowserType_validBrowser_returnsBrowserType() {

        // Arrange

        // Act
        appiumConfiguration.setBrowserType("Chrome");

        // Assert
        assertEquals(BrowserType.CHROME, appiumConfiguration.getBrowserType());
    }

    @Test
    void setBrowserType_invalidBrowser_throwsConfigurationException() {

        // Arrange

        // Act

        // Assert
        assertThrows(ConfigurationException.class, () -> appiumConfiguration.setBrowserType("Invalid Browser"));
    }

    @Test
    void loadPluginSettings_completesSuccessfully() throws IOException {

        // Arrange
        appiumConfiguration.setString(AppiumConfiguration.Keys.WEBVIEW_TIMEOUT, "0");

        // Act
        appiumConfiguration.loadPluginSettings();

        // Assert
        assertEquals("30", appiumConfiguration.getString(AppiumConfiguration.Keys.WEBVIEW_TIMEOUT, "30"));
    }

    @Test
    void loadPluginSettings_throwsIOException() throws IOException {

        //Arrange
        AppiumConfiguration spyConfig = Mockito.spy(appiumConfiguration);
        when(spyConfig.getAppiumInputStream()).thenReturn(inputStream);
        when(inputStream.read(any())).thenThrow(IOException.class);

        //Act
        Executable executable = spyConfig::loadPluginSettings;

        //Assert
        assertThrows(IOException.class, executable);
    }
}
