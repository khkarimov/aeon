package com.ultimatesoftware.aeon.core.command.execution.commands.initialization;

import com.ultimatesoftware.aeon.core.command.execution.commands.interfaces.IWebControlFinder;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class WebCommandInitializerTest {

    private WebCommandInitializer webCommandInitializer;

    @Mock
    private IWebControlFinder finder;

    @Mock
    private IWebDriver driver;

    @Mock
    private IByWeb switchMechanism;

    @Mock
    private IByWeb selector;

    @BeforeEach
    public void setup() {
        webCommandInitializer = new WebCommandInitializer(finder, selector, switchMechanism);
    }

    @Test
    public void WebCommandInitializerFindElementTest() {
        // Arrange

        // Act
        WebControl actual = (WebControl) webCommandInitializer.findElement(driver, selector);
        WebControl expected = finder.findElement(driver, selector);

        // Assert
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void WebCommandInitializerSetContextTest() {
        // Arrange

        // Act
        Consumer<IDriver> actual = webCommandInitializer.setContext();
        actual.accept(driver);

        // Assert
        verify(driver, times(1)).switchToFrame(selector);
    }
}
