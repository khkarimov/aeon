package com.ultimatesoftware.aeon.extensions.appium;

import com.ultimatesoftware.aeon.core.common.exceptions.BrowserTypeNotRecognizedException;
import com.ultimatesoftware.aeon.core.common.mobile.AppType;
import com.ultimatesoftware.aeon.core.common.mobile.selectors.MobileSelectOption;
import com.ultimatesoftware.aeon.core.common.web.BrowserSize;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IBrowserType;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.extensions.selenium.SeleniumElement;
import com.ultimatesoftware.aeon.extensions.selenium.jquery.IJavaScriptFlowExecutor;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.*;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.logging.LoggingPreferences;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppiumAdapterTests {

    private AppiumAdapter appiumAdapter;

    public enum OtherAppType implements IBrowserType {
        Other_App("OtherApp");

        private final String key;

        OtherAppType(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    @Mock
    private AppiumDriver appiumDriver;

    @Mock
    private AndroidDriver androidDriver;

    @Mock
    private IOSDriver iosDriver;

    @Mock
    private WebDriver.Options remoteDriver;

    @Mock
    private WebDriver.Window remoteWindow;

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
    void appiumAdapter_getMobileWebDriver_webDriverIsAnAppiumDriver_returnsAppiumDriver() {
        // Arrange

        // Act
        AppiumDriver appiumDriver = this.appiumAdapter.getMobileWebDriver();

        // Assert
        assertEquals(this.appiumDriver, appiumDriver);
    }

    @Test
    void appiumAdapter_getMobileWebDriver_webDriverIsNotAnAppiumDriver_throwsException() throws MalformedURLException {
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
    void appiumAdapter_closeApp_isIOSApp_throwsException() throws MalformedURLException {
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

    @Test
    void appiumAdapter_closeApp_isOtherApp_throwsException() throws MalformedURLException {
        // Assemble
        when(this.configuration.getBrowserType()).thenReturn(OtherAppType.Other_App);
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
        assertThrows(BrowserTypeNotRecognizedException.class, action);
    }

    @Test
    void appiumAdapter_loseApp_isAndroidApp_callsCloseApp() throws MalformedURLException {
        // Arrange
        this.appiumAdapter = new AppiumAdapter(
                this.androidDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                new URL("http://host/wd/hub"),
                this.loggingPreferences
        );
        AppiumAdapter appiumAdapterSpy = Mockito.spy(appiumAdapter);

        // Act
        appiumAdapterSpy.closeApp();

        // Assert
        verify(appiumAdapterSpy, times(1)).getMobileWebDriver();

    }

    @Test
    void appiumAdapter_mobileSetPortrait_callsRotate() {
        // Assemble

        // Act
        appiumAdapter.mobileSetPortrait();

        // Assert
        verify(appiumDriver, times(1)).rotate(ScreenOrientation.PORTRAIT);
    }

    @Test
    void appiumAdapter_mobileSetLandscape_callsRotate() {
        // Assemble

        // Act
        appiumAdapter.mobileSetLandscape();

        // Assert
        verify(appiumDriver, times(1)).rotate(ScreenOrientation.LANDSCAPE);
    }

    @Test
    void appiumAdapter_mobileHideKeyboard_callsHideKeyboard() {
        // Assemble

        // Act
        appiumAdapter.mobileHideKeyboard();

        // Assert
        verify(appiumDriver, times(1)).hideKeyboard();
    }

    @Test
    void appiumAdapter_mobileLock_isAndroidApp_callsLockDevice() throws MalformedURLException {
        // Assemble
        this.appiumAdapter = new AppiumAdapter(
                this.androidDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                new URL("http://host/wd/hub"),
                this.loggingPreferences
        );

        // Act
        appiumAdapter.mobileLock(1);

        // Assert
        verify(androidDriver, times(1)).lockDevice();
    }

    @Test
    void appiumAdapter_mobileLock_isIOSApp_callsLockDevice() throws MalformedURLException {
        // Assemble
        when(this.configuration.getBrowserType()).thenReturn(AppType.IOS_HYBRID_APP);
        this.appiumAdapter = new AppiumAdapter(
                this.iosDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                new URL("http://host/wd/hub"),
                this.loggingPreferences
        );

        // Act
        appiumAdapter.mobileLock(1);

        // Assert
        verify(iosDriver, times(1)).lockDevice(Duration.ofSeconds(1));
    }

    @Test
    void appiumAdapter_mobileLock_isOtherApp_throwsException() throws MalformedURLException {
        // Assemble
        when(this.configuration.getBrowserType()).thenReturn(OtherAppType.Other_App);
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
        Executable action = () -> appiumAdapter.mobileLock(1);

        // Assert
        assertThrows(UnsupportedCommandException.class, action);
    }

    @Test
    void appiumAdapter_mobileLock_emptyParameters_callsLockDevice() throws MalformedURLException {
        // Assemble
        when(this.configuration.getBrowserType()).thenReturn(AppType.IOS_HYBRID_APP);
        this.appiumAdapter = new AppiumAdapter(
                this.iosDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                new URL("http://host/wd/hub"),
                this.loggingPreferences
        );

        // Act
        appiumAdapter.mobileLock();

        // Assert
        verify(iosDriver, times(1)).lockDevice(Duration.ofSeconds(0));
    }

    @Test
    void appiumAdapter_mobileSetGeoLocation_callsSetLocation() {
        // Assemble
        double latitude = 1, longitude = 1, altitude = 1;

        // Act
        appiumAdapter.mobileSetGeoLocation(latitude, longitude, altitude);

        // Assert
        verify(appiumDriver, times(1)).setLocation(refEq(new Location(latitude, longitude, altitude)));
    }

    @Test
    void appiumAdapter_swipe_callsPress_trueParameters() {
        // Assemble
        Dimension screenSize = new Dimension(5, 6);
        when(this.appiumDriver.manage()).thenReturn(this.remoteDriver);
        when(this.remoteDriver.window()).thenReturn(this.remoteWindow);
        when(this.remoteWindow.getSize()).thenReturn(screenSize);

        // Act
        appiumAdapter.swipe(true, true);

        // Assert
        verify(appiumDriver, times(1)).manage();
    }

    @Test
    void appiumAdapter_swipe_callsPress_falseParameters() {
        // Assemble
        Dimension screenSize = new Dimension(5, 6);
        when(this.appiumDriver.manage()).thenReturn(this.remoteDriver);
        when(this.remoteDriver.window()).thenReturn(this.remoteWindow);
        when(this.remoteWindow.getSize()).thenReturn(screenSize);

        // Act
        appiumAdapter.swipe(false, false);

        // Assert
        verify(appiumDriver, times(1)).manage();
    }

    /*@Test
    void appiumAdapter_recentNotificationDescriptionIs_throwsAppiumException() {
        // Assemble
        Dimension screenSize = new Dimension(5, 6);
        when(this.appiumDriver.manage()).thenReturn(this.remoteDriver);
        when(this.remoteDriver.window()).thenReturn(this.remoteWindow);
        when(this.remoteWindow.getSize()).thenReturn(screenSize);

        // Act
        Executable action = () -> appiumAdapter.recentNotificationDescriptionIs("miss");

        // Assert
        Exception exception = assertThrows(AppiumException.class, action);
        assertEquals("Correct element was not found after all checks", exception);
    }*/

    @Test
    void appiumAdapter_setDate_androidApp() {
        // Assemble
        LocalDate date = LocalDate.now();
        AppiumAdapter appiumAdapterSpy = spy(appiumAdapter);
        SeleniumElement seleniumElement = mock(SeleniumElement.class);
        WebElement webElement1 = mock(WebElement.class);
        WebElement webElement2 = mock(WebElement.class);
        WebElement webElement3 = mock(WebElement.class);
        WebElement webElement4 = mock(WebElement.class);
        IByWeb byMobile = mock(IByWeb.class);

        doReturn(webElement1).when(appiumDriver).findElement(org.openqa.selenium.By.id("android:id/date_picker_header_date"));
        doReturn(webElement2).when(appiumDriver).findElement(org.openqa.selenium.By.id("android:id/date_picker_header_year"));
        doReturn(webElement3).when(appiumDriver).findElementByAccessibilityId(date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
        doReturn(webElement4).when(appiumDriver).findElement(org.openqa.selenium.By.id("android:id/button1"));

        List<String> monthList = Arrays.asList("", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        when(webElement1.getText()).thenReturn("     " + monthList.get(date.getMonthValue()));
        when(webElement2.getText()).thenReturn("" + date.getYear());

        when(seleniumElement.getSelector()).thenReturn(byMobile);

        //doReturn(androidDriver).when(appiumAdapterSpy).getMobileWebDriver();
        //doReturn(appiumDriver).when(appiumDriver).context("NATIVE_APP");

        // Act
        appiumAdapterSpy.setDate(date);

        // Assert
        verify(appiumAdapterSpy, times(2)).click(seleniumElement);
    }

    @Test
    void appiumAdapter_mobileSelect_() {
        // Assemble
        AppiumAdapter appiumAdapterSpy = mock(AppiumAdapter.class);

        // Act
        appiumAdapterSpy.mobileSelect(any(MobileSelectOption.class), anyString());

        // Assert
        verify(appiumAdapterSpy, times(1)).click(any(SeleniumElement.class));

    }
}
