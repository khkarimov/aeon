package com.ultimatesoftware.aeon.extensions.appium;

import com.ultimatesoftware.aeon.core.common.mobile.AppType;
import com.ultimatesoftware.aeon.core.common.web.BrowserSize;
import com.ultimatesoftware.aeon.extensions.selenium.jquery.IJavaScriptFlowExecutor;
import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LoggingPreferences;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppiumAdapterTests {

    private AppiumAdapter appiumAdapter;

    @Mock
    private AppiumDriver appiumDriver;

    @Mock
    private IJavaScriptFlowExecutor javaScriptFlowExecutor;

    @Mock
    private IJavaScriptFlowExecutor asyncJavaScriptFlowExecutor;

    @Mock
    private AppiumConfiguration configuration;

    @Mock
    private LoggingPreferences loggingPreferences;

    @BeforeEach
    void setup() throws MalformedURLException {
        when(this.configuration.getBrowserType()).thenReturn(AppType.ANDROID_HYBRID_APP);
        this.appiumAdapter = new AppiumAdapter(
                this.appiumDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                new URL("http://host/wd/hub"),
                this.loggingPreferences
        );
    }

    @Test
    void getMobileWebDriver_webDriverIsAnAppiumDriver_returnsAppiumDriver() {

        // Arrange

        // Act
        AppiumDriver appiumDriver = this.appiumAdapter.getMobileWebDriver();

        // Assert
        assertEquals(this.appiumDriver, appiumDriver);
    }

    @Test
    void getMobileWebDriver_webDriverIsNotAnAppiumDriver_throwsException() throws MalformedURLException {

        // Arrange

        // Act
        Executable action = () -> {
            this.appiumAdapter = new AppiumAdapter(
                    mock(WebDriver.class),
                    this.javaScriptFlowExecutor,
                    this.asyncJavaScriptFlowExecutor,
                    this.configuration,
                    BrowserSize.FULL_HD,
                    new URL("http://host/wd/hub"),
                    this.loggingPreferences
            );
            this.appiumAdapter.getMobileWebDriver();
        };

        // Assert
        Exception exception = assertThrows(AppiumException.class, action);
        assertEquals("This command is only supported by an AppiumDriver", exception.getMessage());
    }

    @Test
    void closeApp_isIOSApp_throwsException() throws MalformedURLException {

        // Arrange
        when(this.configuration.getBrowserType()).thenReturn(AppType.IOS_HYBRID_APP);
        this.appiumAdapter = new AppiumAdapter(
                this.appiumDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                new URL("http://host/wd/hub"),
                this.loggingPreferences
        );

        // Act
        Executable action = () -> this.appiumAdapter.closeApp();

        // Assert
        Exception exception = assertThrows(AppiumException.class, action);
        assertEquals("Automated pressing of home button currently not supported on IOS", exception.getMessage());
    }
}
