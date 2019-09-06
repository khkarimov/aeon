package com.ultimatesoftware.aeon.extensions.appium;

import com.ultimatesoftware.aeon.core.common.exceptions.BrowserTypeNotRecognizedException;
import com.ultimatesoftware.aeon.core.common.interfaces.IBy;
import com.ultimatesoftware.aeon.core.common.mobile.AppType;
import com.ultimatesoftware.aeon.core.common.mobile.interfaces.IByMobile;
import com.ultimatesoftware.aeon.core.common.mobile.interfaces.IByMobileXPath;
import com.ultimatesoftware.aeon.core.common.mobile.selectors.ByMobile;
import com.ultimatesoftware.aeon.core.common.mobile.selectors.ByMobileId;
import com.ultimatesoftware.aeon.core.common.mobile.selectors.MobileSelectOption;
import com.ultimatesoftware.aeon.core.common.web.BrowserSize;
import com.ultimatesoftware.aeon.core.common.web.BrowserType;
import com.ultimatesoftware.aeon.core.common.web.WebSelectOption;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IBrowserType;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.extensions.selenium.QuadFunction;
import com.ultimatesoftware.aeon.extensions.selenium.SeleniumElement;
import com.ultimatesoftware.aeon.extensions.selenium.jquery.IJavaScriptFlowExecutor;
import com.ultimatesoftware.aeon.extensions.selenium.jquery.SeleniumScriptExecutor;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.*;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.SessionId;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

    @Test
    void closeApp_isOtherApp_throwsException() throws MalformedURLException {

        // Arrange
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
    void loseApp_isAndroidApp_callsCloseApp() throws MalformedURLException {

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
        AppiumAdapter appiumAdapterSpy = spy(appiumAdapter);

        // Act
        appiumAdapterSpy.closeApp();

        // Assert
        verify(appiumAdapterSpy, times(1)).getMobileWebDriver();

    }

    @Test
    void mobileSetPortrait_callsRotate() {

        // Arrange

        // Act
        appiumAdapter.mobileSetPortrait();

        // Assert
        verify(appiumDriver, times(1)).rotate(ScreenOrientation.PORTRAIT);
    }

    @Test
    void mobileSetLandscape_callsRotate() {

        // Arrange

        // Act
        appiumAdapter.mobileSetLandscape();

        // Assert
        verify(appiumDriver, times(1)).rotate(ScreenOrientation.LANDSCAPE);
    }

    @Test
    void mobileHideKeyboard_callsHideKeyboard() {

        // Arrange

        // Act
        appiumAdapter.mobileHideKeyboard();

        // Assert
        verify(appiumDriver, times(1)).hideKeyboard();
    }

    @Test
    void mobileLock_isAndroidApp_callsLockDevice() throws MalformedURLException {

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

        // Act
        appiumAdapter.mobileLock(1);

        // Assert
        verify(androidDriver, times(1)).lockDevice();
    }

    @Test
    void mobileLock_isIOSApp_callsLockDevice() throws MalformedURLException {

        // Arrange
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
    void mobileLock_isOtherApp_throwsException() throws MalformedURLException {

        // Arrange
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
    void mobileLock_emptyParameters_callsLockDevice() throws MalformedURLException {

        // Arrange
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
    void mobileSetGeoLocation_callsSetLocation() {

        // Arrange
        double latitude = 1, longitude = 1, altitude = 1;

        // Act
        appiumAdapter.mobileSetGeoLocation(latitude, longitude, altitude);

        // Assert
        verify(appiumDriver, times(1)).setLocation(refEq(new Location(latitude, longitude, altitude)));
    }

    @Test
    void swipe_trueParameters_callsPress() {

        // Arrange
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
    void swipe_falseParameters_callsPress() {

        // Arrange
        Dimension screenSize = new Dimension(5, 6);
        when(this.appiumDriver.manage()).thenReturn(this.remoteDriver);
        when(this.remoteDriver.window()).thenReturn(this.remoteWindow);
        when(this.remoteWindow.getSize()).thenReturn(screenSize);

        // Act
        appiumAdapter.swipe(false, false);

        // Assert
        verify(appiumDriver, times(1)).manage();
    }

    @Ignore
    @Test
    void recentNotificationDescriptionIs_throwsAppiumException() {

        // Arrange
        SeleniumElement seleniumElement = mock(SeleniumElement.class);
        WebElement webElement = mock(WebElement.class);

        Dimension screenSize = new Dimension(5, 6);
        when(this.appiumDriver.manage()).thenReturn(this.remoteDriver);
        when(this.remoteDriver.window()).thenReturn(this.remoteWindow);
        when(this.remoteWindow.getSize()).thenReturn(screenSize);

        doReturn(webElement).when(appiumDriver).findElement(org.openqa.selenium.By.xpath(anyString()));
        //doReturn(webElement).when(seleniumElement).getUnderlyingWebElement();

        // Act
        Executable action = () -> appiumAdapter.recentNotificationDescriptionIs("miss");

        // Assert
        Exception exception = assertThrows(AppiumException.class, action);
        assertEquals("Correct element was not found after all checks", exception);
    }

    @Test
    void setDate_androidApp_callsClickTwice() {

        // Arrange
        LocalDate date = LocalDate.now();
        WebElement webElement1 = mock(WebElement.class);
        WebElement webElement2 = mock(WebElement.class);
        WebElement webElement3 = mock(WebElement.class);
        WebElement webElement4 = mock(WebElement.class);

        doReturn(webElement1).when(appiumDriver).findElement(org.openqa.selenium.By.id("android:id/date_picker_header_date"));
        doReturn(webElement2).when(appiumDriver).findElement(org.openqa.selenium.By.id("android:id/date_picker_header_year"));
        doReturn(webElement3).when(appiumDriver).findElementByAccessibilityId(date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
        doReturn(webElement4).when(appiumDriver).findElement(org.openqa.selenium.By.id("android:id/button1"));

        List<String> monthList = Arrays.asList("", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        when(webElement1.getText()).thenReturn("     " + monthList.get(date.getMonthValue()));
        when(webElement2.getText()).thenReturn("" + date.getYear());

        AppiumAdapter appiumAdapterSpy = spy(appiumAdapter);

        // Act
        appiumAdapterSpy.setDate(date);

        // Assert
        verify(webElement3, times(1)).click();
        verify(webElement4, times(1)).click();
    }

    @Ignore
    @Test
    void setDate_notAndroidApp_callsGetMobileWebDriverTwice() throws MalformedURLException {

        // Arrange
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

        LocalDate date = LocalDate.now();

        SeleniumElement seleniumElement = mock(SeleniumElement.class);
        WebElement webElement1 = mock(WebElement.class);
        WebElement webElement2 = mock(WebElement.class);
        WebElement webElement3 = mock(WebElement.class);

        when(appiumDriver.findElement(org.openqa.selenium.By.xpath(ByMobile.xpath(anyString()).toString()))).thenReturn(webElement1);
        //doReturn(webElement1).when(appiumDriver).findElement(org.openqa.selenium.By.xpath(ByMobile.xpath(anyString()).toString()));
        //doReturn("Tag").when(webElement1).getTagName();
        when(webElement1.getTagName()).thenReturn("Tag");
        //doNothing().when(webElement1).sendKeys(date.format(DateTimeFormatter.ofPattern("MMMM")));

        //doReturn(webElement2).when(appiumDriver).findElement(org.openqa.selenium.By.xpath(ByMobile.xpath("//XCUIElementTypePickerWheel[2]").toString()));
        //doReturn("Tag").when(webElement2).getTagName();
        //doNothing().when(webElement2).sendKeys(date.format(DateTimeFormatter.ofPattern("MMMM")));

        //doReturn(webElement3).when(appiumDriver).findElement(org.openqa.selenium.By.xpath(ByMobile.xpath("//XCUIElementTypePickerWheel[3]").toString()));
        //doReturn("Tag").when(webElement3).getTagName();
        //doNothing().when(webElement3).sendKeys(date.format(DateTimeFormatter.ofPattern("MMMM")));


        AppiumAdapter appiumAdapterSpy = spy(appiumAdapter);

        // Act
        appiumAdapterSpy.setDate(date);

        // Assert
        verify(appiumAdapterSpy, times(1)).click(any(WebControl.class));
    }

    @Test
    void mobileSelect_androidApp_callsClick() {
        // Arrange
        WebElement webElement = mock(WebElement.class);
        String value = "";

        doReturn(webElement).when(appiumDriver).findElement(org.openqa.selenium.By.xpath(ByMobile.xpath("//android.widget.CheckedTextView[@text='']").toString()));

        // Act
        appiumAdapter.mobileSelect(MobileSelectOption.TEXT, value);

        // Assert
        verify(webElement, times(1)).click();
    }

    @Ignore
    @Test
    void mobileSelect_notAndroidApp_callsClick() throws MalformedURLException {

        // Arrange
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

        WebElement webElement1 = mock(WebElement.class);
        WebElement webElement2 = mock(WebElement.class);
        String value = "";

        doReturn(webElement1).when(appiumDriver).findElement(org.openqa.selenium.By.xpath(ByMobile.xpath("//XCUIElementTypePickerWheel[1]").toString()));
        doReturn("tag").when(webElement1).getTagName();
        //doReturn(webElement2).when(appiumDriver).findElementByAccessibilityId(ByMobile.accessibilityId("Done").toString());
        //doReturn("tag").when(webElement2).getTagName();

        // Act
        appiumAdapter.mobileSelect(MobileSelectOption.TEXT, value);

        // Assert
        verify(webElement1, times(1)).sendKeys(value);
        //verify(webElement2, times(1)).click();
    }

    @Ignore
    @Test
    void scrollElementIntoView_callsScrollElementIntoView() {
        // Arrange
        AppiumAdapter appiumAdapterSpy = spy(appiumAdapter);
        IByWeb ibyweb = mock(IByWeb.class);

        // Act
        appiumAdapter.scrollElementIntoView(ibyweb);

        // Assert


    }

    @Test
    void acceptAlert_callsAccept() {

        // Arrange
        WebDriver.TargetLocator target = mock(WebDriver.TargetLocator.class);
        Alert alert = mock(Alert.class);

        when(appiumDriver.switchTo()).thenReturn(target);
        when(target.alert()).thenReturn(alert);

        // Act
        appiumAdapter.acceptAlert();

        // Assert
        verify(alert, times(1)).accept();
    }

    @Test
    void dismissAlert_callsDismiss() {

        // Arrange
        WebDriver.TargetLocator target = mock(WebDriver.TargetLocator.class);
        Alert alert = mock(Alert.class);

        when(appiumDriver.switchTo()).thenReturn(target);
        when(target.alert()).thenReturn(alert);

        // Act
        appiumAdapter.dismissAlert();

        // Assert
        verify(alert, times(1)).dismiss();
    }

    @Test
    void acceptOrDismissPermissionDialog_isAndroidAppAcceptTrue_callsClick() {

        // Arrange
        WebElement webElement = mock(WebElement.class);

        doReturn(webElement).when(appiumDriver).findElement(org.openqa.selenium.By.id(ByMobile.id("com.android.packageinstaller:id/permission_allow_button").toString()));

        // Act
        appiumAdapter.acceptOrDismissPermissionDialog(true);

        // Asssert
        verify(webElement, times(1)).click();
    }

    @Test
    void acceptOrDismissPermissionDialog_isNotAndroidAppAcceptTrue_callsAcceptAlert() throws MalformedURLException {

        // Arrange
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

        AppiumAdapter appiumAdapterSpy = spy(appiumAdapter);
        doNothing().when(appiumAdapterSpy).acceptAlert();

        // Act
        appiumAdapterSpy.acceptOrDismissPermissionDialog(true);

        // Assert
        verify(appiumAdapterSpy, times(1)).acceptAlert();
    }

    @Test
    void acceptOrDismissPermissionDialog_isAndroidAppAcceptFalse_callsClick() {

        // Arrange
        WebElement webElement = mock(WebElement.class);

        doReturn(webElement).when(appiumDriver).findElement(org.openqa.selenium.By.id(ByMobile.id("com.android.packageinstaller:id/permission_deny_button").toString()));

        // Act
        appiumAdapter.acceptOrDismissPermissionDialog(false);

        // Assert
        verify(webElement, times(1)).click();
    }

    @Test
    void acceptOrDismissPermissionDialog_isNotAndroidAppAcceptFalse_callsDismissAlert() throws MalformedURLException {

        // Arrange
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

        AppiumAdapter appiumAdapterSpy = spy(appiumAdapter);
        doNothing().when(appiumAdapterSpy).dismissAlert();

        // Act
        appiumAdapterSpy.acceptOrDismissPermissionDialog(false);

        // Assert
        verify(appiumAdapterSpy, times(1)).dismissAlert();
    }

    @Test
    void click_elementIsIByMobile_callsClick() {

        // Arrange
        SeleniumElement element = mock(SeleniumElement.class);
        ByMobileId byMobile = mock(ByMobileId.class);
        AppiumAdapter appiumAdapterSpy = spy(appiumAdapter);

        doReturn(byMobile).when(element).getSelector();

        // Act
        appiumAdapterSpy.click(element);

        // Assert
        verify(element, times(1)).click();
        verify(appiumAdapterSpy, times(2)).getMobileWebDriver();
    }

    @Test
    void click_elementIsNotIByMobile_callsClick() {

        // Arrange
        SeleniumElement element = mock(SeleniumElement.class);

        // Act
        appiumAdapter.click(element);

        // Assert
        verify(element, times(1)).click();
    }

    @Ignore
    @Test
    void mobileClick_() {

        // Arrange
        AppiumAdapter appiumAdapterSpy = spy(appiumAdapter);
        SeleniumElement seleniumElement = mock(SeleniumElement.class);
        WebElement webElement = mock(WebElement.class);
        WebDriver.Options options = mock(WebDriver.Options.class);
        WebDriver.Window window = mock(WebDriver.Window.class);
        TouchAction touch = mock(TouchAction.class);
        QuadFunction quad = mock(QuadFunction.class);
        SeleniumScriptExecutor exe = mock(SeleniumScriptExecutor.class);

        Point point = new Point(1, 1);
        Dimension dim = new Dimension(1, 1);
        Object obj = new Object();

        //doReturn(1l).when(appiumAdapterSpy).executeScript("return screen.availWidth");
        //doReturn(1l).when(appiumAdapterSpy).executeScript("return screen.availHeight");
        //doNothing().when(appiumAdapterSpy).executeScript("return screen.availWidth");
        doReturn(quad).when(javaScriptFlowExecutor).getExecutor();
        doReturn(1l).when(quad).apply(exe, "return screen.availWidth", Arrays.asList(obj));
        doReturn(webElement).when(seleniumElement).getUnderlyingWebElement();
        doReturn(point).when(webElement).getLocation();
        doReturn(dim).when(webElement).getSize();

        doReturn(options).when(appiumDriver).manage();
        doReturn(window).when(options).window();
        doReturn(dim).when(window).getSize();

        //doNothing().when(appiumDriver).performTouchAction(any(Touch));

        // Act
        appiumAdapterSpy.mobileClick(seleniumElement);

        // Assert
        verify(appiumDriver, times(1)).performTouchAction(touch);

    }

    @Test
    void switchToWebView_selectorIsNull_callsSwitchToWebViewContext() {

        // Arrange

        // Act
        appiumAdapter.switchToWebView(null);
        String context = appiumDriver.getContext();

        // Assert
        verify(appiumDriver, times(1)).context(context);
    }

    @Test
    void switchToWebView_hasOneContext_throwsAppiumException() {

        // Arrange
        ByMobile byMobile = mock(ByMobile.class);

        Set<String> set = new LinkedHashSet<String>();
        set.add("context0");

        when(appiumDriver.getContextHandles()).thenReturn(set);

        // Act
        Executable action = () -> appiumAdapter.switchToWebView(byMobile);

        // Assert
        Exception exception = assertThrows(AppiumException.class, action);
        assertEquals("Unable to find matching web view", exception.getMessage());
    }

    @Test
    void switchToWebView_hasMultipleContexts_callsFindElement_throwsNoSuchElementException() {

        // Arrange
        ByMobileId byMobile = mock(ByMobileId.class);

        Set<String> set = new LinkedHashSet<>();
        set.add("WEBVIEW");
        set.add("Context1");
        set.add("Context2");

        org.openqa.selenium.NoSuchElementException e = new NoSuchElementException("", new Throwable());

        when(appiumDriver.getContextHandles()).thenReturn(set);
        when(appiumDriver.findElement(org.openqa.selenium.By.id(byMobile.toString()))).thenThrow(e);

        // Act
        Executable action = () -> appiumAdapter.switchToWebView(byMobile);

        // Assert
        Exception exception = assertThrows(AppiumException.class, action);
        verify(appiumDriver, times(1)).findElement(org.openqa.selenium.By.id(byMobile.toString()));
        assertEquals("Unable to find matching web view", exception.getMessage());
    }

    @Test
    void switchToWebView_hasMultipleContexts_callsFindElement() {

        // Arrange
        ByMobileId byMobile = mock(ByMobileId.class);
        WebElement webElement = mock(WebElement.class);

        Set<String> set = new LinkedHashSet<>();
        set.add("WEBVIEW");
        set.add("Context1");
        set.add("Context2");

        when(appiumDriver.getContextHandles()).thenReturn(set);
        when(appiumDriver.findElement(org.openqa.selenium.By.id(byMobile.toString()))).thenReturn(webElement);

        // Act
        appiumAdapter.switchToWebView(byMobile);

        // Assert
        verify(appiumDriver, times(1)).findElement(org.openqa.selenium.By.id(byMobile.toString()));
    }

    @Test
    void set_isAIByMobile_callsSendKeys() {

        // Arrange
        SeleniumElement seleniumElement = mock(SeleniumElement.class);
        ByMobileId byMobile = mock(ByMobileId.class);
        WebElement webElement = mock(WebElement.class);
        AppiumAdapter appiumAdapterSpy = spy(appiumAdapter);

        doReturn(byMobile).when(seleniumElement).getSelector();
        doReturn(webElement).when(seleniumElement).getUnderlyingWebElement();

        // Act
        appiumAdapter.set(seleniumElement, WebSelectOption.VALUE, "");

        // Assert
        verify(webElement, times(1)).sendKeys("");
    }

    @Test
    void set_isNotAIByMobile_callsSendKeys() {

        // Arrange
        SeleniumElement seleniumElement = mock(SeleniumElement.class);
        WebElement webElement = mock(WebElement.class);

        doReturn(webElement).when(seleniumElement).getUnderlyingWebElement();
        doReturn("Name").when(webElement).getTagName();

        // Act
        appiumAdapter.set(seleniumElement, WebSelectOption.VALUE, "");

        // Assert
        verify(webElement, times(1)).sendKeys("");
    }

    @Test
    void quit_isExpectedBrowserType_callsCloseApp_callsQuit() {

        // Arrange

        // Act
        appiumAdapter.quit();

        // Assert
        verify(appiumDriver, times(1)).closeApp();
        verify(appiumDriver, times(1)).quit();
    }

    @Test
    void quit_isNotExpectedBrowserType_callsQuit() throws MalformedURLException {

        // Arrange
        SessionId sessionId = mock(SessionId.class);

        when(this.configuration.getBrowserType()).thenReturn(BrowserType.CHROME);
        this.appiumAdapter = new AppiumAdapter(
                this.appiumDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                new URL("http://host/wd/hub"),
                this.loggingPreferences
        );

        when(appiumDriver.getSessionId()).thenReturn(sessionId);

        // Act
        appiumAdapter.quit();

        // Assert
        verify(appiumDriver, times(1)).quit();
    }


    @Test
    void findElement_findByNotAIByMobile_throwsUnsupportedOperationException() {

        // Arrange
        IBy iby = mock(IBy.class);

        // Act
        Executable action = () -> appiumAdapter.findElement(iby);

        // Assert
        assertThrows(UnsupportedOperationException.class, action);
    }

    @Test
    void findElement_findByIsAIByMobileXPath_throwsNoSuchElementException() {

        // Arrange
        IByMobileXPath iby = mock(IByMobileXPath.class);

        org.openqa.selenium.NoSuchElementException e = new org.openqa.selenium.NoSuchElementException("", new Throwable());

        when(appiumDriver.findElement(org.openqa.selenium.By.xpath(iby.toString()))).thenThrow(e);

        // Act
        Executable action = () -> appiumAdapter.findElement(iby);

        // Assert
        assertThrows(com.ultimatesoftware.aeon.core.common.exceptions.NoSuchElementException.class, action);
    }

    @Test
    void findElement_findByIsOther_throwsNoSuchElementException() {

        // Arrange
        IByMobile iby = mock(IByMobile.class);

        org.openqa.selenium.NoSuchElementException e = new org.openqa.selenium.NoSuchElementException("", new Throwable());

        when(appiumDriver.findElementByAccessibilityId(iby.toString())).thenThrow(e);

        // Act
        Executable action = () -> appiumAdapter.findElement(iby);

        // Assert
        assertThrows(com.ultimatesoftware.aeon.core.common.exceptions.NoSuchElementException.class, action);

    }

}
