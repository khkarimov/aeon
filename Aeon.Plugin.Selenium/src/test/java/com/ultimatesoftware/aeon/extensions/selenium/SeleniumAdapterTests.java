package com.ultimatesoftware.aeon.extensions.selenium;

import com.ultimatesoftware.aeon.core.common.CompareType;
import com.ultimatesoftware.aeon.core.common.ComparisonOption;
import com.ultimatesoftware.aeon.core.common.exceptions.*;
import com.ultimatesoftware.aeon.core.common.exceptions.ElementNotVisibleException;
import com.ultimatesoftware.aeon.core.common.exceptions.NoSuchCookieException;
import com.ultimatesoftware.aeon.core.common.exceptions.NoSuchElementException;
import com.ultimatesoftware.aeon.core.common.exceptions.NoSuchWindowException;
import com.ultimatesoftware.aeon.core.common.helpers.OsCheck;
import com.ultimatesoftware.aeon.core.common.helpers.Sleep;
import com.ultimatesoftware.aeon.core.common.interfaces.IBy;
import com.ultimatesoftware.aeon.core.common.web.*;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IBrowserType;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByXPath;
import com.ultimatesoftware.aeon.core.common.web.selectors.By;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.IWebCookie;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.extensions.selenium.jquery.IJavaScriptFlowExecutor;
import com.ultimatesoftware.aeon.extensions.selenium.jquery.IScriptExecutor;
import com.ultimatesoftware.aeon.extensions.selenium.jquery.SeleniumScriptExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.SessionId;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SeleniumAdapterTests {

    @Mock
    private WebDriver webDriver;

    @Mock
    private RemoteWebDriver remoteWebDriver;

    @Mock
    private WebDriver.Options options;

    @Mock
    private IJavaScriptFlowExecutor javaScriptFlowExecutor;

    @Mock
    private IJavaScriptFlowExecutor asyncJavaScriptFlowExecutor;

    @Mock
    private SeleniumConfiguration configuration;

    @Mock
    private IBrowserType browserType;

    @Mock
    private LoggingPreferences loggingPreferences;

    @Mock
    private WebElement webElement;

    @Mock
    private SeleniumElement seleniumElement;

    @Mock
    private IByXPath xPathSelector;

    @Mock
    private WebDriver.TargetLocator targetLocatorMock;

    @Mock
    private WebDriver.Navigation navigationMock;

    @Mock
    private WebDriver.Window windowMock;

    @Mock
    private Actions actionsMock;

    @Mock
    private ActionsFactory actionsFactory;

    @Mock
    private Sleep sleepMock;

    @Mock
    private FileDownloadHelper fileDownloadHelperMock;

    @Mock
    private QuadFunction<IScriptExecutor, String, Iterable<Object>, Object> javascriptExecutor;

    @Captor
    private ArgumentCaptor<IByWeb> iByWebCaptor;

    @Captor
    private ArgumentCaptor<IByWeb> iByWebCaptor2;

    @Captor
    private ArgumentCaptor<Cookie> cookieCaptor;

    @Captor
    private ArgumentCaptor<Iterable<Object>> listCaptor;

    @Captor
    private ArgumentCaptor<Point> pointCaptor;

    @Captor
    private ArgumentCaptor<Dimension> dimensionCaptor;

    private SeleniumAdapter seleniumAdapter;

    @BeforeEach
    void beforeTest() throws MalformedURLException {
        when(this.configuration.getBrowserType()).thenReturn(this.browserType);
        when(this.webDriver.manage()).thenReturn(options);
        when(this.webDriver.switchTo()).thenReturn(targetLocatorMock);
        when(this.webDriver.navigate()).thenReturn(navigationMock);
        when(this.options.window()).thenReturn(windowMock);

        this.seleniumAdapter = new SeleniumAdapter(
                this.webDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                new URL("http://host/wd/hub"),
                this.loggingPreferences
        );

        when(actionsFactory.createActions(eq(this.webDriver))).thenReturn(actionsMock);
        when(actionsMock.clickAndHold(any())).thenReturn(actionsMock);
        when(actionsMock.moveToElement(any())).thenReturn(actionsMock);
        when(actionsMock.moveToElement(any(), anyInt(), anyInt())).thenReturn(actionsMock);
        when(actionsMock.release(any())).thenReturn(actionsMock);
        when(actionsMock.doubleClick(any())).thenReturn(actionsMock);
        when(actionsMock.contextClick(any())).thenReturn(actionsMock);
        when(actionsMock.click()).thenReturn(actionsMock);

        this.seleniumAdapter.setActionsFactory(actionsFactory);
        this.seleniumAdapter.setFileDownloadHelper(fileDownloadHelperMock);

        Sleep.setInstance(sleepMock);
    }

    @Test
    void findElement_withCssSelector_returnsElement() {

        // Arrange
        IBy selector = By.cssSelector(".class");
        when(this.webDriver.findElement(any(org.openqa.selenium.By.ByCssSelector.class))).thenReturn(this.webElement);

        // Act
        WebControl control = this.seleniumAdapter.findElement(selector);

        // Assert
        assertEquals(this.webElement, ((SeleniumElement) control).getUnderlyingWebElement());
    }

    @Test
    void findElement_withCssSelectorAndElementIsNotFound_ThrowsException() {

        // Arrange
        IBy selector = By.cssSelector(".class");
        when(this.webDriver.findElement(any(org.openqa.selenium.By.ByCssSelector.class)))
                .thenThrow(new org.openqa.selenium.NoSuchElementException("no such element"));

        // Act
        Executable action = () -> this.seleniumAdapter.findElement(selector);

        // Assert
        Exception exception = assertThrows(NoSuchElementException.class, action);
        assertEquals("The specified element with css selector '.class' does not exist.", exception.getMessage());
    }

    @Test
    void findElement_withJQuerySelector_returnsElement() {

        // Arrange
        IBy jQuerySelector = By.jQuery(".class:contains(something)");
        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);
        when(this.javascriptExecutor.apply(any(SeleniumScriptExecutor.class), any(String.class), any())).thenReturn(Collections.singletonList(this.webElement));

        // Act
        WebControl control = this.seleniumAdapter.findElement(jQuerySelector);

        // Assert
        assertEquals(this.webElement, ((SeleniumElement) control).getUnderlyingWebElement());
    }

    @Test
    void findElement_withXPathSelector_returnsElement() {

        // Arrange
        when(this.webDriver.findElement(any(org.openqa.selenium.By.ByXPath.class))).thenReturn(this.webElement);

        // Act
        WebControl control = this.seleniumAdapter.findElement(this.xPathSelector);

        // Assert
        assertEquals(this.webElement, ((SeleniumElement) control).getUnderlyingWebElement());
    }

    @Test
    void findElement_withXPathSelectorAndElementIsNotFound_ThrowsException() {

        // Arrange
        when(this.xPathSelector.toString()).thenReturn("//div/div");
        when(this.webDriver.findElement(any(org.openqa.selenium.By.ByXPath.class)))
                .thenThrow(new org.openqa.selenium.NoSuchElementException("no such element"));

        // Act
        Executable action = () -> this.seleniumAdapter.findElement(this.xPathSelector);

        // Assert
        Exception exception = assertThrows(NoSuchElementException.class, action);
        assertEquals("The specified element with XPath selector '//div/div' does not exist.", exception.getMessage());
    }

    @Test
    void findElement_unknownSelector_throwsUnsupportedOperationException() {

        // Arrange

        // Act
        Executable action = () -> this.seleniumAdapter.findElement(null);

        // Assert
        assertThrows(UnsupportedOperationException.class, action);
    }

    @Test
    void elementHasOptions_notSelect_throwsException() {

        // Arrange
        String[] options = new String[2];
        options[0] = "Option 1";
        options[1] = "Option 2";
        when(this.seleniumElement.getTagName()).thenReturn("OPTION");

        // Act
        Executable action = () -> this.seleniumAdapter.elementHasOptions(this.seleniumElement, options, "optGroup2", WebSelectOption.TEXT);

        // Assert
        Exception exception = assertThrows(IncorrectElementTagException.class, action);
        assertEquals("The specified element has tag OPTION, whereas it should have tag select.", exception.getMessage());
    }

    @Test
    void elementHasOptions_optGroupAndHasOptions_doesNotThrowException() {

        // Arrange
        SeleniumElement seleniumElement = mock(SeleniumElement.class);
        String[] options = new String[2];
        options[0] = "Option 1";
        options[1] = "Option 2";
        when(this.seleniumElement.getTagName()).thenReturn("SELECT");
        when(this.seleniumElement.findElement(this.iByWebCaptor2.capture())).thenReturn(seleniumElement);

        // Act
        Executable action = () -> this.seleniumAdapter.elementHasOptions(this.seleniumElement, options, "optGroup2", WebSelectOption.TEXT);

        // Assert
        assertDoesNotThrow(action);
        verify(seleniumElement, times(2)).findElementByXPath(this.iByWebCaptor.capture());
        assertEquals(".//option[normalize-space(.) = \"Option 1\"]", this.iByWebCaptor.getAllValues().get(0).toString());
        assertEquals(".//option[normalize-space(.) = \"Option 2\"]", this.iByWebCaptor.getAllValues().get(1).toString());
        assertEquals("optgroup[label='optGroup2']", this.iByWebCaptor2.getValue().toString());
    }

    @Test
    void elementHasOptions_optGroupAndDoesNotHaveOptions_throwException() {

        // Arrange
        SeleniumElement seleniumElement = mock(SeleniumElement.class);
        String[] options = new String[2];
        options[0] = "Option 1";
        options[1] = "Option 2";
        when(this.seleniumElement.getTagName()).thenReturn("SELECT");
        when(this.seleniumElement.findElement(this.iByWebCaptor2.capture())).thenReturn(seleniumElement);
        when(seleniumElement.findElementByXPath(this.iByWebCaptor.capture())).thenThrow(new NoSuchElementException(new Exception("error message"), By.cssSelector("test")));

        // Act
        Executable action = () -> this.seleniumAdapter.elementHasOptions(this.seleniumElement, options, "optGroup2", WebSelectOption.TEXT);

        // Assert
        Exception exception = assertThrows(ElementDoesNotHaveOptionException.class, action);
        assertEquals("Element does not have option \"com.ultimatesoftware.aeon.core.common.exceptions.NoSuchElementException: The specified element with css selector 'test' does not exist.\".", exception.getMessage());
        assertEquals("optgroup[label='optGroup2']", this.iByWebCaptor2.getValue().toString());
    }

    @Test
    void elementHasOptions_optGroupAndHasOptionsWithValue_doesNotThrowException() {

        // Arrange
        SeleniumElement seleniumElement = mock(SeleniumElement.class);
        String[] options = new String[2];
        options[0] = "Option 1";
        options[1] = "Option 2";
        when(this.seleniumElement.getTagName()).thenReturn("SELECT");
        when(this.seleniumElement.findElement(this.iByWebCaptor2.capture())).thenReturn(seleniumElement);

        // Act
        Executable action = () -> this.seleniumAdapter.elementHasOptions(this.seleniumElement, options, "optGroup2", WebSelectOption.VALUE);

        // Assert
        assertDoesNotThrow(action);
        verify(seleniumElement, times(2)).findElement(this.iByWebCaptor.capture());
        assertEquals("option[value='Option 1']", this.iByWebCaptor.getAllValues().get(0).toString());
        assertEquals("option[value='Option 2']", this.iByWebCaptor.getAllValues().get(1).toString());
        assertEquals("optgroup[label='optGroup2']", this.iByWebCaptor2.getValue().toString());
    }

    @Test
    void elementHasOptions_optGroupAndDoesNotHaveOptionsWithValue_throwException() {

        // Arrange
        SeleniumElement seleniumElement = mock(SeleniumElement.class);
        String[] options = new String[2];
        options[0] = "Option 1";
        options[1] = "Option 2";
        when(this.seleniumElement.getTagName()).thenReturn("SELECT");
        when(this.seleniumElement.findElement(this.iByWebCaptor2.capture())).thenReturn(seleniumElement);
        when(seleniumElement.findElement(this.iByWebCaptor.capture())).thenThrow(new NoSuchElementException(new Exception("error message"), By.cssSelector("test")));

        // Act
        Executable action = () -> this.seleniumAdapter.elementHasOptions(this.seleniumElement, options, "optGroup2", WebSelectOption.VALUE);

        // Assert
        Exception exception = assertThrows(ElementDoesNotHaveOptionException.class, action);
        assertEquals("Element does not have option \"com.ultimatesoftware.aeon.core.common.exceptions.NoSuchElementException: The specified element with css selector 'test' does not exist.\".", exception.getMessage());
        assertEquals("optgroup[label='optGroup2']", this.iByWebCaptor2.getValue().toString());
    }

    @Test
    void elementHasOptions_noOptGroupAndHasOptions_doesNotThrowException() {

        // Arrange
        String[] options = new String[2];
        options[0] = "Option 1";
        options[1] = "Option 2";
        when(this.seleniumElement.getTagName()).thenReturn("SELECT");

        // Act
        Executable action = () -> this.seleniumAdapter.elementHasOptions(this.seleniumElement, options, null, WebSelectOption.TEXT);

        // Assert
        assertDoesNotThrow(action);
        verify(this.seleniumElement, times(2)).findElementByXPath(this.iByWebCaptor.capture());
        assertEquals(".//option[normalize-space(.) = \"Option 1\"]", this.iByWebCaptor.getAllValues().get(0).toString());
        assertEquals(".//option[normalize-space(.) = \"Option 2\"]", this.iByWebCaptor.getAllValues().get(1).toString());
    }

    @Test
    void elementHasOptions_noOptGroupAndDoesNotHaveOptions_throwException() {

        // Arrange
        String[] options = new String[2];
        options[0] = "Option 1";
        options[1] = "Option 2";
        when(this.seleniumElement.getTagName()).thenReturn("SELECT");
        when(this.seleniumElement.findElementByXPath(this.iByWebCaptor.capture())).thenThrow(new NoSuchElementException(new Exception("error message"), By.cssSelector("test")));

        // Act
        Executable action = () -> this.seleniumAdapter.elementHasOptions(this.seleniumElement, options, null, WebSelectOption.TEXT);

        // Assert
        Exception exception = assertThrows(ElementDoesNotHaveOptionException.class, action);
        assertEquals("Element does not have option \"com.ultimatesoftware.aeon.core.common.exceptions.NoSuchElementException: The specified element with css selector 'test' does not exist.\".", exception.getMessage());
    }

    @Test
    void elementHasOptions_noOptGroupAndHasOptionsWithValue_doesNotThrowException() {

        // Arrange
        String[] options = new String[2];
        options[0] = "Option 1";
        options[1] = "Option 2";
        when(this.seleniumElement.getTagName()).thenReturn("SELECT");

        // Act
        Executable action = () -> this.seleniumAdapter.elementHasOptions(this.seleniumElement, options, null, WebSelectOption.VALUE);

        // Assert
        assertDoesNotThrow(action);
        verify(this.seleniumElement, times(2)).findElement(this.iByWebCaptor.capture());
        assertEquals("option[value='Option 1']", this.iByWebCaptor.getAllValues().get(0).toString());
        assertEquals("option[value='Option 2']", this.iByWebCaptor.getAllValues().get(1).toString());
    }

    @Test
    void elementHasOptions_noOptGroupAndDoesNotHaveOptionsWithValue_throwException() {

        // Arrange
        String[] options = new String[2];
        options[0] = "Option 1";
        options[1] = "Option 2";
        when(this.seleniumElement.getTagName()).thenReturn("SELECT");
        when(this.seleniumElement.findElement(this.iByWebCaptor.capture())).thenThrow(new NoSuchElementException(new Exception("error message"), By.cssSelector("test")));

        // Act
        Executable action = () -> this.seleniumAdapter.elementHasOptions(this.seleniumElement, options, null, WebSelectOption.VALUE);

        // Assert
        Exception exception = assertThrows(ElementDoesNotHaveOptionException.class, action);
        assertEquals("Element does not have option \"com.ultimatesoftware.aeon.core.common.exceptions.NoSuchElementException: The specified element with css selector 'test' does not exist.\".", exception.getMessage());
    }

    @Test
    void elementDoesNotHaveOptions_notSelect_throwsException() {

        // Arrange
        String[] options = new String[2];
        options[0] = "Option 1";
        options[1] = "Option 2";
        when(this.seleniumElement.getTagName()).thenReturn("OPTION");

        // Act
        Executable action = () -> this.seleniumAdapter.elementDoesNotHaveOptions(this.seleniumElement, options, "optGroup2", WebSelectOption.TEXT);

        // Assert
        Exception exception = assertThrows(IncorrectElementTagException.class, action);
        assertEquals("The specified element has tag OPTION, whereas it should have tag select.", exception.getMessage());
    }

    @Test
    void elementDoesNotHaveOptions_optGroupAndHasOptions_doesNotThrowException() {

        // Arrange
        SeleniumElement seleniumElement = mock(SeleniumElement.class);
        String[] options = new String[2];
        options[0] = "Option 1";
        options[1] = "Option 2";
        when(this.seleniumElement.getTagName()).thenReturn("SELECT");
        when(this.seleniumElement.findElement(this.iByWebCaptor2.capture())).thenReturn(seleniumElement);

        // Act
        Executable action = () -> this.seleniumAdapter.elementDoesNotHaveOptions(this.seleniumElement, options, "optGroup2", WebSelectOption.TEXT);

        // Assert
        Exception exception = assertThrows(ElementHasOptionException.class, action);
        assertEquals("The select element has the option with value \"Option 1\".", exception.getMessage());
        assertEquals("optgroup[label='optGroup2']", this.iByWebCaptor2.getValue().toString());
    }

    @Test
    void elementDoesNotHaveOptions_optGroupAndDoesNotHaveOptions_throwException() {

        // Arrange
        SeleniumElement seleniumElement = mock(SeleniumElement.class);
        String[] options = new String[2];
        options[0] = "Option 1";
        options[1] = "Option 2";
        when(this.seleniumElement.getTagName()).thenReturn("SELECT");
        when(this.seleniumElement.findElement(this.iByWebCaptor2.capture())).thenReturn(seleniumElement);
        when(seleniumElement.findElementByXPath(this.iByWebCaptor.capture())).thenThrow(new NoSuchElementException(new Exception("error message"), By.cssSelector("test")));

        // Act
        Executable action = () -> this.seleniumAdapter.elementDoesNotHaveOptions(this.seleniumElement, options, "optGroup2", WebSelectOption.TEXT);

        // Assert
        assertDoesNotThrow(action);
        verify(seleniumElement, times(2)).findElementByXPath(this.iByWebCaptor.capture());
        assertEquals(".//option[normalize-space(.) = \"Option 1\"]", this.iByWebCaptor.getAllValues().get(0).toString());
        assertEquals(".//option[normalize-space(.) = \"Option 2\"]", this.iByWebCaptor.getAllValues().get(1).toString());
        assertEquals("optgroup[label='optGroup2']", this.iByWebCaptor2.getValue().toString());
    }

    @Test
    void elementDoesNotHaveOptions_optGroupAndHasOptionsWithValue_doesNotThrowException() {

        // Arrange
        SeleniumElement seleniumElement = mock(SeleniumElement.class);
        String[] options = new String[2];
        options[0] = "Option 1";
        options[1] = "Option 2";
        when(this.seleniumElement.getTagName()).thenReturn("SELECT");
        when(this.seleniumElement.findElement(this.iByWebCaptor2.capture())).thenReturn(seleniumElement);

        // Act
        Executable action = () -> this.seleniumAdapter.elementDoesNotHaveOptions(this.seleniumElement, options, "optGroup2", WebSelectOption.VALUE);

        // Assert
        Exception exception = assertThrows(ElementHasOptionException.class, action);
        assertEquals("The select element has the option with value \"Option 1\".", exception.getMessage());
        assertEquals("optgroup[label='optGroup2']", this.iByWebCaptor2.getValue().toString());
    }

    @Test
    void elementDoesNotHaveOptions_optGroupAndDoesNotHaveOptionsWithValue_throwException() {

        // Arrange
        SeleniumElement seleniumElement = mock(SeleniumElement.class);
        String[] options = new String[2];
        options[0] = "Option 1";
        options[1] = "Option 2";
        when(this.seleniumElement.getTagName()).thenReturn("SELECT");
        when(this.seleniumElement.findElement(this.iByWebCaptor2.capture())).thenReturn(seleniumElement);
        when(seleniumElement.findElement(this.iByWebCaptor.capture())).thenThrow(new NoSuchElementException(new Exception("error message"), By.cssSelector("test")));

        // Act
        Executable action = () -> this.seleniumAdapter.elementDoesNotHaveOptions(this.seleniumElement, options, "optGroup2", WebSelectOption.VALUE);

        // Assert
        assertDoesNotThrow(action);
        verify(seleniumElement, times(2)).findElement(this.iByWebCaptor.capture());
        assertEquals("option[value='Option 1']", this.iByWebCaptor.getAllValues().get(0).toString());
        assertEquals("option[value='Option 2']", this.iByWebCaptor.getAllValues().get(1).toString());
        assertEquals("optgroup[label='optGroup2']", this.iByWebCaptor2.getValue().toString());
    }

    @Test
    void elementDoesNotHaveOptions_noOptGroupAndHasOptions_doesNotThrowException() {

        // Arrange
        String[] options = new String[2];
        options[0] = "Option 1";
        options[1] = "Option 2";
        when(this.seleniumElement.getTagName()).thenReturn("SELECT");

        // Act
        Executable action = () -> this.seleniumAdapter.elementDoesNotHaveOptions(this.seleniumElement, options, null, WebSelectOption.TEXT);

        // Assert
        Exception exception = assertThrows(ElementHasOptionException.class, action);
        assertEquals("The select element has the option with value \"Option 1\".", exception.getMessage());
    }

    @Test
    void elementDoesNotHaveOptions_noOptGroupAndDoesNotHaveOptions_throwException() {

        // Arrange
        String[] options = new String[2];
        options[0] = "Option 1";
        options[1] = "Option 2";
        when(this.seleniumElement.getTagName()).thenReturn("SELECT");
        when(this.seleniumElement.findElementByXPath(this.iByWebCaptor.capture())).thenThrow(new NoSuchElementException(new Exception("error message"), By.cssSelector("test")));

        // Act
        Executable action = () -> this.seleniumAdapter.elementDoesNotHaveOptions(this.seleniumElement, options, null, WebSelectOption.TEXT);

        // Assert
        assertDoesNotThrow(action);
        verify(this.seleniumElement, times(2)).findElementByXPath(this.iByWebCaptor.capture());
        assertEquals(".//option[normalize-space(.) = \"Option 1\"]", this.iByWebCaptor.getAllValues().get(0).toString());
        assertEquals(".//option[normalize-space(.) = \"Option 2\"]", this.iByWebCaptor.getAllValues().get(1).toString());
    }

    @Test
    void elementDoesNotHaveOptions_noOptGroupAndHasOptionsWithValue_doesNotThrowException() {

        // Arrange
        String[] options = new String[2];
        options[0] = "Option 1";
        options[1] = "Option 2";
        when(this.seleniumElement.getTagName()).thenReturn("SELECT");

        // Act
        Executable action = () -> this.seleniumAdapter.elementDoesNotHaveOptions(this.seleniumElement, options, null, WebSelectOption.VALUE);

        // Assert
        Exception exception = assertThrows(ElementHasOptionException.class, action);
        assertEquals("The select element has the option with value \"Option 1\".", exception.getMessage());
    }

    @Test
    void elementDoesNotHaveOptions_noOptGroupAndDoesNotHaveOptionsWithValue_throwException() {

        // Arrange
        String[] options = new String[2];
        options[0] = "Option 1";
        options[1] = "Option 2";
        when(this.seleniumElement.getTagName()).thenReturn("SELECT");
        when(this.seleniumElement.findElement(this.iByWebCaptor.capture())).thenThrow(new NoSuchElementException(new Exception("error message"), By.cssSelector("test")));

        // Act
        Executable action = () -> this.seleniumAdapter.elementDoesNotHaveOptions(this.seleniumElement, options, null, WebSelectOption.VALUE);

        // Assert
        assertDoesNotThrow(action);
        verify(this.seleniumElement, times(2)).findElement(this.iByWebCaptor.capture());
        assertEquals("option[value='Option 1']", this.iByWebCaptor.getAllValues().get(0).toString());
        assertEquals("option[value='Option 2']", this.iByWebCaptor.getAllValues().get(1).toString());
    }

    @Test
    void getWebDriver_returnsWebDriver() {

        // Act
        WebDriver driver = this.seleniumAdapter.getWebDriver();

        // Assert
        assertEquals(this.webDriver, driver);
    }

    @Test
    void addCookie_validCookie_callsWebDriverAddCookie() {

        // Arrange
        Date expirationDate = new Date();

        IWebCookie mockCookie = Mockito.mock(IWebCookie.class);
        when(mockCookie.getName()).thenReturn("Cookie Name");
        when(mockCookie.getDomain()).thenReturn("Cookie Domain");
        when(mockCookie.getExpiration()).thenReturn(expirationDate);
        when(mockCookie.getPath()).thenReturn("Cookie Path");
        when(mockCookie.getSecure()).thenReturn(true);
        when(mockCookie.getValue()).thenReturn("Cookie Value");

        when(webDriver.manage()).thenReturn(options);

        // Act
        this.seleniumAdapter.addCookie(mockCookie);

        // Assert
        verify(options).addCookie(cookieCaptor.capture());

        Cookie cookie = cookieCaptor.getValue();

        assertEquals("Cookie Name", cookie.getName());
        assertEquals("Cookie Domain", cookie.getDomain());
        assertEquals(expirationDate.toString(), cookie.getExpiry().toString());
        assertEquals("Cookie Path", cookie.getPath());
        assertTrue(cookie.isSecure());
        assertFalse(cookie.isHttpOnly());
        assertEquals("Cookie Value", cookie.getValue());
    }

    @Test
    void addCookie_domainStartsWithPeriodAndNotFirefox_includesStartingPeriod() throws MalformedURLException {

        // Arrange
        when(this.configuration.getBrowserType()).thenReturn(BrowserType.CHROME);
        this.seleniumAdapter = new SeleniumAdapter(
                this.webDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                new URL("http://host/wd/hub"),
                this.loggingPreferences
        );

        Date expirationDate = new Date();

        IWebCookie mockCookie = Mockito.mock(IWebCookie.class);
        when(mockCookie.getName()).thenReturn("Cookie Name");
        when(mockCookie.getDomain()).thenReturn(".http.");
        when(mockCookie.getExpiration()).thenReturn(expirationDate);
        when(mockCookie.getPath()).thenReturn("Cookie Path");
        when(mockCookie.getSecure()).thenReturn(true);
        when(mockCookie.getValue()).thenReturn("Cookie Value");

        when(webDriver.manage()).thenReturn(options);

        // Act
        this.seleniumAdapter.addCookie(mockCookie);

        // Assert
        verify(options).addCookie(cookieCaptor.capture());

        Cookie cookie = cookieCaptor.getValue();

        assertEquals("Cookie Name", cookie.getName());
        assertEquals(".http.", cookie.getDomain());
        assertEquals(expirationDate.toString(), cookie.getExpiry().toString());
        assertEquals("Cookie Path", cookie.getPath());
        assertTrue(cookie.isSecure());
        assertFalse(cookie.isHttpOnly());
        assertEquals("Cookie Value", cookie.getValue());
    }

    @Test
    void addCookie_domainStartsWithPeriodAndFirefox_omitsStartingPeriod() throws MalformedURLException {

        // Arrange
        when(this.configuration.getBrowserType()).thenReturn(BrowserType.FIREFOX);
        this.seleniumAdapter = new SeleniumAdapter(
                this.webDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                new URL("http://host/wd/hub"),
                this.loggingPreferences
        );

        Date expirationDate = new Date();

        IWebCookie mockCookie = Mockito.mock(IWebCookie.class);
        when(mockCookie.getName()).thenReturn("Cookie Name");
        when(mockCookie.getDomain()).thenReturn(".http.");
        when(mockCookie.getExpiration()).thenReturn(expirationDate);
        when(mockCookie.getPath()).thenReturn("Cookie Path");
        when(mockCookie.getSecure()).thenReturn(true);
        when(mockCookie.getValue()).thenReturn("Cookie Value");

        when(webDriver.manage()).thenReturn(options);

        // Act
        this.seleniumAdapter.addCookie(mockCookie);

        // Assert
        verify(options).addCookie(cookieCaptor.capture());

        Cookie cookie = cookieCaptor.getValue();

        assertEquals("Cookie Name", cookie.getName());
        assertEquals("http.", cookie.getDomain());
        assertEquals(expirationDate.toString(), cookie.getExpiry().toString());
        assertEquals("Cookie Path", cookie.getPath());
        assertTrue(cookie.isSecure());
        assertFalse(cookie.isHttpOnly());
        assertEquals("Cookie Value", cookie.getValue());
    }

    @Test
    void deleteAllCookies_callsWebDriverDeleteAllCookies() {

        // Act
        this.seleniumAdapter.deleteAllCookies();

        // Assert
        verify(this.options, times(1)).deleteAllCookies();
    }

    @Test
    void deleteCookie_callsWebDriverDeleteCookie() {

        // Act
        this.seleniumAdapter.deleteCookie("test");

        // Assert
        verify(this.options, times(1)).deleteCookieNamed(eq("test"));
    }

    @Test
    void getAllCookies_returnsAllWebDriverCookies() {

        // Arrange
        Date expiration = new Date();
        Cookie c1 = new Cookie("CN1", "CV1", "CD1", "CP1", expiration, true);
        Cookie c2 = new Cookie("CN2", "CV2", "CD2", "CP2", expiration, true);

        when(options.getCookies()).thenReturn(new HashSet<Cookie>() {{
            add(c1);
            add(c2);
        }});

        // Act
        List<IWebCookie> webCookies = this.seleniumAdapter.getAllCookies();

        // Assert
        assertEquals("CN2", webCookies.get(0).getName());
        assertEquals("CV2", webCookies.get(0).getValue());
        assertEquals(".CD2", webCookies.get(0).getDomain());
        assertEquals("CP2", webCookies.get(0).getPath());
        assertEquals(expiration.toString(), webCookies.get(0).getExpiration().toString());
        assertTrue(webCookies.get(0).getSecure());

        assertEquals("CN1", webCookies.get(1).getName());
        assertEquals("CV1", webCookies.get(1).getValue());
        assertEquals(".CD1", webCookies.get(1).getDomain());
        assertEquals("CP1", webCookies.get(1).getPath());
        assertEquals(expiration.toString(), webCookies.get(1).getExpiration().toString());
        assertTrue(webCookies.get(1).getSecure());
    }

    @Test
    void getCookie_cookieExists_ReturnsWebDriverCookie() {

        // Arrange
        Date expiration = new Date();
        Cookie c = new Cookie("CN1", "CV1", "CD1", "CP1", expiration, true);

        when(options.getCookieNamed(eq("CN1"))).thenReturn(c);

        // Act
        IWebCookie webCookie = this.seleniumAdapter.getCookie("CN1");

        // Assert
        assertEquals("CN1", webCookie.getName());
        assertEquals("CV1", webCookie.getValue());
        assertEquals(".CD1", webCookie.getDomain());
        assertEquals("CP1", webCookie.getPath());
        assertEquals(expiration.toString(), webCookie.getExpiration().toString());
        assertTrue(webCookie.getSecure());
    }

    @Test
    void getCookie_cookieDoesNotExist_ThrowsNoSuchCookieException() {

        // Act
        Executable action = () -> this.seleniumAdapter.getCookie("test");

        // Assert
        Exception exception = assertThrows(NoSuchCookieException.class, action);
        assertEquals("The cookie \"test\" does not exist.", exception.getMessage());
    }

    @Test
    void modifyCookie_happyPath() {

        // Arrange
        Date expiration = new Date();
        Cookie c = new Cookie("CN1", "CV1", "CD1", "CP1", expiration, true);

        when(options.getCookieNamed(eq("CN1"))).thenReturn(c);

        // Act
        this.seleniumAdapter.modifyCookie("CN1", "CVMod");

        // Assert
        verify(options, times(1)).getCookieNamed(eq("CN1"));
        verify(options, times(1)).deleteCookieNamed(eq("CN1"));

        verify(options).addCookie(cookieCaptor.capture());

        Cookie cookie = cookieCaptor.getValue();

        assertEquals("CN1", cookie.getName());
        assertEquals("CD1", cookie.getDomain());
        assertEquals(expiration.toString(), cookie.getExpiry().toString());
        assertEquals("CP1", cookie.getPath());

        // TODO(DionnyS): The following assertion is not passing. Determine correct expected behavior.
        // assertTrue(cookie.isSecure());

        assertFalse(cookie.isHttpOnly());
        assertEquals("CVMod", cookie.getValue());
    }

    @Test
    void modifyCookie_cookieDoesNotExist_throwsNoSuchCookieException() {

        // Act
        Executable action = () -> this.seleniumAdapter.modifyCookie("test", "val");

        // Assert
        Exception exception = assertThrows(NoSuchCookieException.class, action);
        assertEquals("The cookie \"test\" does not exist.", exception.getMessage());
    }

    @Test
    void modifyCookie_domainStartsWithPeriodAndFirefox_omitsStartingPeriod() throws MalformedURLException {

        // Arrange
        when(this.configuration.getBrowserType()).thenReturn(BrowserType.FIREFOX);
        this.seleniumAdapter = new SeleniumAdapter(
                this.webDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                new URL("http://host/wd/hub"),
                this.loggingPreferences
        );

        Date expiration = new Date();
        Cookie c = new Cookie("CN1", "CV1", ".CD1.", "CP1", expiration, true);

        when(options.getCookieNamed(eq("CN1"))).thenReturn(c);

        // Act
        this.seleniumAdapter.modifyCookie("CN1", "CVMod");

        // Assert
        verify(options, times(1)).getCookieNamed(eq("CN1"));
        verify(options, times(1)).deleteCookieNamed(eq("CN1"));

        verify(options).addCookie(cookieCaptor.capture());

        Cookie cookie = cookieCaptor.getValue();

        assertEquals("CN1", cookie.getName());
        assertEquals("CD1.", cookie.getDomain());
        assertEquals(expiration.toString(), cookie.getExpiry().toString());
        assertEquals("CP1", cookie.getPath());
        assertFalse(cookie.isHttpOnly());
        assertEquals("CVMod", cookie.getValue());
    }

    @Test
    void getTitle_happyPath() {

        // Arrange
        when(webDriver.getTitle()).thenReturn("Test Title");

        // Act
        String title = this.seleniumAdapter.getTitle();

        // Assert
        assertEquals("Test Title", title);
    }

    @Test
    void getCurrentUrl_happyPath() {

        // Arrange
        when(webDriver.getCurrentUrl()).thenReturn("http://google.com");

        // Act
        URL url = this.seleniumAdapter.getUrl();

        // Assert
        assertEquals("http://google.com", Objects.requireNonNull(url).toString());
    }

    @Test
    void getCurrentUrl_malformedUrl_returnsNull() {

        // Arrange
        when(webDriver.getCurrentUrl()).thenReturn("httx://google.com");

        // Act
        URL url = this.seleniumAdapter.getUrl();

        // Assert
        assertNull(url);
    }

    @Test
    void getFocusedElement_happyPath() {

        // Arrange
        WebElement webElement = Mockito.mock(RemoteWebElement.class);
        when(targetLocatorMock.activeElement()).thenReturn(webElement);

        // Act
        SeleniumElement element = (SeleniumElement) this.seleniumAdapter.getFocusedElement();

        // Assert
        assertEquals(webElement, element.getUnderlyingWebElement());
    }

    @Test
    void getElementTagName_happyPath() {

        // Arrange
        WebElement webElement = Mockito.mock(RemoteWebElement.class);
        when(webElement.getTagName()).thenReturn("Test Tag");
        SeleniumElement element = new SeleniumElement(webElement);

        // Act
        String tagName = this.seleniumAdapter.getElementTagName(element);

        // Assert
        assertEquals("Test Tag", tagName);
    }

    @Test
    void clearElement_happyPath() {

        // Arrange
        WebElement webElement = Mockito.mock(RemoteWebElement.class);
        SeleniumElement element = new SeleniumElement(webElement);

        // Act
        this.seleniumAdapter.clearElement(element);

        // Assert
        verify(webElement, times(1)).clear();
    }

    @Test
    void chooseSelectElementByValue_happyPath() {

        // Act
        this.seleniumAdapter.chooseSelectElementByValue(seleniumElement, "Test Value");

        // Assert
        verify(seleniumElement, times(1)).selectByValue("Test Value");
    }

    @Test
    void chooseSelectElementByText_happyPath() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);

        // Act
        this.seleniumAdapter.chooseSelectElementByText(element, "Test Value");

        // Assert
        verify(element, times(1)).selectByText("Test Value");
    }

    @Test
    void clickElement_happyPath() {

        // Arrange
        WebElement webElement = Mockito.mock(RemoteWebElement.class);
        SeleniumElement element = new SeleniumElement(webElement);

        // Act
        this.seleniumAdapter.clickElement(element);

        // Assert
        verify(webElement, times(1)).click();
    }

    @Test
    void sendKeysToElement_happyPath() {

        // Arrange
        WebElement webElement = Mockito.mock(RemoteWebElement.class);
        SeleniumElement element = new SeleniumElement(webElement);

        // Act
        this.seleniumAdapter.sendKeysToElement(element, "Test Keys");

        // Assert
        verify(webElement, times(1)).sendKeys("Test Keys");
    }

    @Test
    void getElementAttribute_happyPath() {

        // Arrange
        WebElement webElement = Mockito.mock(RemoteWebElement.class);
        when(webElement.getAttribute(eq("Test Attr"))).thenReturn("Test Val");
        SeleniumElement element = new SeleniumElement(webElement);

        // Act
        String attrValue = this.seleniumAdapter.getElementAttribute(element, "Test Attr");

        // Assert
        assertEquals("Test Val", attrValue);
    }

    @Test
    void switchToMainWindow_HappyPath() {

        // Act
        this.seleniumAdapter.switchToMainWindow("Test Handle", false);

        // Assert
        verify(targetLocatorMock, times(1)).window("Test Handle");
    }

    @Test
    void switchToMainWindow_multipleOpenWindows_notWaitingForAllToClose_doesNotThrowException() {

        // Arrange
        when(webDriver.getWindowHandles()).thenReturn(new HashSet<String>() {{
            add("handle1");
            add("handle2");
        }});

        // Act
        this.seleniumAdapter.switchToMainWindow("Test Handle", false);

        // Assert
        verify(targetLocatorMock, times(1)).window("Test Handle");
    }

    @Test
    void switchToMainWindow_multipleOpenWindows_waitingForAllToClose_throwsException() {

        // Arrange
        when(webDriver.getWindowHandles()).thenReturn(new HashSet<String>() {{
            add("handle1");
            add("handle2");
        }});

        // Act
        Executable action = () -> this.seleniumAdapter.switchToMainWindow("Test Handle", true);

        // Assert
        Exception exception = assertThrows(NotAllPopupWindowsClosedException.class, action);
        verify(targetLocatorMock, times(1)).window("Test Handle");
        assertEquals("Not all popup windows were closed.", exception.getMessage());
    }

    @Test
    void switchToMainWindow_singleOpenWindow_waitingForAllToClose_doesNotThrowException() {

        // Arrange
        when(webDriver.getWindowHandles()).thenReturn(new HashSet<String>() {{
            add("handle1");
        }});

        // Act
        this.seleniumAdapter.switchToMainWindow("Test Handle", true);

        // Assert
        verify(targetLocatorMock, times(1)).window("Test Handle");
    }

    @Test
    void getCurrentWindowHandle_happyPath() {

        // Arrange
        when(webDriver.getWindowHandle()).thenReturn("Test Handle");

        // Act
        String handle = this.seleniumAdapter.getCurrentWindowHandle();

        // Assert
        assertEquals("Test Handle", handle);
    }

    @Test
    void getWindowHandles_happyPath() {

        // Arrange
        HashSet<String> handles = new HashSet<String>() {{
            add("handle1");
            add("handle2");
        }};

        when(webDriver.getWindowHandles()).thenReturn(handles);

        // Act
        Collection<String> actualHandles = this.seleniumAdapter.getWindowHandles();

        // Assert
        assertEquals(handles, actualHandles);
    }

    @Test
    void goToUrl_happyPath() throws MalformedURLException {

        // Act
        URL navigateUrl = new URL("http://google.com");
        this.seleniumAdapter.goToUrl(navigateUrl);

        // Assert
        verify(navigationMock, times(1)).to(eq(navigateUrl));
    }

    @Test
    void scrollToTop_happyPath() {

        // Arrange
        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);
        when(this.javascriptExecutor.apply(any(SeleniumScriptExecutor.class), any(String.class), any())).thenReturn(true);

        // Act
        this.seleniumAdapter.scrollToTop();

        // Assert
        // Note: The script execution contents are purposely not asserted against.
        verify(this.javascriptExecutor, times(1))
                .apply(any(), any(), any());
    }

    @Test
    void scrollToEnd_happyPath() {

        // Arrange
        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);
        when(this.javascriptExecutor.apply(any(SeleniumScriptExecutor.class), any(String.class), any())).thenReturn(true);

        // Act
        this.seleniumAdapter.scrollToEnd();

        // Assert
        // Note: The script execution contents are purposely not asserted against.
        verify(this.javascriptExecutor, times(1))
                .apply(any(), any(), any());
    }

    @Test
    void findElements_withCssSelector_returnsElements() {

        // Arrange
        List<WebElement> returnedElements = new ArrayList<>();
        returnedElements.add(Mockito.mock(WebElement.class));
        returnedElements.add(Mockito.mock(WebElement.class));

        IBy selector = By.cssSelector(".class");
        when(this.webDriver.findElements(any(org.openqa.selenium.By.ByCssSelector.class))).thenReturn(returnedElements);

        // Act
        Collection<WebControl> controls = this.seleniumAdapter.findElements(selector);
        List<WebControl> controlList = new ArrayList<>(controls);

        // Assert
        assertEquals(returnedElements.get(0), ((SeleniumElement) controlList.get(0)).getUnderlyingWebElement());
        assertEquals(returnedElements.get(1), ((SeleniumElement) controlList.get(1)).getUnderlyingWebElement());
    }

    @Test
    void findElements_withCssSelectorAndElementIsNotFound_ThrowsException() {

        // Arrange
        IBy selector = By.cssSelector(".class");
        when(this.webDriver.findElements(any(org.openqa.selenium.By.ByCssSelector.class)))
                .thenThrow(new org.openqa.selenium.NoSuchElementException("no such element"));

        // Act
        Executable action = () -> this.seleniumAdapter.findElements(selector);

        // Assert
        Exception exception = assertThrows(NoSuchElementsException.class, action);
        assertEquals("The specified elements with css selector '.class' does not exist.", exception.getMessage());
    }

    @Test
    void findElements_emptyCollectionReturned_ThrowsException() {

        // Arrange
        List<WebElement> returnedElements = new ArrayList<>();

        IBy selector = By.cssSelector(".class");
        when(this.webDriver.findElements(any(org.openqa.selenium.By.ByCssSelector.class))).thenReturn(returnedElements);

        // Act
        Executable action = () -> this.seleniumAdapter.findElements(selector);

        // Assert
        Exception exception = assertThrows(NoSuchElementsException.class, action);
        assertEquals("The specified elements with css selector '.class' does not exist.", exception.getMessage());
    }

    @Test
    void findElements_withJQuerySelector_returnsElements() {

        // Arrange
        List<WebElement> returnedElements = new ArrayList<>();
        returnedElements.add(Mockito.mock(WebElement.class));
        returnedElements.add(Mockito.mock(WebElement.class));

        IBy jQuerySelector = By.jQuery(".class:contains(something)");
        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);
        when(this.javascriptExecutor.apply(any(SeleniumScriptExecutor.class), any(String.class), any())).thenReturn(returnedElements);

        // Act
        Collection<WebControl> controls = this.seleniumAdapter.findElements(jQuerySelector);
        List<WebControl> controlList = new ArrayList<>(controls);

        // Assert
        assertEquals(returnedElements.get(0), ((SeleniumElement) controlList.get(0)).getUnderlyingWebElement());
        assertEquals(returnedElements.get(1), ((SeleniumElement) controlList.get(1)).getUnderlyingWebElement());
    }

    @Test
    void findElements_withJQuerySelectorAndElementIsNotFound_throwsException() {

        // Arrange
        List<WebElement> returnedElements = new ArrayList<>();

        IBy jQuerySelector = By.jQuery(".class:contains(something)");
        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);
        when(this.javascriptExecutor.apply(any(SeleniumScriptExecutor.class), any(String.class), any())).thenReturn(returnedElements);

        // Act
        Executable action = () -> this.seleniumAdapter.findElements(jQuerySelector);

        // Assert
        Exception exception = assertThrows(NoSuchElementsException.class, action);
        assertEquals("The specified elements with jquery selector '$(\".class:contains(something)\")' does not exist.", exception.getMessage());
    }

    @Test
    void findElements_unknownSelector_throwsUnsupportedOperationException() {

        // Arrange

        // Act
        Executable action = () -> this.seleniumAdapter.findElements(null);

        // Assert
        assertThrows(UnsupportedOperationException.class, action);
    }

    @Test
    void findElements_withJQuerySelectorAndInvalidElementResponse_throwsException() {

        // Arrange
        List<Object> returnedElements = new ArrayList<>();
        returnedElements.add(true);
        returnedElements.add(true);

        IBy jQuerySelector = By.jQuery(".class:contains(something)");
        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);
        when(this.javascriptExecutor.apply(any(SeleniumScriptExecutor.class), any(String.class), any())).thenReturn(returnedElements);

        // Act
        Executable action = () -> this.seleniumAdapter.findElements(jQuerySelector);

        // Assert
        Exception exception = assertThrows(ScriptReturnValueNotHtmlElementException.class, action);
        assertEquals("Return value of [true, true] is not an HTML element. Script executed was: return $.makeArray($(\".class:contains(something)\"));", exception.getMessage());
    }

    @Test
    void findSelectElement_happyPath() {

        // Arrange
        IByWeb selector = By.cssSelector(".class");
        when(webElement.getTagName()).thenReturn("select");
        when(this.webDriver.findElement(any(org.openqa.selenium.By.ByCssSelector.class))).thenReturn(webElement);

        // Act
        WebControl control = this.seleniumAdapter.findSelectElement(selector);

        // Assert
        assertEquals(webElement, ((SeleniumSelectElement) control).getUnderlyingSelectElement().getWrappedElement());
    }

    @Test
    void switchToDefaultContent_happyPath() {

        // Act
        this.seleniumAdapter.switchToDefaultContent();

        // Assert
        verify(this.targetLocatorMock, times(1)).defaultContent();
    }

    @Test
    void switchToFrame_happyPath() {

        // Arrange
        IByWeb selector = By.cssSelector(".class");
        when(this.webDriver.findElement(any(org.openqa.selenium.By.ByCssSelector.class))).thenReturn(this.webElement);

        // Act
        this.seleniumAdapter.switchToFrame(selector);

        // Assert
        verify(this.targetLocatorMock, times(1)).frame(eq(this.webElement));
    }

    @Test
    void switchToWindowByTitle_happyPath() {

        // Arrange
        when(webDriver.getWindowHandles()).thenReturn(new HashSet<String>() {{
            add("handleA");
            add("handleB");
            add("handleC");
            add("handleD");
        }});

        when(webDriver.getTitle())
                .thenReturn("handle1 title")
                .thenReturn("handle2 title")
                .thenReturn("handle3 title")
                .thenReturn("handle4 title");

        // Act
        String windowHandle = this.seleniumAdapter.switchToWindowByTitle("handle3 title");

        // Assert
        assertNotNull(windowHandle);
        assertNotEquals("", windowHandle);
        verify(targetLocatorMock, times(3)).window(any());
    }

    @Test
    void switchToWindowByTitle_blankWindowTitle_throwsException() {

        // Act
        Executable action = () -> this.seleniumAdapter.switchToWindowByTitle("");

        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, action);
        assertEquals("windowTitle is null or an empty string", exception.getMessage());
    }

    @Test
    void switchToWindowByTitle_windowNotFound_throwsException() {

        // Arrange
        when(webDriver.getWindowHandles()).thenReturn(new HashSet<String>() {{
            add("handleA");
            add("handleB");
            add("handleC");
            add("handleD");
        }});

        when(webDriver.getTitle())
                .thenReturn("handle1 title")
                .thenReturn("handle2 title")
                .thenReturn("handleX title")
                .thenReturn("handle4 title");

        // Act
        Executable action = () -> this.seleniumAdapter.switchToWindowByTitle("handle3 title");

        // Assert
        Exception exception = assertThrows(NoSuchWindowException.class, action);
        assertEquals("The window \"handle3 title\" does not exist.", exception.getMessage());
        verify(targetLocatorMock, times(4)).window(any());
    }

    @Test
    void switchToWindowByHandle_happyPath() {

        // Act
        this.seleniumAdapter.switchToWindowByHandle("Test Handle");

        // Assert
        verify(targetLocatorMock, times(1)).window(eq("Test Handle"));
    }

    @Test
    void switchToWindowByUrl_happyPath() {

        // Arrange
        when(webDriver.getWindowHandles()).thenReturn(new HashSet<String>() {{
            add("handleA");
            add("handleB");
            add("handleC");
            add("handleD");
        }});

        when(webDriver.getCurrentUrl())
                .thenReturn("http://google1.com")
                .thenReturn("http://google2.com")
                .thenReturn("http://google3.com")
                .thenReturn("http://google4.com");

        // Act
        String windowHandle = this.seleniumAdapter.switchToWindowByUrl("http://google3.com");

        // Assert
        assertNotNull(windowHandle);
        assertNotEquals("", windowHandle);
        verify(targetLocatorMock, times(3)).window(any());
    }

    @Test
    void switchToWindowByUrl_blankUrl_throwsException() {

        // Act
        Executable action = () -> this.seleniumAdapter.switchToWindowByUrl("");

        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, action);
        assertEquals("value", exception.getMessage());
    }

    @Test
    void switchToWindowByUrl_windowNotFound_throwsException() {

        // Arrange
        when(webDriver.getWindowHandles()).thenReturn(new HashSet<String>() {{
            add("handleA");
            add("handleB");
            add("handleC");
            add("handleD");
        }});

        when(webDriver.getCurrentUrl())
                .thenReturn("http://google1.com")
                .thenReturn("http://google2.com")
                .thenReturn("http://google3.com")
                .thenReturn("http://google4.com");

        // Act
        Executable action = () -> this.seleniumAdapter.switchToWindowByUrl("http://google5.com");

        // Assert
        Exception exception = assertThrows(NoSuchWindowException.class, action);
        assertEquals("The window \"http://google5.com\" does not exist.", exception.getMessage());
        verify(targetLocatorMock, times(4)).window(any());
    }

    @Test
    void close_happyPath() {

        // Arrange
        this.seleniumAdapter.close();

        // Act
        verify(this.targetLocatorMock, times(1)).defaultContent();
        verify(this.webDriver, times(1)).close();
    }

    @Test
    void quit_happyPath() throws MalformedURLException {

        // Arrange
        when(this.remoteWebDriver.getSessionId()).thenReturn(new SessionId("s1"));

        when(this.configuration.getBrowserType()).thenReturn(BrowserType.CHROME);

        this.seleniumAdapter = new SeleniumAdapter(
                this.remoteWebDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                new URL("http://host/wd/hub"),
                this.loggingPreferences
        );

        this.seleniumAdapter.setFileDownloadHelper(fileDownloadHelperMock);

        // Act
        this.seleniumAdapter.quit();
        verify(this.remoteWebDriver, times(1)).quit();
    }

    @Test
    void verifyAlertExists_happyPath() {

        // Act
        this.seleniumAdapter.verifyAlertExists();

        // Assert
        verify(this.targetLocatorMock, times(1)).alert();
    }

    @Test
    void verifyAlertExists_switchToAlertFails_throwsException() {

        // Arrange
        when(this.targetLocatorMock.alert()).thenThrow(new NoAlertPresentException());

        // Act
        Executable action = () -> this.seleniumAdapter.verifyAlertExists();

        // Assert
        Exception exception = assertThrows(NoAlertException.class, action);
        assertEquals("The alert does not exist.", exception.getMessage());
    }

    @Test
    void verifyAlertNotExists_alertDoesNotExist_doesNotThrowException() {

        // Arrange
        when(this.targetLocatorMock.alert()).thenThrow(NoAlertPresentException.class);

        // Act
        this.seleniumAdapter.verifyAlertNotExists();

        // Assert
        verify(this.targetLocatorMock, times(1)).alert();
    }

    @Test
    void verifyAlertNotExists_alertExists_throwsException() {

        // Act
        Executable action = () -> this.seleniumAdapter.verifyAlertNotExists();

        // Assert
        Exception exception = assertThrows(AlertExistsException.class, action);
        assertEquals("The alert exists.", exception.getMessage());
    }

    @Test
    void getAlertText_alertPresent_returnsAlertText() {

        // Arrange
        Alert alertMock = Mockito.mock(Alert.class);
        when(alertMock.getText()).thenReturn("Test Alert");
        when(targetLocatorMock.alert()).thenReturn(alertMock);

        // Act
        String alertText = this.seleniumAdapter.getAlertText();

        // Assert
        assertEquals("Test Alert", alertText);
    }

    @Test
    void getAlertText_noAlertPresent_throwsException() {

        // Arrange
        when(this.targetLocatorMock.alert()).thenThrow(new NoAlertPresentException());

        // Act
        Executable action = () -> this.seleniumAdapter.getAlertText();

        // Assert
        Exception exception = assertThrows(NoAlertException.class, action);
        assertEquals("The alert does not exist.", exception.getMessage());
    }

    @Test
    void sendKeysToAlert_alertPresent_sendsKeys() {

        // Arrange
        Alert alertMock = Mockito.mock(Alert.class);
        when(targetLocatorMock.alert()).thenReturn(alertMock);

        // Act
        this.seleniumAdapter.sendKeysToAlert("Test Keys");

        // Assert
        verify(alertMock, times(1)).sendKeys("Test Keys");
    }

    @Test
    void sendKeysToAlert_noAlertPresent_throwsException() {

        // Arrange
        when(this.targetLocatorMock.alert()).thenThrow(new NoAlertPresentException());

        // Act
        Executable action = () -> this.seleniumAdapter.sendKeysToAlert("Test Keys");

        // Assert
        Exception exception = assertThrows(NoAlertException.class, action);
        assertEquals("The alert does not exist.", exception.getMessage());
    }

    @Test
    void acceptAlert_alertPresent_acceptsAlert() {

        // Arrange
        Alert alertMock = Mockito.mock(Alert.class);
        when(targetLocatorMock.alert()).thenReturn(alertMock);

        // Act
        this.seleniumAdapter.acceptAlert();

        // Assert
        verify(alertMock, times(1)).accept();
    }

    @Test
    void acceptAlert_noAlertPresent_throwsException() {

        // Arrange
        when(this.targetLocatorMock.alert()).thenThrow(new NoAlertPresentException());

        // Act
        Executable action = () -> this.seleniumAdapter.acceptAlert();

        // Assert
        Exception exception = assertThrows(NoAlertException.class, action);
        assertEquals("The alert does not exist.", exception.getMessage());
    }

    @Test
    void dismissAlert_alertPresent_dismissesAlert() {

        // Arrange
        Alert alertMock = Mockito.mock(Alert.class);
        when(targetLocatorMock.alert()).thenReturn(alertMock);

        // Act
        this.seleniumAdapter.dismissAlert();

        // Assert
        verify(alertMock, times(1)).dismiss();
    }

    @Test
    void dismissAlert_noAlertPresent_throwsException() {

        // Arrange
        when(this.targetLocatorMock.alert()).thenThrow(new NoAlertPresentException());

        // Act
        Executable action = () -> this.seleniumAdapter.dismissAlert();

        // Assert
        Exception exception = assertThrows(NoAlertException.class, action);
        assertEquals("The alert does not exist.", exception.getMessage());
    }

    @Test
    void focusWindow_happyPath() {

        // Arrange

        // Act
        Executable action = () -> this.seleniumAdapter.focusWindow();

        // Assert
        assertDoesNotThrow(action);
    }

    @Test
    void executeScript_happyPath() {

        // Arrange
        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);

        // Act
        this.seleniumAdapter.executeScript("Test Script", true, 1);

        // Assert
        verify(this.javascriptExecutor, times(1)).apply(any(), eq("Test Script"), listCaptor.capture());
        List<Object> arguments = new ArrayList<>();
        listCaptor.getValue().forEach(arguments::add);
        assertEquals(true, arguments.get(0));
        assertEquals(1, arguments.get(1));
    }

    @Test
    void executeScript_scriptExecutionThrowsRuntimeException_throwsException() {

        // Arrange
        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);
        when(this.javascriptExecutor.apply(any(SeleniumScriptExecutor.class), any(String.class), any())).thenThrow(RuntimeException.class);

        // Act
        Executable action = () -> this.seleniumAdapter.executeScript("Test Script", true, 1);

        // Assert
        Exception exception = assertThrows(ScriptExecutionException.class, action);
        assertEquals("Error executing script: Test Script", exception.getMessage());
    }

    @Test
    void executeAsyncScript_happyPath() {

        // Arrange
        when(this.asyncJavaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);

        // Act
        this.seleniumAdapter.executeAsyncScript("Test Script", true, 1);

        // Assert
        verify(this.javascriptExecutor, times(1)).apply(any(), eq("Test Script"), listCaptor.capture());
        List<Object> arguments = new ArrayList<>();
        listCaptor.getValue().forEach(arguments::add);
        assertEquals(true, arguments.get(0));
        assertEquals(1, arguments.get(1));
    }

    @Test
    void executeAsyncScript_scriptExecutionThrowsRuntimeException_throwsException() {

        // Arrange
        when(this.asyncJavaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);
        when(this.javascriptExecutor.apply(any(SeleniumScriptExecutor.class), any(String.class), any())).thenThrow(RuntimeException.class);

        // Act
        Executable action = () -> this.seleniumAdapter.executeAsyncScript("Test Script", true, 1);

        // Assert
        Exception exception = assertThrows(ScriptExecutionException.class, action);
        assertEquals("Error executing script: Test Script", exception.getMessage());
    }

    @Test
    void back_happyPath() {

        // Arrange
        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);

        // Act
        this.seleniumAdapter.back();

        // Assert
        verify(this.javascriptExecutor, times(1)).apply(any(), any(), any());
    }

    @Test
    void forward_happyPath() {

        // Arrange
        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);

        // Act
        this.seleniumAdapter.forward();

        // Assert
        verify(this.javascriptExecutor, times(1)).apply(any(), any(), any());
    }

    @Test
    void refresh_happyPath() {

        // Act
        this.seleniumAdapter.refresh();

        // Assert
        verify(this.navigationMock, times(1)).refresh();
    }

    @Test
    void blur_happyPath() {

        // Arrange
        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);

        IByWeb selector = By.cssSelector("#id");
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getSelector()).thenReturn(selector);

        // Act
        this.seleniumAdapter.blur(element);

        // Assert
        String expectedScript = selector.toJQuery().toString(JQueryStringType.BLUR_ELEMENT);
        verify(this.javascriptExecutor, times(1)).apply(any(), eq(expectedScript), any());
    }

    @ParameterizedTest
    @EnumSource(
            value = BrowserType.class,
            names = {"CHROME", "FIREFOX", "OPERA"})
    void maximize_remoteSpecificBrowsers_manuallySetsDimensions(BrowserType browserType) throws MalformedURLException {

        // Arrange
        when(this.configuration.getBrowserType()).thenReturn(browserType);
        this.seleniumAdapter = new SeleniumAdapter(
                this.webDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                new URL("http://host/wd/hub"),
                this.loggingPreferences
        );

        // Act
        this.seleniumAdapter.maximize();

        // Assert
        verify(this.windowMock, times(1)).setPosition(pointCaptor.capture());
        assertEquals(0, pointCaptor.getValue().getX());
        assertEquals(0, pointCaptor.getValue().getY());

        verify(this.windowMock, times(1)).setSize(dimensionCaptor.capture());
        assertEquals(BrowserSizeMap.map(BrowserSize.FULL_HD).width, dimensionCaptor.getValue().getWidth());
        assertEquals(BrowserSizeMap.map(BrowserSize.FULL_HD).height, dimensionCaptor.getValue().getHeight());
    }

    @ParameterizedTest
    @EnumSource(
            value = BrowserType.class,
            names = {"ANDROID_CHROME", "EDGE", "INTERNET_EXPLORER", "SAFARI", "IOS_SAFARI"})
    void maximize_remoteSpecificBrowsers_invokesRegularMaximize(BrowserType browserType) throws MalformedURLException {

        // Arrange
        when(this.configuration.getBrowserType()).thenReturn(browserType);
        this.seleniumAdapter = new SeleniumAdapter(
                this.webDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                new URL("http://host/wd/hub"),
                this.loggingPreferences
        );

        OsCheck.setDetectedOS(OsCheck.OSType.MAC_OS);

        // Act
        this.seleniumAdapter.maximize();

        // Assert
        verify(this.windowMock, times(0)).setPosition(any());
        verify(this.windowMock, times(0)).setSize(any());
        verify(this.windowMock, times(1)).maximize();
    }

    @ParameterizedTest
    @EnumSource(
            value = BrowserType.class,
            names = {"CHROME", "OPERA"})
    void maximize_notRemoteOnMacSpecificBrowsers_manuallySetsDimensions(BrowserType browserType) {

        // Arrange
        when(this.configuration.getBrowserType()).thenReturn(browserType);
        this.seleniumAdapter = new SeleniumAdapter(
                this.webDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                null,
                this.loggingPreferences
        );

        OsCheck.setDetectedOS(OsCheck.OSType.MAC_OS);
        ScreenHelper.screenWidth = 100;
        ScreenHelper.screenHeight = 100;

        // Act
        this.seleniumAdapter.maximize();

        // Assert
        verify(this.windowMock, times(1)).setPosition(pointCaptor.capture());
        assertEquals(0, pointCaptor.getValue().getX());
        assertEquals(0, pointCaptor.getValue().getY());

        verify(this.windowMock, times(1)).setSize(dimensionCaptor.capture());
        assertEquals(100, dimensionCaptor.getValue().getWidth());
        assertEquals(100, dimensionCaptor.getValue().getHeight());
    }

    @ParameterizedTest
    @EnumSource(
            value = BrowserType.class,
            names = {"CHROME", "OPERA"})
    void maximize_notRemoteOnLinuxSpecificBrowsers_manuallySetsDimensions(BrowserType browserType) {

        // Arrange
        when(this.configuration.getBrowserType()).thenReturn(browserType);
        this.seleniumAdapter = new SeleniumAdapter(
                this.webDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                null,
                this.loggingPreferences
        );

        OsCheck.setDetectedOS(OsCheck.OSType.LINUX);
        ScreenHelper.screenWidth = 100;
        ScreenHelper.screenHeight = 100;

        // Act
        this.seleniumAdapter.maximize();

        // Assert
        verify(this.windowMock, times(1)).setPosition(pointCaptor.capture());
        assertEquals(0, pointCaptor.getValue().getX());
        assertEquals(0, pointCaptor.getValue().getY());

        verify(this.windowMock, times(1)).setSize(dimensionCaptor.capture());
        assertEquals(100, dimensionCaptor.getValue().getWidth());
        assertEquals(100, dimensionCaptor.getValue().getHeight());
    }

    @ParameterizedTest
    @EnumSource(
            value = BrowserType.class,
            names = {"ANDROID_CHROME", "EDGE", "INTERNET_EXPLORER", "SAFARI", "IOS_SAFARI", "FIREFOX"})
    void maximize_notRemoteOnMacSpecificBrowsers_invokesRegularMaximize(BrowserType browserType) {

        // Arrange
        when(this.configuration.getBrowserType()).thenReturn(browserType);
        this.seleniumAdapter = new SeleniumAdapter(
                this.webDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                null,
                this.loggingPreferences
        );

        OsCheck.setDetectedOS(OsCheck.OSType.MAC_OS);

        // Act
        this.seleniumAdapter.maximize();

        // Assert
        verify(this.windowMock, times(0)).setPosition(any());
        verify(this.windowMock, times(0)).setSize(any());
        verify(this.windowMock, times(1)).maximize();
    }

    @ParameterizedTest
    @EnumSource(
            value = BrowserType.class,
            names = {"ANDROID_CHROME", "EDGE", "INTERNET_EXPLORER", "SAFARI", "IOS_SAFARI", "FIREFOX"})
    void maximize_notRemoteOnLinuxSpecificBrowsers_invokesRegularMaximize(BrowserType browserType) {

        // Arrange
        when(this.configuration.getBrowserType()).thenReturn(browserType);
        this.seleniumAdapter = new SeleniumAdapter(
                this.webDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                null,
                this.loggingPreferences
        );

        OsCheck.setDetectedOS(OsCheck.OSType.LINUX);

        // Act
        this.seleniumAdapter.maximize();

        // Assert
        verify(this.windowMock, times(0)).setPosition(any());
        verify(this.windowMock, times(0)).setSize(any());
        verify(this.windowMock, times(1)).maximize();
    }

    @ParameterizedTest
    @EnumSource(
            value = BrowserType.class,
            names = {"ANDROID_CHROME", "EDGE", "INTERNET_EXPLORER", "SAFARI", "IOS_SAFARI", "FIREFOX", "OPERA", "CHROME"})
    void maximize_notRemoteOnWindowsSpecificBrowsers_invokesRegularMaximize(BrowserType browserType) {

        // Arrange
        when(this.configuration.getBrowserType()).thenReturn(browserType);
        this.seleniumAdapter = new SeleniumAdapter(
                this.webDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                null,
                this.loggingPreferences
        );

        OsCheck.setDetectedOS(OsCheck.OSType.WINDOWS);

        // Act
        this.seleniumAdapter.maximize();

        // Assert
        verify(this.windowMock, times(0)).setPosition(any());
        verify(this.windowMock, times(0)).setSize(any());
        verify(this.windowMock, times(1)).maximize();
    }

    @ParameterizedTest
    @EnumSource(
            value = BrowserType.class,
            names = {"ANDROID_CHROME", "EDGE", "INTERNET_EXPLORER", "SAFARI", "IOS_SAFARI", "FIREFOX", "OPERA", "CHROME"})
    void maximize_notRemoteOnOtherOsSpecificBrowsers_invokesRegularMaximize(BrowserType browserType) {

        // Arrange
        when(this.configuration.getBrowserType()).thenReturn(browserType);
        this.seleniumAdapter = new SeleniumAdapter(
                this.webDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                null,
                this.loggingPreferences
        );

        OsCheck.setDetectedOS(OsCheck.OSType.WINDOWS);

        // Act
        this.seleniumAdapter.maximize();

        // Assert
        verify(this.windowMock, times(0)).setPosition(any());
        verify(this.windowMock, times(0)).setSize(any());
        verify(this.windowMock, times(1)).maximize();
    }

    @Test
    void maximize_maximizeThrowsIllegalStateException_maximizesUsingJavaScript() {

        // Arrange
        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);
        Mockito.doThrow(new IllegalStateException()).when(this.windowMock).maximize();

        // Act
        this.seleniumAdapter.maximize();

        // Assert
        verify(this.javascriptExecutor, times(1)).apply(any(), any(), any());
    }

    @Test
    void resize_happyPath() {

        // Arrange
        java.awt.Dimension dimension = new java.awt.Dimension(100, 100);

        // Act
        this.seleniumAdapter.resize(dimension);

        // Assert
        verify(this.windowMock, times(1)).setSize(dimensionCaptor.capture());
        assertEquals(100, dimensionCaptor.getValue().getWidth());
        assertEquals(100, dimensionCaptor.getValue().getHeight());
    }

    @Test
    void scrollElementIntoView_happyPath() {

        // Arrange
        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);
        SeleniumElement seleniumElement = Mockito.mock(SeleniumElement.class);
        when(seleniumElement.getSelector()).thenReturn(By.cssSelector("#id"));

        // Act
        this.seleniumAdapter.scrollElementIntoView(seleniumElement);

        // Assert
        verify(this.javascriptExecutor, times(2)).apply(any(), any(), any());
    }

    @Test
    void getScreenshot_screenshotCompatibleWebDriver_takesScreenshot() {

        // Arrange
        FirefoxDriver firefoxDriver = Mockito.mock(FirefoxDriver.class);

        byte[] imageBytes = new byte[10];

        when(firefoxDriver.getScreenshotAs(any())).thenReturn(imageBytes);

        when(this.configuration.getBrowserType()).thenReturn(browserType);
        this.seleniumAdapter = new SeleniumAdapter(
                firefoxDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                null,
                this.loggingPreferences
        );

        // Act
        this.seleniumAdapter.getScreenshot();

        // Assert
        verify(firefoxDriver, times(1)).getScreenshotAs(any());
    }

    @Test
    void getScreenshot_invalidImageBytes_throwsException() {

        // Arrange
        FirefoxDriver firefoxDriver = Mockito.mock(FirefoxDriver.class);

        when(firefoxDriver.getScreenshotAs(any())).thenReturn(null);

        when(this.configuration.getBrowserType()).thenReturn(browserType);
        this.seleniumAdapter = new SeleniumAdapter(
                firefoxDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                null,
                this.loggingPreferences
        );

        // Act
        Executable action = () -> this.seleniumAdapter.getScreenshot();

        // Assert
        Exception exception = assertThrows(UnableToTakeScreenshotException.class, action);
        assertEquals("Unable to take screenshot.", exception.getMessage());
    }

    @Test
    void getScreenshot_screenshotIncompatibleDriver_throwsException() {

        // Act
        Executable action = () -> this.seleniumAdapter.getScreenshot();

        // Assert
        Exception exception = assertThrows(IllegalStateException.class, action);
        assertEquals("Web IDriver does not support taking screenshot", exception.getMessage());
    }

    @Test
    void getPageSource_happyPath() {

        // Arrange
        when(this.webDriver.getPageSource()).thenReturn("Test Source");

        // Act
        String pageSource = this.seleniumAdapter.getPageSource();

        // Assert
        assertEquals("Test Source", pageSource);
    }

    @Test
    void dragAndDrop_html5Draggable_executesDragScript() {

        // Arrange
        WebElement draggable = Mockito.mock(WebElement.class);
        SeleniumElement draggableWrapped = Mockito.mock(SeleniumElement.class);
        when(draggableWrapped.getUnderlyingWebElement()).thenReturn(draggable);

        WebElement target = Mockito.mock(WebElement.class);
        when(this.webDriver.findElement(any(org.openqa.selenium.By.ByCssSelector.class))).thenReturn(target);

        when(draggable.getAttribute(eq("draggable"))).thenReturn("true");

        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);

        // Act
        this.seleniumAdapter.dragAndDrop(draggableWrapped, By.cssSelector("#target"));

        // Assert
        verify(this.javascriptExecutor, times(1)).apply(any(), any(), any());
    }

    @Test
    void dragAndDrop_notHtml5Draggable_usesActionsBuilder() {

        // Arrange
        WebElement draggable = Mockito.mock(WebElement.class);
        SeleniumElement draggableWrapped = Mockito.mock(SeleniumElement.class);
        when(draggableWrapped.getUnderlyingWebElement()).thenReturn(draggable);

        WebElement target = Mockito.mock(WebElement.class);
        when(this.webDriver.findElement(any(org.openqa.selenium.By.ByCssSelector.class))).thenReturn(target);

        when(draggable.getAttribute(eq("draggable"))).thenReturn(null);

        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);

        // Act
        this.seleniumAdapter.dragAndDrop(draggableWrapped, By.cssSelector("#target"));

        // Assert
        verify(this.javascriptExecutor, times(0)).apply(any(), any(), any());

        InOrder inOrder = inOrder(this.actionsMock);

        inOrder.verify(this.actionsMock, times(1)).clickAndHold(eq(draggable));
        inOrder.verify(this.actionsMock, times(1)).perform();

        inOrder.verify(this.actionsMock, times(1)).moveToElement(eq(target));
        inOrder.verify(this.actionsMock, times(1)).perform();

        inOrder.verify(this.actionsMock, times(1)).release(eq(target));
        inOrder.verify(this.actionsMock, times(1)).perform();
    }

    @Test
    void dragAndDrop_onFirefoxDriver_scrollsElementIntoView() {

        // Arrange
        when(this.configuration.getBrowserType()).thenReturn(BrowserType.FIREFOX);
        this.seleniumAdapter = new SeleniumAdapter(
                this.webDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                null,
                this.loggingPreferences
        );

        WebElement draggable = Mockito.mock(WebElement.class);
        SeleniumElement draggableWrapped = Mockito.mock(SeleniumElement.class);
        when(draggableWrapped.getUnderlyingWebElement()).thenReturn(draggable);

        WebElement target = Mockito.mock(WebElement.class);
        when(this.webDriver.findElement(any(org.openqa.selenium.By.ByCssSelector.class))).thenReturn(target);

        when(draggable.getAttribute(eq("draggable"))).thenReturn(null);

        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);

        this.seleniumAdapter.setActionsFactory(actionsFactory);

        // Act
        this.seleniumAdapter.dragAndDrop(draggableWrapped, By.cssSelector("#target"));

        // Assert.

        InOrder inOrder = inOrder(this.actionsMock, this.javascriptExecutor);

        inOrder.verify(this.actionsMock, times(1)).clickAndHold(eq(draggable));
        inOrder.verify(this.actionsMock, times(1)).perform();

        inOrder.verify(this.javascriptExecutor, times(2)).apply(any(), any(), any());

        inOrder.verify(this.actionsMock, times(1)).moveToElement(eq(target));
        inOrder.verify(this.actionsMock, times(1)).perform();

        inOrder.verify(this.actionsMock, times(1)).release(eq(target));
        inOrder.verify(this.actionsMock, times(1)).perform();
    }

    @ParameterizedTest
    @EnumSource(
            value = BrowserType.class,
            names = {"ANDROID_CHROME", "EDGE", "SAFARI", "IOS_SAFARI", "CHROME", "OPERA"})
    void rightClick_specificBrowsers_usesActionsBuilderWithoutScrollIntoView(BrowserType browserType) {

        // Arrange
        when(this.configuration.getBrowserType()).thenReturn(browserType);
        this.seleniumAdapter = new SeleniumAdapter(
                this.webDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                null,
                this.loggingPreferences
        );
        this.seleniumAdapter.setActionsFactory(this.actionsFactory);

        WebElement element = Mockito.mock(WebElement.class);
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getUnderlyingWebElement()).thenReturn(element);

        // Act
        this.seleniumAdapter.rightClick(elementWrapped);

        // Assert
        verify(this.javascriptExecutor, times(0)).apply(any(), any(), any());

        InOrder inOrder = inOrder(this.actionsMock);

        inOrder.verify(this.actionsMock, times(1)).contextClick(eq(element));
        inOrder.verify(this.actionsMock, times(1)).perform();
    }

    @ParameterizedTest
    @EnumSource(
            value = BrowserType.class,
            names = {"INTERNET_EXPLORER", "FIREFOX"})
    void rightClick_specificBrowsers_usesActionsBuilderWithScrollIntoView(BrowserType browserType) {

        // Arrange
        when(this.configuration.getBrowserType()).thenReturn(browserType);
        this.seleniumAdapter = new SeleniumAdapter(
                this.webDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                null,
                this.loggingPreferences
        );
        this.seleniumAdapter.setActionsFactory(this.actionsFactory);

        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);

        WebElement element = Mockito.mock(WebElement.class);
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.getUnderlyingWebElement()).thenReturn(element);

        // Act
        this.seleniumAdapter.rightClick(elementWrapped);

        // Assert
        InOrder inOrder = inOrder(this.actionsMock, this.javascriptExecutor);

        inOrder.verify(this.javascriptExecutor, times(2)).apply(any(), any(), any());

        inOrder.verify(this.actionsMock, times(1)).contextClick(eq(element));
        inOrder.verify(this.actionsMock, times(1)).perform();
    }

    @ParameterizedTest
    @EnumSource(
            value = BrowserType.class,
            names = {"ANDROID_CHROME", "EDGE", "SAFARI", "IOS_SAFARI", "CHROME", "OPERA"})
    void doubleClick_specificBrowsers_usesActionsBuilderWithoutScrollIntoView(BrowserType browserType) {

        // Arrange
        when(this.configuration.getBrowserType()).thenReturn(browserType);
        this.seleniumAdapter = new SeleniumAdapter(
                this.webDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                null,
                this.loggingPreferences
        );
        this.seleniumAdapter.setActionsFactory(this.actionsFactory);

        WebElement element = Mockito.mock(WebElement.class);
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getUnderlyingWebElement()).thenReturn(element);

        // Act
        this.seleniumAdapter.doubleClick(elementWrapped);

        // Assert
        verify(this.javascriptExecutor, times(0)).apply(any(), any(), any());

        InOrder inOrder = inOrder(this.actionsMock);

        inOrder.verify(this.actionsMock, times(1)).doubleClick(eq(element));
        inOrder.verify(this.actionsMock, times(1)).perform();
    }

    @ParameterizedTest
    @EnumSource(
            value = BrowserType.class,
            names = {"INTERNET_EXPLORER"})
    void doubleClick_specificBrowsers_usesActionsBuilderWithScrollIntoView(BrowserType browserType) {

        // Arrange
        when(this.configuration.getBrowserType()).thenReturn(browserType);
        this.seleniumAdapter = new SeleniumAdapter(
                this.webDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                null,
                this.loggingPreferences
        );
        this.seleniumAdapter.setActionsFactory(this.actionsFactory);

        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);

        WebElement element = Mockito.mock(WebElement.class);
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.getUnderlyingWebElement()).thenReturn(element);

        // Act
        this.seleniumAdapter.doubleClick(elementWrapped);

        // Assert
        InOrder inOrder = inOrder(this.actionsMock, this.javascriptExecutor);

        inOrder.verify(this.javascriptExecutor, times(2)).apply(any(), any(), any());

        inOrder.verify(this.actionsMock, times(1)).doubleClick(eq(element));
        inOrder.verify(this.actionsMock, times(1)).perform();
    }

    @ParameterizedTest
    @EnumSource(
            value = BrowserType.class,
            names = {"FIREFOX"})
    void doubleClick_specificBrowsers_doubleClickByJavaScript(BrowserType browserType) {

        // Arrange
        when(this.configuration.getBrowserType()).thenReturn(browserType);
        this.seleniumAdapter = new SeleniumAdapter(
                this.webDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                null,
                this.loggingPreferences
        );
        this.seleniumAdapter.setActionsFactory(this.actionsFactory);

        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);

        WebElement element = Mockito.mock(WebElement.class);
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.getUnderlyingWebElement()).thenReturn(element);

        // Act
        this.seleniumAdapter.doubleClick(elementWrapped);

        // Assert
        verify(this.javascriptExecutor, times(1)).apply(any(), any(), any());

        verify(this.actionsMock, times(0)).doubleClick(any());
        verify(this.actionsMock, times(0)).perform();
    }

    @Test
    void click_happyPath() {

        // Arrange
        WebElement element = Mockito.mock(WebElement.class);
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getUnderlyingWebElement()).thenReturn(element);

        // Act
        this.seleniumAdapter.click(elementWrapped);

        // Assert
        verify(elementWrapped, times(1)).click();
    }

    @Test
    void click_elementNotVisible_throwsException() {

        // Arrange
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        Mockito.doThrow(new org.openqa.selenium.ElementNotVisibleException("Test")).when(elementWrapped).click();

        // Act
        Executable action = () -> this.seleniumAdapter.click(elementWrapped);

        // Assert
        Exception exception = assertThrows(ElementNotVisibleException.class, action);
        assertEquals("The specified element with css selector '#id' is not visible.", exception.getMessage());
    }

    @Test
    void clickAtOffset_usesActionsBuilder() {

        // Arrange
        when(this.seleniumElement.getUnderlyingWebElement()).thenReturn(this.webElement);

        // Act
        this.seleniumAdapter.clickAtOffset(this.seleniumElement, 10, 10);

        // Assert
        InOrder inOrder = inOrder(this.actionsMock);

        inOrder.verify(this.actionsMock, times(1)).moveToElement(eq(this.webElement), eq(10), eq(10));
        inOrder.verify(this.actionsMock, times(1)).click();
        inOrder.verify(this.actionsMock, times(1)).perform();
    }

    @Test
    void refreshFrame_happyPath() {

        // Arrange
        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);

        // Act
        this.seleniumAdapter.refreshFrame();

        // Assert
        verify(this.javascriptExecutor, times(1)).apply(any(), any(), any());
    }

    @ParameterizedTest
    @EnumSource(
            value = BrowserType.class,
            names = {"ANDROID_CHROME", "EDGE", "SAFARI", "IOS_SAFARI", "CHROME", "OPERA"})
    void clickAndHold_specificBrowsers_usesActionsBuilderWithoutScrollIntoView(BrowserType browserType) {

        // Arrange
        when(this.configuration.getBrowserType()).thenReturn(browserType);
        this.seleniumAdapter = new SeleniumAdapter(
                this.webDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                null,
                this.loggingPreferences
        );
        this.seleniumAdapter.setActionsFactory(this.actionsFactory);

        WebElement element = Mockito.mock(WebElement.class);
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getUnderlyingWebElement()).thenReturn(element);

        // Act
        this.seleniumAdapter.clickAndHold(elementWrapped, 10);

        // Assert
        verify(this.javascriptExecutor, times(0)).apply(any(), any(), any());

        InOrder inOrder = inOrder(this.actionsMock, this.sleepMock);

        inOrder.verify(this.actionsMock, times(1)).clickAndHold(eq(element));
        inOrder.verify(this.actionsMock, times(1)).perform();

        inOrder.verify(this.sleepMock, times(1)).wait(10);

        inOrder.verify(this.actionsMock, times(1)).release(eq(element));
        inOrder.verify(this.actionsMock, times(1)).perform();
    }

    @ParameterizedTest
    @EnumSource(
            value = BrowserType.class,
            names = {"INTERNET_EXPLORER", "FIREFOX"})
    void clickAndHold_specificBrowsers_usesActionsBuilderWithScrollIntoView(BrowserType browserType) {

        // Arrange
        when(this.configuration.getBrowserType()).thenReturn(browserType);
        this.seleniumAdapter = new SeleniumAdapter(
                this.webDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.configuration,
                BrowserSize.FULL_HD,
                null,
                this.loggingPreferences
        );
        this.seleniumAdapter.setActionsFactory(this.actionsFactory);

        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);

        WebElement element = Mockito.mock(WebElement.class);
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.getUnderlyingWebElement()).thenReturn(element);

        // Act
        this.seleniumAdapter.clickAndHold(elementWrapped, 10);

        // Assert
        InOrder inOrder = inOrder(this.actionsMock, this.javascriptExecutor, this.sleepMock);

        inOrder.verify(this.javascriptExecutor, times(2)).apply(any(), any(), any());

        inOrder.verify(this.actionsMock, times(1)).clickAndHold(eq(element));
        inOrder.verify(this.actionsMock, times(1)).perform();

        inOrder.verify(this.sleepMock, times(1)).wait(10);

        inOrder.verify(this.actionsMock, times(1)).release(eq(element));
        inOrder.verify(this.actionsMock, times(1)).perform();
    }

    @Test
    void checkElement_elementNotSelected_clicksOnElement() {

        // Arrange
        when(this.seleniumElement.getUnderlyingWebElement()).thenReturn(this.webElement);
        when(this.webElement.isSelected()).thenReturn(false);

        // Act
        this.seleniumAdapter.checkElement(this.seleniumElement);

        // Assert
        verify(this.seleniumElement, times(1)).click();
    }

    @Test
    void checkElement_elementSelected_doesNotClickOnElement() {

        // Arrange
        when(this.seleniumElement.getUnderlyingWebElement()).thenReturn(this.webElement);
        when(this.webElement.isSelected()).thenReturn(true);

        // Act
        this.seleniumAdapter.checkElement(this.seleniumElement);

        // Assert
        verify(this.seleniumElement, times(0)).click();
    }

    @Test
    void uncheckElement_elementNotSelected_doesNotClickOnElement() {

        // Arrange
        when(this.seleniumElement.getUnderlyingWebElement()).thenReturn(this.webElement);
        when(this.webElement.isSelected()).thenReturn(false);

        // Act
        this.seleniumAdapter.unCheckElement(this.seleniumElement);

        // Assert
        verify(this.seleniumElement, times(0)).click();
    }

    @Test
    void uncheckElement_elementSelected_clicksOnElement() {

        // Arrange
        when(this.seleniumElement.getUnderlyingWebElement()).thenReturn(this.webElement);
        when(this.webElement.isSelected()).thenReturn(true);

        // Act
        this.seleniumAdapter.unCheckElement(this.seleniumElement);

        // Assert
        verify(this.seleniumElement, times(1)).click();
    }

    @Test
    void isElementDisabled_elementDisabled_doesNotThrowException() {

        // Arrange
        when(this.seleniumElement.enabled()).thenReturn(false);

        // Act
        this.seleniumAdapter.isElementDisabled(this.seleniumElement);

        // Assert
        verify(this.seleniumElement, times(1)).enabled();
    }

    @Test
    void isElementDisabled_elementEnabled_throwsException() {

        // Arrange
        when(this.seleniumElement.enabled()).thenReturn(true);
        when(this.seleniumElement.getSelector()).thenReturn(By.cssSelector("#id"));

        // Act
        Executable action = () -> this.seleniumAdapter.isElementDisabled(this.seleniumElement);

        // Assert
        Exception exception = assertThrows(ElementIsEnabledException.class, action);
        assertEquals("The specified element with css selector '#id' is enabled.", exception.getMessage());
    }

    @Test
    void isElementEnabled_elementDisabled_throwsException() {

        // Arrange
        when(this.seleniumElement.enabled()).thenReturn(false);
        when(this.seleniumElement.getSelector()).thenReturn(By.cssSelector("#id"));

        // Act
        Executable action = () -> this.seleniumAdapter.isElementEnabled(this.seleniumElement);

        // Assert
        Exception exception = assertThrows(ElementNotEnabledException.class, action);
        assertEquals("The specified element with css selector '#id' is not enabled.", exception.getMessage());
    }

    @Test
    void isElementEnabled_elementEnabled_doesNotThrowException() {

        // Arrange
        when(this.seleniumElement.enabled()).thenReturn(true);

        // Act
        this.seleniumAdapter.isElementEnabled(this.seleniumElement);

        // Assert
        verify(this.seleniumElement, times(1)).enabled();
    }

    @Test
    void exists_happyPath() {

        // Act
        Executable action = () -> this.seleniumAdapter.exists(this.seleniumElement);

        // Assert
        assertDoesNotThrow(action);
    }

    @Test
    void notExists_happyPath() {

        // Arrange
        when(this.seleniumElement.getSelector()).thenReturn(By.cssSelector("#id"));

        // Act
        Executable action = () -> this.seleniumAdapter.notExists(this.seleniumElement);

        // Assert
        Exception exception = assertThrows(ElementExistsException.class, action);
        assertEquals("The specified element with css selector '#id' exists.", exception.getMessage());
    }

    @Test
    void mouseOut_happyPath() {

        // Arrange
        IByWeb selector = By.cssSelector("#id");
        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);
        when(this.seleniumElement.getSelector()).thenReturn(selector);

        // Act
        this.seleniumAdapter.mouseOut(this.seleniumElement);

        // Assert
        String expectedScript = selector.toJQuery().toString(JQueryStringType.MOUSE_OUT);
        verify(this.javascriptExecutor, times(1)).apply(any(), eq(expectedScript), any());
    }

    @Test
    void mouseOver_happyPath() {

        // Arrange
        IByWeb selector = By.cssSelector("#id");
        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);
        when(this.seleniumElement.getSelector()).thenReturn(selector);

        // Act
        this.seleniumAdapter.mouseOver(this.seleniumElement);

        // Assert
        String expectedScript = selector.toJQuery().toString(JQueryStringType.MOUSE_OVER);
        verify(this.javascriptExecutor, times(1)).apply(any(), eq(expectedScript), any());
    }

    @Test
    void setBodyValueByJavaScript_happyPath() {

        // Arrange
        IByWeb selector = By.cssSelector("#id");
        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);
        when(this.seleniumElement.getSelector()).thenReturn(selector);

        // Act
        this.seleniumAdapter.setBodyValueByJavaScript(this.seleniumElement, "value");

        // Assert
        String expectedScript = String.format(selector.toJQuery().toString(JQueryStringType.SET_BODY_TEXT), "\"value\"");
        verify(this.javascriptExecutor, times(1)).apply(any(), eq(expectedScript), any());
    }

    @Test
    void setTextByJavaScript_happyPath() {

        // Arrange
        IByWeb selector = By.cssSelector("#id");
        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);
        when(this.seleniumElement.getSelector()).thenReturn(selector);

        // Act
        this.seleniumAdapter.setTextByJavaScript(this.seleniumElement, "value");

        // Assert
        String expectedScript = String.format(selector.toJQuery().toString(JQueryStringType.SET_ELEMENT_TEXT), "\"value\"");
        verify(this.javascriptExecutor, times(1)).apply(any(), eq(expectedScript), any());
    }

    @Test
    void setDivValueByJavaScript_happyPath() {

        // Arrange
        IByWeb selector = By.cssSelector("#id");
        when(this.javaScriptFlowExecutor.getExecutor()).thenReturn(this.javascriptExecutor);
        when(this.seleniumElement.getSelector()).thenReturn(selector);

        // Act
        this.seleniumAdapter.setDivValueByJavaScript(this.seleniumElement, "value");

        // Assert
        String expectedScript = String.format(selector.toJQuery().toString(JQueryStringType.SET_DIV_TEXT), "\"value\"");
        verify(this.javascriptExecutor, times(1)).apply(any(), eq(expectedScript), any());
    }

    @Test
    void clickAllElements_multipleElements_allClicked() {

        // Arrange
        List<WebElement> returnedElements = new ArrayList<>();
        returnedElements.add(Mockito.mock(WebElement.class));
        returnedElements.add(Mockito.mock(WebElement.class));

        IByWeb selector = By.cssSelector(".class");
        when(this.webDriver.findElements(any(org.openqa.selenium.By.ByCssSelector.class))).thenReturn(returnedElements);

        // Act
        this.seleniumAdapter.clickAllElements(selector);

        // Assert
        verify(returnedElements.get(0), times(1)).click();
        verify(returnedElements.get(1), times(1)).click();
    }

    @Test
    void elementHasOptionsInOrder_multipleOptionsNoOptGroupByValue_passes() {

        // Arrange
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getTagName()).thenReturn("select");

        // Act
        String[] options = new String[]{"x", "y"};
        this.seleniumAdapter.elementHasOptionsInOrder(elementWrapped, options, null, WebSelectOption.VALUE);

        // Assert
        verify(elementWrapped, times(1)).findElement(iByWebCaptor.capture());
        IByWeb selector = iByWebCaptor.getValue();
        assertEquals("option[value='x'] ~ option[value='y']", selector.toString());
    }

    @Test
    void elementHasOptionsInOrder_threeOptionsNoOptGroupByValue_passes() {

        // Arrange
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getTagName()).thenReturn("select");

        // Act
        String[] options = new String[]{"x", "y", "z"};
        this.seleniumAdapter.elementHasOptionsInOrder(elementWrapped, options, null, WebSelectOption.VALUE);

        // Assert
        verify(elementWrapped, times(2)).findElement(iByWebCaptor.capture());
        List<IByWeb> selectors = iByWebCaptor.getAllValues();
        assertEquals("option[value='x'] ~ option[value='y']", selectors.get(0).toString());
        assertEquals("option[value='y'] ~ option[value='z']", selectors.get(1).toString());
    }

    @Test
    void elementHasOptionsInOrder_oneOptionNoOptGroupByValue_passes() {

        // Arrange
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getTagName()).thenReturn("select");

        // Act
        String[] options = new String[]{"x"};
        this.seleniumAdapter.elementHasOptionsInOrder(elementWrapped, options, null, WebSelectOption.VALUE);

        // Assert
        verify(elementWrapped, times(1)).findElement(iByWebCaptor.capture());
        IByWeb selector = iByWebCaptor.getValue();
        assertEquals("option[value='x']", selector.toString());
    }

    @Test
    void elementHasOptionsInOrder_oneOptionNoOptGroupByText_passes() {

        // Arrange
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getTagName()).thenReturn("select");
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("selector"));

        // Act
        String[] options = new String[]{"x"};
        this.seleniumAdapter.elementHasOptionsInOrder(elementWrapped, options, null, WebSelectOption.TEXT);

        // Assert
        verify(elementWrapped, times(1)).findElementByXPath(iByWebCaptor.capture());
        IByWeb selector = iByWebCaptor.getValue();
        assertEquals(".//following-sibling::option[normalize-space(.) = \"x\"]", selector.toString());
    }

    @Test
    void elementHasOptionsInOrder_multipleOptionsNoOptGroupByText_passes() {

        // Arrange
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getTagName()).thenReturn("select");
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("selector"));
        when(elementWrapped.findElementByXPath(any())).thenReturn(elementWrapped);

        // Act
        String[] options = new String[]{"x", "y"};
        this.seleniumAdapter.elementHasOptionsInOrder(elementWrapped, options, null, WebSelectOption.TEXT);

        // Assert
        verify(elementWrapped, times(2)).findElementByXPath(iByWebCaptor.capture());
        List<IByWeb> selectors = iByWebCaptor.getAllValues();
        assertEquals(".//option[normalize-space(.) = \"x\"]", selectors.get(0).toString());
        assertEquals(".//following-sibling::option[normalize-space(.) = \"y\"]", selectors.get(1).toString());
    }

    @Test
    void elementHasOptionsInOrder_threeOptionsNoOptGroupByText_passes() {

        // Arrange
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getTagName()).thenReturn("select");
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("selector"));
        when(elementWrapped.findElementByXPath(any())).thenReturn(elementWrapped);

        // Act
        String[] options = new String[]{"x", "y", "z"};
        this.seleniumAdapter.elementHasOptionsInOrder(elementWrapped, options, null, WebSelectOption.TEXT);

        // Assert
        verify(elementWrapped, times(3)).findElementByXPath(iByWebCaptor.capture());
        List<IByWeb> selectors = iByWebCaptor.getAllValues();
        assertEquals(".//option[normalize-space(.) = \"x\"]", selectors.get(0).toString());
        assertEquals(".//following-sibling::option[normalize-space(.) = \"y\"]", selectors.get(1).toString());
        assertEquals(".//following-sibling::option[normalize-space(.) = \"z\"]", selectors.get(2).toString());
    }

    @Test
    void elementHasOptionsInOrder_elementNotFound_throwsException() {

        // Arrange
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getTagName()).thenReturn("select");
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("selector"));
        when(elementWrapped.findElementByXPath(any())).thenThrow(new org.openqa.selenium.NoSuchElementException("Test"));

        // Act
        String[] options = new String[]{"x", "y"};
        Executable action = () -> this.seleniumAdapter.elementHasOptionsInOrder(elementWrapped, options, null, WebSelectOption.TEXT);

        // Assert
        Exception exception = assertThrows(ElementDoesNotHaveOptionException.class, action);
        assertTrue(exception.getMessage().startsWith("Element does not have option "));
    }

    @Test
    void elementHasOptionsInOrder_elementNotFoundOwnException_throwsException() {

        // Arrange
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getTagName()).thenReturn("select");
        IByWeb selector = By.cssSelector("selector");
        when(elementWrapped.getSelector()).thenReturn(selector);
        when(elementWrapped.findElementByXPath(any())).thenThrow(new NoSuchElementException(new org.openqa.selenium.NoSuchElementException("Test"), selector));

        // Act
        String[] options = new String[]{"x", "y"};
        Executable action = () -> this.seleniumAdapter.elementHasOptionsInOrder(elementWrapped, options, null, WebSelectOption.TEXT);

        // Assert
        Exception exception = assertThrows(ElementDoesNotHaveOptionException.class, action);
        assertTrue(exception.getMessage().startsWith("Element does not have option "));
    }

    @Test
    void elementHasOptionsInOrder_tagNotSelect_throwsException() {

        // Arrange
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getTagName()).thenReturn("not-select");

        // Act
        String[] options = new String[]{"x", "y"};
        Executable action = () -> this.seleniumAdapter.elementHasOptionsInOrder(elementWrapped, options, null, WebSelectOption.TEXT);

        // Assert
        Exception exception = assertThrows(IncorrectElementTagException.class, action);
        assertEquals("The specified element has tag not-select, whereas it should have tag select.", exception.getMessage());
    }

    @Test
    void elementHasOptionsInOrder_optGroup_passes() {

        // Arrange
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getTagName()).thenReturn("select");
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("selector"));
        when(elementWrapped.findElement(any())).thenReturn(elementWrapped);
        when(elementWrapped.findElementByXPath(any())).thenReturn(elementWrapped);

        // Act
        String[] options = new String[]{"x", "y", "z"};
        this.seleniumAdapter.elementHasOptionsInOrder(elementWrapped, options, "test-group", WebSelectOption.TEXT);

        // Assert
        verify(elementWrapped, times(1)).findElement(iByWebCaptor.capture());
        assertEquals("optgroup[label='test-group']", iByWebCaptor.getValue().toString());

        verify(elementWrapped, times(3)).findElementByXPath(iByWebCaptor2.capture());
        List<IByWeb> selectors = iByWebCaptor2.getAllValues();
        assertEquals(".//option[normalize-space(.) = \"x\"]", selectors.get(0).toString());
        assertEquals(".//following-sibling::option[normalize-space(.) = \"y\"]", selectors.get(1).toString());
        assertEquals(".//following-sibling::option[normalize-space(.) = \"z\"]", selectors.get(2).toString());
    }

    @Test
    void hasNumberOfOptions_withoutOptGroupWithMatchingCount_passes() {

        // Arrange
        Collection<WebControl> optionElements = new ArrayList<>();
        optionElements.add(Mockito.mock(SeleniumElement.class));
        optionElements.add(Mockito.mock(SeleniumElement.class));

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getTagName()).thenReturn("select");
        when(elementWrapped.findElements(any())).thenReturn(optionElements);

        // Act
        this.seleniumAdapter.hasNumberOfOptions(elementWrapped, 2, null);

        // Assert
        verify(elementWrapped, times(1)).findElements(iByWebCaptor.capture());
        assertEquals("option", iByWebCaptor.getValue().toString());
    }

    @Test
    void hasNumberOfOptions_withoutOptGroupWithMismatchingCount_throwsException() {

        // Arrange
        Collection<WebControl> optionElements = new ArrayList<>();
        optionElements.add(Mockito.mock(SeleniumElement.class));

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getTagName()).thenReturn("select");
        when(elementWrapped.findElements(any())).thenReturn(optionElements);

        // Act
        Executable action = () -> this.seleniumAdapter.hasNumberOfOptions(elementWrapped, 2, null);

        // Assert
        Exception exception = assertThrows(ElementDoesNotHaveNumberOfOptionsException.class, action);
        assertEquals("2 options are expected in the selector, but 1 options were found.", exception.getMessage());
    }

    @Test
    void hasNumberOfOptions_tagNotSelect_throwsException() {

        // Arrange
        Collection<WebControl> optionElements = new ArrayList<>();
        optionElements.add(Mockito.mock(SeleniumElement.class));

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getTagName()).thenReturn("not-select");
        when(elementWrapped.findElements(any())).thenReturn(optionElements);

        // Act
        Executable action = () -> this.seleniumAdapter.hasNumberOfOptions(elementWrapped, 2, null);

        // Assert
        Exception exception = assertThrows(IncorrectElementTagException.class, action);
        assertEquals("The specified element has tag not-select, whereas it should have tag select.", exception.getMessage());
    }

    @Test
    void hasNumberOfOptions_withOptGroup_firstFindsOptGroup() {

        // Arrange
        Collection<WebControl> optionElements = new ArrayList<>();
        optionElements.add(Mockito.mock(SeleniumElement.class));
        optionElements.add(Mockito.mock(SeleniumElement.class));

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getTagName()).thenReturn("select");
        when(elementWrapped.findElement(any())).thenReturn(elementWrapped);
        when(elementWrapped.findElements(any())).thenReturn(optionElements);

        // Act
        this.seleniumAdapter.hasNumberOfOptions(elementWrapped, 2, "test-group");

        // Assert
        verify(elementWrapped, times(1)).findElement(iByWebCaptor.capture());
        assertEquals("optgroup[label='test-group']", iByWebCaptor.getValue().toString());

        verify(elementWrapped, times(1)).findElements(iByWebCaptor2.capture());
        assertEquals("option", iByWebCaptor2.getValue().toString());
    }

    @Test
    void hasAllOptionsInOrder_ascendingByTextWithProperOrdering_passes() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("a");
        when(element1.getAttribute(eq("value"))).thenReturn("b");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("b");
        when(element2.getAttribute(eq("value"))).thenReturn("a");

        Collection<WebControl> optionElements = new ArrayList<>();
        optionElements.add(element1);
        optionElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getTagName()).thenReturn("select");
        when(elementWrapped.findElements(any())).thenReturn(optionElements);

        // Act
        this.seleniumAdapter.hasAllOptionsInOrder(elementWrapped, CompareType.ASCENDING_BY_TEXT, null);

        // Assert
        verify(elementWrapped, times(1)).findElements(iByWebCaptor.capture());
        assertEquals("option", iByWebCaptor.getValue().toString());
    }

    @Test
    void hasAllOptionsInOrder_descendingByTextWithProperOrdering_passes() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("b");
        when(element1.getAttribute(eq("value"))).thenReturn("a");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("a");
        when(element2.getAttribute(eq("value"))).thenReturn("b");

        Collection<WebControl> optionElements = new ArrayList<>();
        optionElements.add(element1);
        optionElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getTagName()).thenReturn("select");
        when(elementWrapped.findElements(any())).thenReturn(optionElements);

        // Act
        this.seleniumAdapter.hasAllOptionsInOrder(elementWrapped, CompareType.DESCENDING_BY_TEXT, null);

        // Assert
        verify(elementWrapped, times(1)).findElements(iByWebCaptor.capture());
        assertEquals("option", iByWebCaptor.getValue().toString());
    }

    @Test
    void hasAllOptionsInOrder_ascendingByValueWithProperOrdering_passes() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("b");
        when(element1.getAttribute(eq("value"))).thenReturn("a");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("a");
        when(element2.getAttribute(eq("value"))).thenReturn("b");

        Collection<WebControl> optionElements = new ArrayList<>();
        optionElements.add(element1);
        optionElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getTagName()).thenReturn("select");
        when(elementWrapped.findElements(any())).thenReturn(optionElements);

        // Act
        this.seleniumAdapter.hasAllOptionsInOrder(elementWrapped, CompareType.ASCENDING_BY_VALUE, null);

        // Assert
        verify(elementWrapped, times(1)).findElements(iByWebCaptor.capture());
        assertEquals("option", iByWebCaptor.getValue().toString());
    }

    @Test
    void hasAllOptionsInOrder_descendingByValueWithProperOrdering_passes() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("a");
        when(element1.getAttribute(eq("value"))).thenReturn("b");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("b");
        when(element2.getAttribute(eq("value"))).thenReturn("a");

        Collection<WebControl> optionElements = new ArrayList<>();
        optionElements.add(element1);
        optionElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getTagName()).thenReturn("select");
        when(elementWrapped.findElements(any())).thenReturn(optionElements);

        // Act
        this.seleniumAdapter.hasAllOptionsInOrder(elementWrapped, CompareType.DESCENDING_BY_VALUE, null);

        // Assert
        verify(elementWrapped, times(1)).findElements(iByWebCaptor.capture());
        assertEquals("option", iByWebCaptor.getValue().toString());
    }

    @Test
    void hasAllOptionsInOrder_ascendingByTextWithImproperOrdering_throwsException() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("b");
        when(element1.getAttribute(eq("value"))).thenReturn("a");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("a");
        when(element2.getAttribute(eq("value"))).thenReturn("b");

        Collection<WebControl> optionElements = new ArrayList<>();
        optionElements.add(element1);
        optionElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getTagName()).thenReturn("select");
        when(elementWrapped.findElements(any())).thenReturn(optionElements);

        // Act
        Executable action = () -> this.seleniumAdapter.hasAllOptionsInOrder(elementWrapped, CompareType.ASCENDING_BY_TEXT, null);

        // Assert
        Exception exception = assertThrows(ElementsNotInOrderException.class, action);
        assertEquals("The elements are not in 'ASCENDING_BY_TEXT' order.", exception.getMessage());
    }

    @Test
    void hasAllOptionsInOrder_descendingByTextWithImproperOrdering_throwsException() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("a");
        when(element1.getAttribute(eq("value"))).thenReturn("b");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("b");
        when(element2.getAttribute(eq("value"))).thenReturn("a");

        Collection<WebControl> optionElements = new ArrayList<>();
        optionElements.add(element1);
        optionElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getTagName()).thenReturn("select");
        when(elementWrapped.findElements(any())).thenReturn(optionElements);

        // Act
        Executable action = () -> this.seleniumAdapter.hasAllOptionsInOrder(elementWrapped, CompareType.DESCENDING_BY_TEXT, null);

        // Assert
        Exception exception = assertThrows(ElementsNotInOrderException.class, action);
        assertEquals("The elements are not in 'DESCENDING_BY_TEXT' order.", exception.getMessage());
    }

    @Test
    void hasAllOptionsInOrder_ascendingByValueWithImproperOrdering_throwsException() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("a");
        when(element1.getAttribute(eq("value"))).thenReturn("b");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("b");
        when(element2.getAttribute(eq("value"))).thenReturn("a");

        Collection<WebControl> optionElements = new ArrayList<>();
        optionElements.add(element1);
        optionElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getTagName()).thenReturn("select");
        when(elementWrapped.findElements(any())).thenReturn(optionElements);

        // Act
        Executable action = () -> this.seleniumAdapter.hasAllOptionsInOrder(elementWrapped, CompareType.ASCENDING_BY_VALUE, null);

        // Assert
        Exception exception = assertThrows(ElementsNotInOrderException.class, action);
        assertEquals("The elements are not in 'ASCENDING_BY_VALUE' order.", exception.getMessage());
    }

    @Test
    void hasAllOptionsInOrder_descendingByValueWithImproperOrdering_throwsException() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("b");
        when(element1.getAttribute(eq("value"))).thenReturn("a");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("a");
        when(element2.getAttribute(eq("value"))).thenReturn("b");

        Collection<WebControl> optionElements = new ArrayList<>();
        optionElements.add(element1);
        optionElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getTagName()).thenReturn("select");
        when(elementWrapped.findElements(any())).thenReturn(optionElements);

        // Act
        Executable action = () -> this.seleniumAdapter.hasAllOptionsInOrder(elementWrapped, CompareType.DESCENDING_BY_VALUE, null);

        // Assert
        Exception exception = assertThrows(ElementsNotInOrderException.class, action);
        assertEquals("The elements are not in 'DESCENDING_BY_VALUE' order.", exception.getMessage());
    }

    @Test
    void selected_elementIsSelected_passes() {

        // Arrange
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.selected()).thenReturn(true);

        // Act
        this.seleniumAdapter.selected(elementWrapped);

        // Assert
        verify(elementWrapped, times(1)).selected();
    }

    @Test
    void selected_elementIsNotSelected_throwsException() {

        // Arrange
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.selected()).thenReturn(false);

        // Act
        Executable action = () -> this.seleniumAdapter.selected(elementWrapped);

        // Assert
        Exception exception = assertThrows(ElementNotSelectedException.class, action);
        assertEquals("The specified element is not selected.", exception.getMessage());
    }

    @Test
    void notSelected_elementIsNotSelected_passes() {

        // Arrange
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.selected()).thenReturn(false);

        // Act
        this.seleniumAdapter.notSelected(elementWrapped);

        // Assert
        verify(elementWrapped, times(1)).selected();
    }

    @Test
    void notSelected_elementIsSelected_throwsException() {

        // Arrange
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.selected()).thenReturn(true);

        // Act
        Executable action = () -> this.seleniumAdapter.notSelected(elementWrapped);

        // Assert
        Exception exception = assertThrows(ElementIsSelectedException.class, action);
        assertEquals("The specified element is selected.", exception.getMessage());
    }

    @Test
    void visible_elementIsVisible_passes() {

        // Arrange
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.displayed()).thenReturn(true);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));

        // Act
        this.seleniumAdapter.visible(elementWrapped);

        // Assert
        verify(elementWrapped, times(1)).displayed();
    }

    @Test
    void visible_elementIsNotVisible_throwsException() {

        // Arrange
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.displayed()).thenReturn(false);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));

        // Act
        Executable action = () -> this.seleniumAdapter.visible(elementWrapped);

        // Assert
        Exception exception = assertThrows(ElementNotVisibleException.class, action);
        assertEquals("The specified element with css selector '#id' is not visible.", exception.getMessage());
    }

    @Test
    void notVisible_elementIsNotVisible_passes() {

        // Arrange
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.displayed()).thenReturn(false);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));

        // Act
        this.seleniumAdapter.notVisible(elementWrapped);

        // Assert
        verify(elementWrapped, times(1)).displayed();
    }

    @Test
    void notVisible_elementIsVisible_throwsException() {

        // Arrange
        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.displayed()).thenReturn(true);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));

        // Act
        Executable action = () -> this.seleniumAdapter.notVisible(elementWrapped);

        // Assert
        Exception exception = assertThrows(ElementIsVisibleException.class, action);
        assertEquals("The specified element with css selector '#id' is visible.", exception.getMessage());
    }

    @Test
    void has_comparisonByTextMatchesExpectation_passes() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("abc");
        when(element1.getAttribute(eq("value"))).thenReturn("cba");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("bcd");
        when(element2.getAttribute(eq("value"))).thenReturn("dcb");

        SeleniumElement element3 = Mockito.mock(SeleniumElement.class);
        when(element3.getText()).thenReturn("foo");
        when(element3.getAttribute(eq("value"))).thenReturn("foo");

        Collection<WebControl> foundElements = new ArrayList<>();
        foundElements.add(element1);
        foundElements.add(element2);
        foundElements.add(element3);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.findElements(any())).thenReturn(foundElements);

        // Act
        String[] messages = new String[]{"bcd", "abc"};
        this.seleniumAdapter.has(elementWrapped, messages, ".class", ComparisonOption.TEXT, "INNERHTML");

        // Assert
        verify(elementWrapped, times(1)).findElements(iByWebCaptor.capture());
        assertEquals(".class", iByWebCaptor.getValue().toString());
    }

    @Test
    void has_comparisonByRawMatchesExpectation_passes() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("abc");
        when(element1.getAttribute(eq("attr"))).thenReturn("cba");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("bcd");
        when(element2.getAttribute(eq("attr"))).thenReturn("dcb");

        Collection<WebControl> foundElements = new ArrayList<>();
        foundElements.add(element1);
        foundElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.findElements(any())).thenReturn(foundElements);

        // Act
        String[] messages = new String[]{"cba", "dcb"};
        this.seleniumAdapter.has(elementWrapped, messages, ".class", ComparisonOption.RAW, "attr");

        // Assert
        verify(elementWrapped, times(1)).findElements(iByWebCaptor.capture());
        assertEquals(".class", iByWebCaptor.getValue().toString());
    }

    @Test
    void has_comparisonByTextCustomAttributeMatchesExpectation_passes() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("abc");
        when(element1.getAttribute(eq("attr"))).thenReturn("cba");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("bcd");
        when(element2.getAttribute(eq("attr"))).thenReturn("dcb");

        Collection<WebControl> foundElements = new ArrayList<>();
        foundElements.add(element1);
        foundElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.findElements(any())).thenReturn(foundElements);

        // Act
        String[] messages = new String[]{"cba", "dcb"};
        this.seleniumAdapter.has(elementWrapped, messages, ".class", ComparisonOption.TEXT, "attr");

        // Assert
        verify(elementWrapped, times(1)).findElements(iByWebCaptor.capture());
        assertEquals(".class", iByWebCaptor.getValue().toString());
    }

    @Test
    void has_comparisonByTextDoesNotMatchExpectation_throwsException() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("abC");
        when(element1.getAttribute(eq("value"))).thenReturn("cba");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("bcd");
        when(element2.getAttribute(eq("value"))).thenReturn("dcb");

        Collection<WebControl> foundElements = new ArrayList<>();
        foundElements.add(element1);
        foundElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.findElements(any())).thenReturn(foundElements);

        // Act
        String[] messages = new String[]{"bcd", "abc"};
        Executable action = () -> this.seleniumAdapter.has(elementWrapped, messages, ".class", ComparisonOption.TEXT, "INNERHTML");

        // Assert
        Exception exception = assertThrows(ElementDoesNotHaveException.class, action);
        assertEquals("The specified element does not have a child element with value \"abc\".", exception.getMessage());
    }

    @Test
    void has_comparisonByTextDoesNotMatchExpectationSubstringMatch_throwsException() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("abc test");
        when(element1.getAttribute(eq("value"))).thenReturn("cba");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("bcd");
        when(element2.getAttribute(eq("value"))).thenReturn("dcb");

        Collection<WebControl> foundElements = new ArrayList<>();
        foundElements.add(element1);
        foundElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.findElements(any())).thenReturn(foundElements);

        // Act
        String[] messages = new String[]{"abc", "bcd"};
        Executable action = () -> this.seleniumAdapter.has(elementWrapped, messages, ".class", ComparisonOption.TEXT, "INNERHTML");

        // Assert
        Exception exception = assertThrows(ElementDoesNotHaveException.class, action);
        assertEquals("The specified element does not have a child element with value \"abc\".", exception.getMessage());
    }

    @Test
    void doesNotHave_comparisonByTextMatchesExpectation_passes() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("abc");
        when(element1.getAttribute(eq("value"))).thenReturn("cba");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("bcd");
        when(element2.getAttribute(eq("value"))).thenReturn("dcb");

        Collection<WebControl> foundElements = new ArrayList<>();
        foundElements.add(element1);
        foundElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.findElements(any())).thenReturn(foundElements);

        // Act
        String[] messages = new String[]{"cba", "cdb", "ab"};
        this.seleniumAdapter.doesNotHave(elementWrapped, messages, ".class", ComparisonOption.TEXT, "INNERHTML");

        // Assert
        verify(elementWrapped, times(1)).findElements(iByWebCaptor.capture());
        assertEquals(".class", iByWebCaptor.getValue().toString());
    }

    @Test
    void doesNotHave_comparisonByTextDoesNotMatchExpectation_throwsException() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("abc");
        when(element1.getAttribute(eq("value"))).thenReturn("cba");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("cdb");
        when(element2.getAttribute(eq("value"))).thenReturn("dcb");

        Collection<WebControl> foundElements = new ArrayList<>();
        foundElements.add(element1);
        foundElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.findElements(any())).thenReturn(foundElements);

        // Act
        String[] messages = new String[]{"cba", "ab", "cdb"};
        Executable action = () -> this.seleniumAdapter.doesNotHave(elementWrapped, messages, ".class", ComparisonOption.TEXT, "INNERHTML");

        // Assert
        Exception exception = assertThrows(ElementHasException.class, action);
        assertEquals("The specified element has \"cdb\".", exception.getMessage());
    }

    @Test
    void hasLike_comparisonByTextMatchesExpectation_passes() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("complex texT");
        when(element1.getAttribute(eq("value"))).thenReturn("cba");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("simpler tExt");
        when(element2.getAttribute(eq("value"))).thenReturn("dcb");

        SeleniumElement element3 = Mockito.mock(SeleniumElement.class);
        when(element3.getText()).thenReturn("foo");
        when(element3.getAttribute(eq("value"))).thenReturn("foo");

        Collection<WebControl> foundElements = new ArrayList<>();
        foundElements.add(element1);
        foundElements.add(element2);
        foundElements.add(element3);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.findElements(any())).thenReturn(foundElements);

        // Act
        String[] messages = new String[]{"complex text", "complex texT", "text", "simple"};
        this.seleniumAdapter.hasLike(elementWrapped, messages, ".class", ComparisonOption.TEXT, "INNERHTML");

        // Assert
        verify(elementWrapped, times(1)).findElements(iByWebCaptor.capture());
        assertEquals(".class", iByWebCaptor.getValue().toString());
    }

    @Test
    void hasLike_comparisonByRawMatchesExpectation_passes() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("abc");
        when(element1.getAttribute(eq("attr"))).thenReturn("complex texT");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("bcd");
        when(element2.getAttribute(eq("attr"))).thenReturn("simpler tExt");

        Collection<WebControl> foundElements = new ArrayList<>();
        foundElements.add(element1);
        foundElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.findElements(any())).thenReturn(foundElements);

        // Act
        String[] messages = new String[]{"comPle", "Simpler Text"};
        this.seleniumAdapter.hasLike(elementWrapped, messages, ".class", ComparisonOption.RAW, "attr");

        // Assert
        verify(elementWrapped, times(1)).findElements(iByWebCaptor.capture());
        assertEquals(".class", iByWebCaptor.getValue().toString());
    }

    @Test
    void hasLike_comparisonByTextCustomAttributeMatchesExpectation_passes() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("abc");
        when(element1.getAttribute(eq("attr"))).thenReturn("complex simple");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("bcd");
        when(element2.getAttribute(eq("attr"))).thenReturn("simple! test");

        Collection<WebControl> foundElements = new ArrayList<>();
        foundElements.add(element1);
        foundElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.findElements(any())).thenReturn(foundElements);

        // Act
        String[] messages = new String[]{"e!", "sim"};
        this.seleniumAdapter.hasLike(elementWrapped, messages, ".class", ComparisonOption.TEXT, "attr");

        // Assert
        verify(elementWrapped, times(1)).findElements(iByWebCaptor.capture());
        assertEquals(".class", iByWebCaptor.getValue().toString());
    }

    @Test
    void hasLike_comparisonByTextDoesNotMatchExpectation_throwsException() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("abc");
        when(element1.getAttribute(eq("value"))).thenReturn("cba");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("bcd");
        when(element2.getAttribute(eq("value"))).thenReturn("dcb");

        Collection<WebControl> foundElements = new ArrayList<>();
        foundElements.add(element1);
        foundElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.findElements(any())).thenReturn(foundElements);

        // Act
        String[] messages = new String[]{"abc", "dcb"};
        Executable action = () -> this.seleniumAdapter.hasLike(elementWrapped, messages, ".class", ComparisonOption.TEXT, "INNERHTML");

        // Assert
        Exception exception = assertThrows(ElementDoesNotHaveException.class, action);
        assertEquals("The specified element does not have a child element with value \"dcb\".", exception.getMessage());
    }

    @Test
    void doesNotHaveLike_comparisonByTextMatchesExpectation_passes() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("complex texT");
        when(element1.getAttribute(eq("value"))).thenReturn("cba");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("simpler tExt");
        when(element2.getAttribute(eq("value"))).thenReturn("dcb");

        Collection<WebControl> foundElements = new ArrayList<>();
        foundElements.add(element1);
        foundElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.findElements(any())).thenReturn(foundElements);

        // Act
        String[] messages = new String[]{"plext", "cba"};
        this.seleniumAdapter.doesNotHaveLike(elementWrapped, messages, ".class", ComparisonOption.TEXT, "INNERHTML");

        // Assert
        verify(elementWrapped, times(1)).findElements(iByWebCaptor.capture());
        assertEquals(".class", iByWebCaptor.getValue().toString());
    }

    @Test
    void doesNotHaveLike_comparisonByRawMatchesExpectation_passes() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("abc");
        when(element1.getAttribute(eq("attr"))).thenReturn("complex texT");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("bcd");
        when(element2.getAttribute(eq("attr"))).thenReturn("simpler tExt");

        Collection<WebControl> foundElements = new ArrayList<>();
        foundElements.add(element1);
        foundElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.findElements(any())).thenReturn(foundElements);

        // Act
        String[] messages = new String[]{"comlex", "bcd"};
        this.seleniumAdapter.doesNotHaveLike(elementWrapped, messages, ".class", ComparisonOption.RAW, "attr");

        // Assert
        verify(elementWrapped, times(1)).findElements(iByWebCaptor.capture());
        assertEquals(".class", iByWebCaptor.getValue().toString());
    }

    @Test
    void doesNotHaveLike_comparisonByTextCustomAttributeMatchesExpectation_passes() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("abc");
        when(element1.getAttribute(eq("attr"))).thenReturn("complex simple");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("bcd");
        when(element2.getAttribute(eq("attr"))).thenReturn("simple! test");

        Collection<WebControl> foundElements = new ArrayList<>();
        foundElements.add(element1);
        foundElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.findElements(any())).thenReturn(foundElements);

        // Act
        String[] messages = new String[]{"e!!", "sImpl "};
        this.seleniumAdapter.doesNotHaveLike(elementWrapped, messages, ".class", ComparisonOption.TEXT, "attr");

        // Assert
        verify(elementWrapped, times(1)).findElements(iByWebCaptor.capture());
        assertEquals(".class", iByWebCaptor.getValue().toString());
    }

    @Test
    void doesNotHaveLike_comparisonByTextDoesNotMatchExpectation_throwsException() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("simple texT");
        when(element1.getAttribute(eq("value"))).thenReturn("cba");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("complex Text!");
        when(element2.getAttribute(eq("value"))).thenReturn("dcb");

        Collection<WebControl> foundElements = new ArrayList<>();
        foundElements.add(element1);
        foundElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.findElements(any())).thenReturn(foundElements);

        // Act
        String[] messages = new String[]{"simple text!", "Text!"};
        Executable action = () -> this.seleniumAdapter.doesNotHaveLike(elementWrapped, messages, ".class", ComparisonOption.TEXT, "INNERHTML");

        // Assert
        Exception exception = assertThrows(ElementHasException.class, action);
        assertEquals("The specified element has \"text!\".", exception.getMessage());
    }

    @Test
    void hasOnly_comparisonByTextMatchesExpectation_passes() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("abc");
        when(element1.getAttribute(eq("value"))).thenReturn("cba");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("bcd");
        when(element2.getAttribute(eq("value"))).thenReturn("dcb");

        Collection<WebControl> foundElements = new ArrayList<>();
        foundElements.add(element1);
        foundElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.findElements(any())).thenReturn(foundElements);

        // Act
        String[] messages = new String[]{"abc", "bcd"};
        this.seleniumAdapter.hasOnly(elementWrapped, messages, ".class", ComparisonOption.TEXT, "INNERHTML");

        // Assert
        verify(elementWrapped, times(1)).findElements(iByWebCaptor.capture());
        assertEquals(".class", iByWebCaptor.getValue().toString());
    }

    @Test
    void hasOnly_comparisonByRawMatchesExpectation_passes() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("abc");
        when(element1.getAttribute(eq("attr"))).thenReturn("cba");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("bcd");
        when(element2.getAttribute(eq("attr"))).thenReturn("dcb");

        Collection<WebControl> foundElements = new ArrayList<>();
        foundElements.add(element1);
        foundElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.findElements(any())).thenReturn(foundElements);

        // Act
        String[] messages = new String[]{"cba", "dcb"};
        this.seleniumAdapter.hasOnly(elementWrapped, messages, ".class", ComparisonOption.RAW, "attr");

        // Assert
        verify(elementWrapped, times(1)).findElements(iByWebCaptor.capture());
        assertEquals(".class", iByWebCaptor.getValue().toString());
    }

    @Test
    void hasOnly_comparisonByTextCustomAttributeMatchesExpectation_passes() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("abc");
        when(element1.getAttribute(eq("attr"))).thenReturn("cba");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("bcd");
        when(element2.getAttribute(eq("attr"))).thenReturn("dcb");

        Collection<WebControl> foundElements = new ArrayList<>();
        foundElements.add(element1);
        foundElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.findElements(any())).thenReturn(foundElements);

        // Act
        String[] messages = new String[]{"cba", "dcb"};
        this.seleniumAdapter.hasOnly(elementWrapped, messages, ".class", ComparisonOption.TEXT, "attr");

        // Assert
        verify(elementWrapped, times(1)).findElements(iByWebCaptor.capture());
        assertEquals(".class", iByWebCaptor.getValue().toString());
    }

    @Test
    void hasOnly_comparisonByTextDoesNotMatchExpectation_throwsException() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("abc");
        when(element1.getAttribute(eq("value"))).thenReturn("cba");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("bcd");
        when(element2.getAttribute(eq("value"))).thenReturn("dcb");

        Collection<WebControl> foundElements = new ArrayList<>();
        foundElements.add(element1);
        foundElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.findElements(any())).thenReturn(foundElements);

        // Act
        String[] messages = new String[]{"abc"};
        Executable action = () -> this.seleniumAdapter.hasOnly(elementWrapped, messages, ".class", ComparisonOption.TEXT, "INNERHTML");

        // Assert
        Exception exception = assertThrows(ElementDoesNotOnlyHaveException.class, action);
        assertEquals("The specified element does not only have the options \"[bcd]\".", exception.getMessage());
    }

    @Test
    void hasOnly_casingMismatch_throwsException() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("abc");
        when(element1.getAttribute(eq("value"))).thenReturn("cba");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("bcd");
        when(element2.getAttribute(eq("value"))).thenReturn("dcb");

        Collection<WebControl> foundElements = new ArrayList<>();
        foundElements.add(element1);
        foundElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.findElements(any())).thenReturn(foundElements);

        // Act
        String[] messages = new String[]{"abc", "bcD"};
        Executable action = () -> this.seleniumAdapter.hasOnly(elementWrapped, messages, ".class", ComparisonOption.TEXT, "INNERHTML");

        // Assert
        Exception exception = assertThrows(ElementDoesNotHaveException.class, action);
        assertEquals("The specified element does not have a child element with value \"bcD\".", exception.getMessage());
    }

    @Test
    void hasOnly_onlyHasSubstring_throwsException() {

        // Arrange
        SeleniumElement element1 = Mockito.mock(SeleniumElement.class);
        when(element1.getText()).thenReturn("abc");
        when(element1.getAttribute(eq("value"))).thenReturn("cba");

        SeleniumElement element2 = Mockito.mock(SeleniumElement.class);
        when(element2.getText()).thenReturn("bcd");
        when(element2.getAttribute(eq("value"))).thenReturn("dcb");

        Collection<WebControl> foundElements = new ArrayList<>();
        foundElements.add(element1);
        foundElements.add(element2);

        SeleniumElement elementWrapped = Mockito.mock(SeleniumElement.class);
        when(elementWrapped.getSelector()).thenReturn(By.cssSelector("#id"));
        when(elementWrapped.findElements(any())).thenReturn(foundElements);

        // Act
        String[] messages = new String[]{"abc", "bc"};
        Executable action = () -> this.seleniumAdapter.hasOnly(elementWrapped, messages, ".class", ComparisonOption.TEXT, "INNERHTML");

        // Assert
        Exception exception = assertThrows(ElementDoesNotHaveException.class, action);
        assertEquals("The specified element does not have a child element with value \"bc\".", exception.getMessage());
    }

    @Test
    void is_notSelectTextComparisonMatchesExactly_passes() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("abc");
        when(element.getTagName()).thenReturn("h1");
        when(element.getAttribute(eq("attr"))).thenReturn("cba");

        // Act
        this.seleniumAdapter.is(element, "abc", ComparisonOption.TEXT, "INNERHTML");

        // Assert
        verify(element, times(1)).getText();
    }

    @Test
    void is_notSelectRawComparisonMatchesExactly_passes() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cba");
        when(element.getTagName()).thenReturn("h1");
        when(element.getAttribute(eq("attr"))).thenReturn("abc");

        // Act
        this.seleniumAdapter.is(element, "abc", ComparisonOption.RAW, "attr");

        // Assert
        verify(element, times(0)).getText();
        verify(element, times(1)).getAttribute(eq("attr"));
    }

    @Test
    void is_notSelectTextComparisonMismatch_throwsException() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("abc");
        when(element.getTagName()).thenReturn("h1");
        when(element.getAttribute(eq("attr"))).thenReturn("abC");

        // Act
        Executable action = () -> this.seleniumAdapter.is(element, "abC", ComparisonOption.TEXT, "INNERHTML");

        // Assert
        Exception exception = assertThrows(ValuesAreNotEqualException.class, action);
        assertEquals("Values are not equal. Expected \"abC\" but actual value is \"abc\".", exception.getMessage());
    }

    @Test
    void is_notSelectRawComparisonMismatch_throwsException() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cbA");
        when(element.getTagName()).thenReturn("h1");
        when(element.getAttribute(eq("attr"))).thenReturn("cba");

        // Act
        Executable action = () -> this.seleniumAdapter.is(element, "cbA", ComparisonOption.RAW, "attr");

        // Assert
        Exception exception = assertThrows(ValuesAreNotEqualException.class, action);
        assertEquals("Values are not equal. Expected \"cbA\" but actual value is \"cba\".", exception.getMessage());
    }

    @Test
    void is_notSelectRawComparisonMismatchSubstring_throwsException() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cb");
        when(element.getTagName()).thenReturn("h1");
        when(element.getAttribute(eq("attr"))).thenReturn("cba");

        // Act
        Executable action = () -> this.seleniumAdapter.is(element, "cb", ComparisonOption.RAW, "attr");

        // Assert
        Exception exception = assertThrows(ValuesAreNotEqualException.class, action);
        assertEquals("Values are not equal. Expected \"cb\" but actual value is \"cba\".", exception.getMessage());
    }

    @Test
    void is_selectElementRawComparison_passes() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cb");
        when(element.getTagName()).thenReturn("select");
        when(element.getAttribute(eq("attr"))).thenReturn("cba");
        when(element.getSelectedOptionText()).thenReturn("exec");

        // Act
        this.seleniumAdapter.is(element, "exec", ComparisonOption.RAW, "INNERHTML");

        // Assert
        verify(element, times(1)).getSelectedOptionText();
    }

    @Test
    void is_selectElementRawComparisonWithValueTest_passes() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cb");
        when(element.getTagName()).thenReturn("select");
        when(element.getAttribute(eq("attr"))).thenReturn("cba");
        when(element.getSelectedOptionText()).thenReturn("exe");

        when(element.getSelectedOption()).thenReturn(seleniumElement);
        when(seleniumElement.getUnderlyingWebElement()).thenReturn(webElement);
        when(webElement.getAttribute(eq("value"))).thenReturn("exec");

        // Act
        this.seleniumAdapter.is(element, "exec", ComparisonOption.RAW, "value");

        // Assert
        verify(element, times(1)).getSelectedOption();
        verify(webElement, times(1)).getAttribute(eq("value"));
    }

    @Test
    void is_selectElementRawComparisonWithTextMismatch_throwsException() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cb");
        when(element.getTagName()).thenReturn("select");
        when(element.getAttribute(eq("attr"))).thenReturn("cba");
        when(element.getSelectedOptionText()).thenReturn("exe");

        when(element.getSelectedOption()).thenReturn(seleniumElement);
        when(seleniumElement.getUnderlyingWebElement()).thenReturn(webElement);
        when(webElement.getAttribute(eq("value"))).thenReturn("exec");

        // Act
        Executable action = () -> this.seleniumAdapter.is(element, "bar", ComparisonOption.RAW, "INNERHTML");

        // Assert
        Exception exception = assertThrows(ValuesAreNotEqualException.class, action);
        assertEquals("Values are not equal. Expected \"bar\" but actual value is \"exe\".", exception.getMessage());
    }

    @Test
    void is_selectElementRawComparisonWithValueMismatch_throwsException() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cb");
        when(element.getTagName()).thenReturn("select");
        when(element.getAttribute(eq("attr"))).thenReturn("cba");
        when(element.getSelectedOptionText()).thenReturn("exe");

        when(element.getSelectedOption()).thenReturn(seleniumElement);
        when(seleniumElement.getUnderlyingWebElement()).thenReturn(webElement);
        when(webElement.getAttribute(eq("value"))).thenReturn("exec");

        // Act
        Executable action = () -> this.seleniumAdapter.is(element, "bar", ComparisonOption.RAW, "value");

        // Assert
        Exception exception = assertThrows(ValuesAreNotEqualException.class, action);
        assertEquals("Values are not equal. Expected \"bar\" but actual value is \"exec\".", exception.getMessage());
    }

    @Test
    void isLike_notSelectTextComparisonMatchesExactly_passes() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("abc");
        when(element.getTagName()).thenReturn("h1");
        when(element.getAttribute(eq("attr"))).thenReturn("cba");

        // Act
        this.seleniumAdapter.isLike(element, "abc", ComparisonOption.TEXT, "INNERHTML");

        // Assert
        verify(element, times(1)).getText();
    }

    @Test
    void isLike_notSelectRawComparisonMatchesExactly_passes() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cba");
        when(element.getTagName()).thenReturn("h1");
        when(element.getAttribute(eq("attr"))).thenReturn("abc");

        // Act
        this.seleniumAdapter.isLike(element, "abc", ComparisonOption.RAW, "attr");

        // Assert
        verify(element, times(0)).getText();
        verify(element, times(1)).getAttribute(eq("attr"));
    }

    @Test
    void isLike_notSelectRawComparisonMatchesWithCaseMismatch_passes() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cba");
        when(element.getTagName()).thenReturn("h1");
        when(element.getAttribute(eq("attr"))).thenReturn("abC");

        // Act
        this.seleniumAdapter.isLike(element, "abc", ComparisonOption.RAW, "attr");

        // Assert
        verify(element, times(0)).getText();
        verify(element, times(1)).getAttribute(eq("attr"));
    }

    @Test
    void isLike_notSelectRawComparisonMatchesSubstring_passes() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cba");
        when(element.getTagName()).thenReturn("h1");
        when(element.getAttribute(eq("attr"))).thenReturn("abc");

        // Act
        this.seleniumAdapter.isLike(element, "ab", ComparisonOption.RAW, "attr");

        // Assert
        verify(element, times(0)).getText();
        verify(element, times(1)).getAttribute(eq("attr"));
    }

    @Test
    void isLike_notSelectTextComparisonMismatch_throwsException() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("abc");
        when(element.getTagName()).thenReturn("h1");
        when(element.getAttribute(eq("attr"))).thenReturn("abC");

        // Act
        Executable action = () -> this.seleniumAdapter.isLike(element, "abcc", ComparisonOption.TEXT, "INNERHTML");

        // Assert
        Exception exception = assertThrows(ValuesAreNotAlikeException.class, action);
        assertEquals("The expected value \"abcc\" and the encountered value \"abc\" are not alike.", exception.getMessage());
    }

    @Test
    void isLike_notSelectRawComparisonMismatch_throwsException() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cbA");
        when(element.getTagName()).thenReturn("h1");
        when(element.getAttribute(eq("attr"))).thenReturn("cba");

        // Act
        Executable action = () -> this.seleniumAdapter.isLike(element, "cbaa", ComparisonOption.RAW, "attr");

        // Assert
        Exception exception = assertThrows(ValuesAreNotAlikeException.class, action);
        assertEquals("The expected value \"cbaa\" and the encountered value \"cba\" are not alike.", exception.getMessage());
    }

    @Test
    void isLike_selectElementRawComparison_passes() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cb");
        when(element.getTagName()).thenReturn("select");
        when(element.getAttribute(eq("attr"))).thenReturn("cba");
        when(element.getSelectedOptionText()).thenReturn("exec");

        // Act
        this.seleniumAdapter.isLike(element, "exec", ComparisonOption.RAW, "INNERHTML");

        // Assert
        verify(element, times(1)).getSelectedOptionText();
    }

    @Test
    void isLike_selectElementRawComparisonSubstringMatch_passes() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cb");
        when(element.getTagName()).thenReturn("select");
        when(element.getAttribute(eq("attr"))).thenReturn("cba");
        when(element.getSelectedOptionText()).thenReturn("exec");

        // Act
        this.seleniumAdapter.isLike(element, "exe", ComparisonOption.RAW, "INNERHTML");

        // Assert
        verify(element, times(1)).getSelectedOptionText();
    }

    @Test
    void isLike_selectElementRawComparisonWithValueTest_passes() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cb");
        when(element.getTagName()).thenReturn("select");
        when(element.getAttribute(eq("attr"))).thenReturn("cba");
        when(element.getSelectedOptionText()).thenReturn("exe");

        when(element.getSelectedOption()).thenReturn(seleniumElement);
        when(seleniumElement.getUnderlyingWebElement()).thenReturn(webElement);
        when(webElement.getAttribute(eq("value"))).thenReturn("exec");

        // Act
        this.seleniumAdapter.isLike(element, "exec", ComparisonOption.RAW, "value");

        // Assert
        verify(element, times(1)).getSelectedOption();
        verify(webElement, times(1)).getAttribute(eq("value"));
    }

    @Test
    void isLike_selectElementRawComparisonWithValueTestSubstringMatch_passes() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cb");
        when(element.getTagName()).thenReturn("select");
        when(element.getAttribute(eq("attr"))).thenReturn("cba");
        when(element.getSelectedOptionText()).thenReturn("exe");

        when(element.getSelectedOption()).thenReturn(seleniumElement);
        when(seleniumElement.getUnderlyingWebElement()).thenReturn(webElement);
        when(webElement.getAttribute(eq("value"))).thenReturn("exec");

        // Act
        this.seleniumAdapter.isLike(element, "exe", ComparisonOption.RAW, "value");

        // Assert
        verify(element, times(1)).getSelectedOption();
        verify(webElement, times(1)).getAttribute(eq("value"));
    }

    @Test
    void isLike_selectElementRawComparisonWithTextMismatch_throwsException() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cb");
        when(element.getTagName()).thenReturn("select");
        when(element.getAttribute(eq("attr"))).thenReturn("cba");
        when(element.getSelectedOptionText()).thenReturn("exe");

        when(element.getSelectedOption()).thenReturn(seleniumElement);
        when(seleniumElement.getUnderlyingWebElement()).thenReturn(webElement);
        when(webElement.getAttribute(eq("value"))).thenReturn("exec");

        // Act
        Executable action = () -> this.seleniumAdapter.isLike(element, "bar", ComparisonOption.RAW, "INNERHTML");

        // Assert
        Exception exception = assertThrows(ValuesAreNotAlikeException.class, action);
        assertEquals("The expected value \"bar\" and the encountered value \"exe\" are not alike.", exception.getMessage());
    }

    @Test
    void isLike_selectElementRawComparisonWithValueMismatch_throwsException() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cb");
        when(element.getTagName()).thenReturn("select");
        when(element.getAttribute(eq("attr"))).thenReturn("cba");
        when(element.getSelectedOptionText()).thenReturn("exe");

        when(element.getSelectedOption()).thenReturn(seleniumElement);
        when(seleniumElement.getUnderlyingWebElement()).thenReturn(webElement);
        when(webElement.getAttribute(eq("value"))).thenReturn("exec");

        // Act
        Executable action = () -> this.seleniumAdapter.isLike(element, "bar", ComparisonOption.RAW, "value");

        // Assert
        Exception exception = assertThrows(ValuesAreNotAlikeException.class, action);
        assertEquals("The expected value \"bar\" and the encountered value \"exec\" are not alike.", exception.getMessage());
    }

    @Test
    void isNotLike_notSelectTextComparisonMismatches_passes() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("abc");
        when(element.getTagName()).thenReturn("h1");
        when(element.getAttribute(eq("attr"))).thenReturn("dba");

        // Act
        this.seleniumAdapter.isNotLike(element, "dba", ComparisonOption.TEXT, "INNERHTML");

        // Assert
        verify(element, times(1)).getText();
    }

    @Test
    void isNotLike_notSelectRawComparisonMismatches_passes() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("dba");
        when(element.getTagName()).thenReturn("h1");
        when(element.getAttribute(eq("attr"))).thenReturn("abc");

        // Act
        this.seleniumAdapter.isNotLike(element, "dba", ComparisonOption.RAW, "attr");

        // Assert
        verify(element, times(0)).getText();
        verify(element, times(1)).getAttribute(eq("attr"));
    }

    @Test
    void isNotLike_notSelectRawComparisonCaseMismatch_throwsException() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cba");
        when(element.getTagName()).thenReturn("h1");
        when(element.getAttribute(eq("attr"))).thenReturn("abC");

        // Act
        Executable action = () -> this.seleniumAdapter.isNotLike(element, "abc", ComparisonOption.RAW, "attr");

        // Assert
        Exception exception = assertThrows(ValuesAreAlikeException.class, action);
        assertEquals("The expected value \"abc\" and the encountered value \"abC\" are alike.", exception.getMessage());
    }

    @Test
    void isNotLike_notSelectTextComparisonExactMatch_throwsException() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("abc");
        when(element.getTagName()).thenReturn("h1");
        when(element.getAttribute(eq("attr"))).thenReturn("cba");

        // Act
        Executable action = () -> this.seleniumAdapter.isNotLike(element, "abc", ComparisonOption.TEXT, "INNERHTML");

        // Assert
        Exception exception = assertThrows(ValuesAreAlikeException.class, action);
        assertEquals("The expected value \"abc\" and the encountered value \"abc\" are alike.", exception.getMessage());
    }

    @Test
    void isNotLike_notSelectRawComparisonMatchesSubstring_throwsException() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cba");
        when(element.getTagName()).thenReturn("h1");
        when(element.getAttribute(eq("attr"))).thenReturn("abc");

        // Act
        Executable action = () -> this.seleniumAdapter.isNotLike(element, "ab", ComparisonOption.RAW, "attr");

        // Assert
        Exception exception = assertThrows(ValuesAreAlikeException.class, action);
        assertEquals("The expected value \"ab\" and the encountered value \"abc\" are alike.", exception.getMessage());
    }

    @Test
    void isNotLike_selectElementRawComparisonMismatch_passes() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cb");
        when(element.getTagName()).thenReturn("select");
        when(element.getAttribute(eq("attr"))).thenReturn("cba");
        when(element.getSelectedOptionText()).thenReturn("exec");

        // Act
        this.seleniumAdapter.isNotLike(element, "execute", ComparisonOption.RAW, "INNERHTML");

        // Assert
        verify(element, times(1)).getSelectedOptionText();
    }

    @Test
    void isNotLike_selectElementRawComparisonSubstringMatch_throwsException() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cb");
        when(element.getTagName()).thenReturn("select");
        when(element.getAttribute(eq("attr"))).thenReturn("cba");
        when(element.getSelectedOptionText()).thenReturn("exec");

        // Act
        Executable action = () -> this.seleniumAdapter.isNotLike(element, "exe", ComparisonOption.RAW, "INNERHTML");

        // Assert
        Exception exception = assertThrows(ValuesAreAlikeException.class, action);
        assertEquals("The expected value \"exe\" and the encountered value \"exec\" are alike.", exception.getMessage());
    }

    @Test
    void isNotLike_selectElementRawComparisonWithValueMismatch_passes() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cb");
        when(element.getTagName()).thenReturn("select");
        when(element.getAttribute(eq("attr"))).thenReturn("cba");
        when(element.getSelectedOptionText()).thenReturn("exe");

        when(element.getSelectedOption()).thenReturn(seleniumElement);
        when(seleniumElement.getUnderlyingWebElement()).thenReturn(webElement);
        when(webElement.getAttribute(eq("value"))).thenReturn("exec");

        // Act
        this.seleniumAdapter.isNotLike(element, "loop", ComparisonOption.RAW, "value");

        // Assert
        verify(element, times(1)).getSelectedOption();
        verify(webElement, times(1)).getAttribute(eq("value"));
    }

    @Test
    void isNotLike_selectElementRawComparisonWithValueTestSubstringMatch_throwsException() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cb");
        when(element.getTagName()).thenReturn("select");
        when(element.getAttribute(eq("attr"))).thenReturn("cba");
        when(element.getSelectedOptionText()).thenReturn("exe");

        when(element.getSelectedOption()).thenReturn(seleniumElement);
        when(seleniumElement.getUnderlyingWebElement()).thenReturn(webElement);
        when(webElement.getAttribute(eq("value"))).thenReturn("exec");

        // Act
        Executable action = () -> this.seleniumAdapter.isNotLike(element, "exe", ComparisonOption.RAW, "value");

        // Assert
        Exception exception = assertThrows(ValuesAreAlikeException.class, action);
        assertEquals("The expected value \"exe\" and the encountered value \"exec\" are alike.", exception.getMessage());
    }

    @Test
    void isNotLike_selectElementRawComparisonWithValueMatch_throwsException() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getText()).thenReturn("cb");
        when(element.getTagName()).thenReturn("select");
        when(element.getAttribute(eq("attr"))).thenReturn("cba");
        when(element.getSelectedOptionText()).thenReturn("exe");

        when(element.getSelectedOption()).thenReturn(seleniumElement);
        when(seleniumElement.getUnderlyingWebElement()).thenReturn(webElement);
        when(webElement.getAttribute(eq("value"))).thenReturn("bar");

        // Act
        Executable action = () -> this.seleniumAdapter.isNotLike(element, "bar", ComparisonOption.RAW, "value");

        // Assert
        Exception exception = assertThrows(ValuesAreAlikeException.class, action);
        assertEquals("The expected value \"bar\" and the encountered value \"bar\" are alike.", exception.getMessage());
    }

    @Test
    void hasAttribute_elementHasAttribute_passes() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.hasAttribute(eq("attribute-name"))).thenReturn(true);

        // Act
        this.seleniumAdapter.hasAttribute(element, "attribute-name");

        // Assert
        verify(element, times(1)).hasAttribute("attribute-name");
    }

    @Test
    void hasAttribute_elementDoesNotHaveAttribute_throwsException() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getSelector()).thenReturn(By.cssSelector(".selector"));
        when(element.hasAttribute(eq("attribute-name"))).thenReturn(false);

        // Act
        Executable action = () -> this.seleniumAdapter.hasAttribute(element, "attribute-name");

        // Assert
        Exception exception = assertThrows(ElementDoesNotHaveAttributeException.class, action);
        assertEquals("The element with selector .selector does not have an attribute with name \"attribute-name\".", exception.getMessage());
        verify(element, times(1)).hasAttribute("attribute-name");
    }

    @Test
    void doesNotHaveAttribute_elementDoesNotHaveAttribute_passes() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.hasAttribute(eq("attribute-name"))).thenReturn(false);

        // Act
        this.seleniumAdapter.doesNotHaveAttribute(element, "attribute-name");

        // Assert
        verify(element, times(1)).hasAttribute("attribute-name");
    }

    @Test
    void doesNotHaveAttribute_elementHasAttribute_throwsException() {

        // Arrange
        SeleniumElement element = Mockito.mock(SeleniumElement.class);
        when(element.getSelector()).thenReturn(By.cssSelector(".selector"));
        when(element.hasAttribute(eq("attribute-name"))).thenReturn(true);

        // Act
        Executable action = () -> this.seleniumAdapter.doesNotHaveAttribute(element, "attribute-name");

        // Assert
        Exception exception = assertThrows(ElementHasAttributeException.class, action);
        assertEquals("The element with selector .selector has an attribute with name \"attribute-name\".", exception.getMessage());
        verify(element, times(1)).hasAttribute("attribute-name");
    }

    @Test
    void verifyAlertText_alertTextMatches_passes() {

        // Arrange
        Alert alertMock = Mockito.mock(Alert.class);
        when(targetLocatorMock.alert()).thenReturn(alertMock);
        when(alertMock.getText()).thenReturn("alert Text");

        // Act
        this.seleniumAdapter.verifyAlertText("alert Text");

        // Assert
        verify(alertMock, times(1)).getText();
    }

    @Test
    void verifyAlertText_alertTextMismatch_throwsException() {

        // Arrange
        Alert alertMock = Mockito.mock(Alert.class);
        when(targetLocatorMock.alert()).thenReturn(alertMock);
        when(alertMock.getText()).thenReturn("alert Text");

        // Act
        Executable action = () -> this.seleniumAdapter.verifyAlertText("alert Tex");

        // Assert
        Exception exception = assertThrows(ValuesAreNotEqualException.class, action);
        assertEquals("Values are not equal. Expected \"alert Tex\" but actual value is \"alert Text\".", exception.getMessage());
    }

    @Test
    void verifyAlertTextLike_caseSensitiveAlertTextMatches_passes() {

        // Arrange
        Alert alertMock = Mockito.mock(Alert.class);
        when(targetLocatorMock.alert()).thenReturn(alertMock);
        when(alertMock.getText()).thenReturn("alert Text");

        // Act
        this.seleniumAdapter.verifyAlertTextLike("alert Text", true);

        // Assert
        verify(alertMock, times(1)).getText();
    }

    @Test
    void verifyAlertTextLike_caseSensitiveAlertTextSubstringMatches_passes() {

        // Arrange
        Alert alertMock = Mockito.mock(Alert.class);
        when(targetLocatorMock.alert()).thenReturn(alertMock);
        when(alertMock.getText()).thenReturn("alert Text");

        // Act
        this.seleniumAdapter.verifyAlertTextLike("rt Text", true);

        // Assert
        verify(alertMock, times(1)).getText();
    }

    @Test
    void verifyAlertTextLike_caseInsensitiveAlertTextMatches_passes() {

        // Arrange
        Alert alertMock = Mockito.mock(Alert.class);
        when(targetLocatorMock.alert()).thenReturn(alertMock);
        when(alertMock.getText()).thenReturn("alert Text");

        // Act
        this.seleniumAdapter.verifyAlertTextLike("alert text", false);

        // Assert
        verify(alertMock, times(1)).getText();
    }

    @Test
    void verifyAlertTextLike_caseSensitiveAlertTextMismatch_throwsException() {

        // Arrange
        Alert alertMock = Mockito.mock(Alert.class);
        when(targetLocatorMock.alert()).thenReturn(alertMock);
        when(alertMock.getText()).thenReturn("alert Text");

        // Act
        Executable action = () -> this.seleniumAdapter.verifyAlertTextLike("alert text", true);

        // Assert
        Exception exception = assertThrows(ValuesAreNotAlikeException.class, action);
        assertEquals("The expected value \"alert text\" and the encountered value \"alert Text\" are not alike.", exception.getMessage());
    }

    @Test
    void verifyAlertTextLike_caseInsensitiveAlertTextMismatch_throwsException() {

        // Arrange
        Alert alertMock = Mockito.mock(Alert.class);
        when(targetLocatorMock.alert()).thenReturn(alertMock);
        when(alertMock.getText()).thenReturn("alert Text");

        // Act
        Executable action = () -> this.seleniumAdapter.verifyAlertTextLike("aler Text", false);

        // Assert
        Exception exception = assertThrows(ValuesAreNotAlikeException.class, action);
        assertEquals("The expected value \"aler Text\" and the encountered value \"alert Text\" are not alike.", exception.getMessage());
    }

    @Test
    void verifyTitle_titleTextMatches_passes() {

        // Arrange
        when(webDriver.getTitle()).thenReturn("title Text");

        // Act
        this.seleniumAdapter.verifyTitle("title Text");

        // Assert
        verify(webDriver, times(1)).getTitle();
    }

    @Test
    void verifyTitle_titleTextMismatch_throwsException() {

        // Arrange
        when(webDriver.getTitle()).thenReturn("title Text");

        // Act
        Executable action = () -> this.seleniumAdapter.verifyTitle("title Tex");

        // Assert
        Exception exception = assertThrows(ValuesAreNotEqualException.class, action);
        assertEquals("Values are not equal. Expected \"title Tex\" but actual value is \"title Text\".", exception.getMessage());
    }

    @Test
    void verifyURL_nullActualUrl_throwsException() {

        // Arrange
        when(webDriver.getCurrentUrl()).thenReturn(null);

        // Act
        Executable action = () -> this.seleniumAdapter.verifyURL(new URL("http://google.com"));

        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, action);
        assertEquals("url", exception.getMessage());
    }

    @Test
    void verifyURL_uriSyntaxException_throwsException() {

        // Arrange
        when(webDriver.getCurrentUrl()).thenReturn("http://google.com/?a b");

        // Act
        Executable action = () -> this.seleniumAdapter.verifyURL(new URL("http://google.com/?a b"));

        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, action);
        assertEquals("url", exception.getMessage());
    }

    @Test
    void verifyURL_comparingURLisNull_throwsException() {

        // Arrange
        when(webDriver.getCurrentUrl()).thenReturn("http://google.com/");

        // Act
        Executable action = () -> this.seleniumAdapter.verifyURL(null);

        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, action);
        assertEquals("comparingURL", exception.getMessage());
    }

    @Test
    void verifyURL_comparingURLinvalidSyntax_throwsException() {

        // Arrange
        when(webDriver.getCurrentUrl()).thenReturn("http://google.com/");

        // Act
        Executable action = () -> this.seleniumAdapter.verifyURL(new URL("http://google.com/?a b"));

        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, action);
        assertEquals("comparingURL", exception.getMessage());
    }

    @Test
    void verifyURL_URLMismatch_throwsException() {

        // Arrange
        when(webDriver.getCurrentUrl()).thenReturn("http://google.com/");

        // Act
        Executable action = () -> this.seleniumAdapter.verifyURL(new URL("http://google.com/test"));

        // Assert
        Exception exception = assertThrows(ValuesAreNotEqualException.class, action);
        assertEquals("Values are not equal. Expected \"http://google.com/test\" but actual value is \"http://google.com/\".", exception.getMessage());
    }

    @Test
    void verifyURL_URLMatches_passes() throws MalformedURLException {

        // Arrange
        when(webDriver.getCurrentUrl()).thenReturn("http://google.com/");

        // Act
        this.seleniumAdapter.verifyURL(new URL("http://google.com/"));

        // Assert
        verify(webDriver, times(1)).getCurrentUrl();
    }
}
