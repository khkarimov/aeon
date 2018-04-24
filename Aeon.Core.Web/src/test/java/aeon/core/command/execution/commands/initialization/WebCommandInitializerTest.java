package aeon.core.command.execution.commands.initialization;

import aeon.core.command.execution.commands.interfaces.IWebControlFinder;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class WebCommandInitializerTest {

    private WebCommandInitializer webCommandInitializer;
    private Iterable<IByWeb> iterator;

    @Mock
    private IWebControlFinder finder;

    @Mock
    private IWebDriver driver;

    @Mock
    private IByWeb selector;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
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
        Assert.assertEquals(actual, expected);
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
