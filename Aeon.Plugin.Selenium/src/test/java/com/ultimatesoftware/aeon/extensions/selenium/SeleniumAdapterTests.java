package com.ultimatesoftware.aeon.extensions.selenium;

import com.ultimatesoftware.aeon.core.common.exceptions.NoSuchElementException;
import com.ultimatesoftware.aeon.core.common.interfaces.IBy;
import com.ultimatesoftware.aeon.core.common.web.BrowserSize;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IBrowserType;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByXPath;
import com.ultimatesoftware.aeon.core.common.web.selectors.By;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.extensions.selenium.jquery.IJavaScriptFlowExecutor;
import com.ultimatesoftware.aeon.extensions.selenium.jquery.IScriptExecutor;
import com.ultimatesoftware.aeon.extensions.selenium.jquery.SeleniumScriptExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LoggingPreferences;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SeleniumAdapterTests {

    @Mock
    private WebDriver webDriver;

    @Mock
    private IJavaScriptFlowExecutor javaScriptFlowExecutor;

    @Mock
    private IJavaScriptFlowExecutor asyncJavaScriptFlowExecutor;

    @Mock
    private IBrowserType browserType;

    @Mock
    private LoggingPreferences loggingPreferences;

    @Mock
    private WebElement webElement;

    @Mock
    private IByXPath xPathSelector;

    @Mock
    private QuadFunction<IScriptExecutor, String, Iterable<Object>, Object> javascriptExecutor;

    private SeleniumAdapter seleniumAdapter;

    @BeforeEach
    void beforeTest() throws MalformedURLException {
        this.seleniumAdapter = new SeleniumAdapter(
                this.webDriver,
                this.javaScriptFlowExecutor,
                this.asyncJavaScriptFlowExecutor,
                this.browserType,
                BrowserSize.FULL_HD,
                true,
                new URL("http://host/wd/hub"),
                "",
                this.loggingPreferences
        );
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
}
