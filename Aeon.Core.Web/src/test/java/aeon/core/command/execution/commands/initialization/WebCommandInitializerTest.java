package aeon.core.command.execution.commands.initialization;

import aeon.core.command.execution.commands.interfaces.IWebControlFinder;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class WebCommandInitializerTest {

    private WebCommandInitializer webCommandInitializer;
    private Iterable<IByWeb> iterator;

    @Mock
    private IWebControlFinder finder;

    @Mock
    private IWebDriver driver;

    @Mock
    private IByWeb selector;

    @BeforeEach
    public void setup() {
        iterator = Arrays.asList(selector);
        webCommandInitializer = new WebCommandInitializer(finder, iterator);
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
