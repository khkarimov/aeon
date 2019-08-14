package com.ultimatesoftware.aeon.extensions.selenium.jquery;

import com.ultimatesoftware.aeon.core.common.web.selectors.By;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeleniumScriptExecutorTests {

    private SeleniumScriptExecutor seleniumScriptExecutor;

    @Mock
    private RemoteWebDriver remoteWebDriver;

    @Mock
    private Logger logger;

    @BeforeEach
    void setup() {
        this.seleniumScriptExecutor = new SeleniumScriptExecutor(this.remoteWebDriver);
        SeleniumScriptExecutor.log = this.logger;
    }

    @Test
    void setTimeout_setsTimeout() {

        // Arrange
        WebDriver.Options webDriverOptions = mock(WebDriver.Options.class);
        WebDriver.Timeouts webDriverTimeouts = mock(WebDriver.Timeouts.class);
        when(this.remoteWebDriver.manage()).thenReturn(webDriverOptions);
        when(webDriverOptions.timeouts()).thenReturn(webDriverTimeouts);

        // Act
        this.seleniumScriptExecutor.setTimeout(Duration.ofSeconds(3));

        // Assert
        verify(webDriverTimeouts, times(1)).setScriptTimeout(3L, TimeUnit.SECONDS);
        verify(this.logger, times(1)).trace("WebDriver.Manage().Timeouts().SetScriptTimeout({});", Duration.ofSeconds(3));
    }

    @Test
    void executeScript_noReturnValue_executesScript() {

        // Arrange

        // Act
        this.seleniumScriptExecutor.executeScript("my-javascript", "arg1", "arg2");

        // Assert
        verify(this.remoteWebDriver, times(1)).executeScript("my-javascript", "arg1", "arg2");
        verify(this.logger, times(1)).trace("WebDriver.executeScript(\"{}\");", "my-javascript");
        verifyNoMoreInteractions(this.logger);
    }

    @Test
    void executeScript_returnsValueButTraceLogsAreDisabled_executesScriptAndDoesNotLogResult() {

        // Arrange
        when(this.remoteWebDriver.executeScript("my-javascript", "arg1", "arg2")).thenReturn("{test: [\"value1\", \"value2\"]}");

        // Act
        this.seleniumScriptExecutor.executeScript("my-javascript", "arg1", "arg2");

        // Assert
        verify(this.remoteWebDriver, times(1)).executeScript("my-javascript", "arg1", "arg2");
        verify(this.logger, times(1)).trace("WebDriver.executeScript(\"{}\");", "my-javascript");
        verify(this.logger, times(1)).isTraceEnabled();
        verifyNoMoreInteractions(this.logger);
    }

    @Test
    void executeScript_returnsValueAndTraceLogsAreEnabled_executesScriptAndLogsResult() {

        // Arrange
        WebControl webControl1 = new WebControl();
        webControl1.setSelector(By.cssSelector("selector1"));
        WebControl webControl2 = new WebControl();
        webControl2.setSelector(By.cssSelector("selector2"));
        List<WebControl> result = Arrays.asList(webControl1, webControl2);
        when(this.remoteWebDriver.executeScript("my-javascript", "arg1", "arg2")).thenReturn(result);
        when(this.logger.isTraceEnabled()).thenReturn(true);

        // Act
        this.seleniumScriptExecutor.executeScript("my-javascript", "arg1", "arg2");

        // Assert
        verify(this.remoteWebDriver, times(1)).executeScript("my-javascript", "arg1", "arg2");
        verify(this.logger, times(1)).trace("WebDriver.executeScript(\"{}\");", "my-javascript");
        verify(this.logger, times(1)).isTraceEnabled();
        verify(this.logger, times(1)).trace("WebDriver.executeScript() returned {}", "class java.util.Arrays$ArrayList: [\"selector1\", \"selector2\"]");
    }

    @Test
    void executeScript_returnsEmptyCollectionAndTraceLogsAreEnabled_executesScriptAndLogsResult() {

        // Arrange
        List<WebControl> result = Collections.emptyList();
        when(this.remoteWebDriver.executeScript("my-javascript", "arg1", "arg2")).thenReturn(result);
        when(this.logger.isTraceEnabled()).thenReturn(true);

        // Act
        this.seleniumScriptExecutor.executeScript("my-javascript", "arg1", "arg2");

        // Assert
        verify(this.remoteWebDriver, times(1)).executeScript("my-javascript", "arg1", "arg2");
        verify(this.logger, times(1)).trace("WebDriver.executeScript(\"{}\");", "my-javascript");
        verify(this.logger, times(1)).isTraceEnabled();
        verify(this.logger, times(1)).trace("WebDriver.executeScript() returned {}", "[]");
    }

    @Test
    void executeScript_returnsValueThatFailsMakingWebControlSelectorReadableAndTraceLogsAreEnabled_executesScriptAndLogsResultWithSimpleSerialization() {

        // Arrange
        WebControl webControl1 = mock(WebControl.class);
        WebControl webControl2 = mock(WebControl.class);
        when(webControl1.toString()).thenReturn("WebControl1");
        when(webControl2.toString()).thenReturn("WebControl2");
        List<WebControl> result = Arrays.asList(webControl1, webControl2);
        when(this.remoteWebDriver.executeScript("my-javascript", "arg1", "arg2")).thenReturn(result);
        when(this.logger.isTraceEnabled()).thenReturn(true);

        // Act
        this.seleniumScriptExecutor.executeScript("my-javascript", "arg1", "arg2");

        // Assert
        verify(this.remoteWebDriver, times(1)).executeScript("my-javascript", "arg1", "arg2");
        verify(this.logger, times(1)).trace("WebDriver.executeScript(\"{}\");", "my-javascript");
        verify(this.logger, times(1)).isTraceEnabled();
        verify(this.logger, times(1)).trace("WebDriver.executeScript() returned {}", "[WebControl1, WebControl2]");
    }

    @Test
    void executeScript_returnsStringValueAndTraceLogsAreEnabled_executesScriptAndLogsResult() {

        // Arrange
        when(this.remoteWebDriver.executeScript("my-javascript", "arg1", "arg2")).thenReturn("return-value");
        when(this.logger.isTraceEnabled()).thenReturn(true);

        // Act
        this.seleniumScriptExecutor.executeScript("my-javascript", "arg1", "arg2");

        // Assert
        verify(this.remoteWebDriver, times(1)).executeScript("my-javascript", "arg1", "arg2");
        verify(this.logger, times(1)).trace("WebDriver.executeScript(\"{}\");", "my-javascript");
        verify(this.logger, times(1)).isTraceEnabled();
        verify(this.logger, times(1)).trace("WebDriver.executeScript() returned {}", "return-value");
    }

    @Test
    void executeAsyncScript_noReturnValue_executesScript() {

        // Arrange

        // Act
        this.seleniumScriptExecutor.executeAsyncScript("my-javascript", "arg1", "arg2");

        // Assert
        verify(this.remoteWebDriver, times(1)).executeAsyncScript("my-javascript", "arg1", "arg2");
        verify(this.logger, times(1)).trace("WebDriver.executeAsyncScript(\"{}\");", "my-javascript");
        verifyNoMoreInteractions(this.logger);
    }

    @Test
    void executeAsyncScript_returnsValueButTraceLogsAreDisabled_executesScriptAndDoesNotLogResult() {

        // Arrange
        when(this.remoteWebDriver.executeAsyncScript("my-javascript", "arg1", "arg2")).thenReturn("{test: [\"value1\", \"value2\"]}");

        // Act
        this.seleniumScriptExecutor.executeAsyncScript("my-javascript", "arg1", "arg2");

        // Assert
        verify(this.remoteWebDriver, times(1)).executeAsyncScript("my-javascript", "arg1", "arg2");
        verify(this.logger, times(1)).trace("WebDriver.executeAsyncScript(\"{}\");", "my-javascript");
        verify(this.logger, times(1)).isTraceEnabled();
        verifyNoMoreInteractions(this.logger);
    }

    @Test
    void executeAsyncScript_returnsValueAndTraceLogsAreEnabled_executesScriptAndDoesLogsResult() {

        // Arrange
        WebControl webControl1 = new WebControl();
        webControl1.setSelector(By.cssSelector("selector1"));
        WebControl webControl2 = new WebControl();
        webControl2.setSelector(By.cssSelector("selector2"));
        List<WebControl> result = Arrays.asList(webControl1, webControl2);
        when(this.remoteWebDriver.executeAsyncScript("my-javascript", "arg1", "arg2")).thenReturn(result);
        when(this.logger.isTraceEnabled()).thenReturn(true);

        // Act
        this.seleniumScriptExecutor.executeAsyncScript("my-javascript", "arg1", "arg2");

        // Assert
        verify(this.remoteWebDriver, times(1)).executeAsyncScript("my-javascript", "arg1", "arg2");
        verify(this.logger, times(1)).trace("WebDriver.executeAsyncScript(\"{}\");", "my-javascript");
        verify(this.logger, times(1)).isTraceEnabled();
        verify(this.logger, times(1)).trace("WebDriver.executeAsyncScript() returned {}", "class java.util.Arrays$ArrayList: [\"selector1\", \"selector2\"]");
    }
}
