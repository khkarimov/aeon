package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.command.execution.commands.interfaces.IWebSelectorFinder;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import java.util.function.Consumer;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


public class WebControlFinderTests {
    private WebControlFinder webControlFinderDefault;
    private WebControlFinder webControlFinderSet;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock private IByWeb selector;
    @Mock private ICommandInitializer initializer;
    @Mock private IWebDriver driver;
    @Mock private WebControl control;
    @Mock private Consumer<IDriver> action;
    @Mock private IWebSelectorFinder selectorFinder;

    @Before
    public void setup(){
        webControlFinderDefault = new WebControlFinder();
        webControlFinderSet = new WebControlFinder(selectorFinder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionIfSetDriverIsNull() {
        // Arrange

        // Act
        webControlFinderSet.findElement(null, selector);

        // Arrange
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionIfDefaultDriverIsNull() {
        // Arrange

        // Act
        webControlFinderDefault.findElement(null, selector);

        // Arrange
    }

    @Test
    public void verify_webControl_Default_After_Calling_findElement(){
        // Arrange
        when(initializer.setContext()).thenReturn(action);
        when(driver.findElement(selector)).thenReturn(control);

        // Act
        WebControl controlDefault = webControlFinderDefault.findElement(driver, selector);

        // Assert
        assertEquals(control, controlDefault);
    }

    @Test
    public void verify_webControl_Set_After_Calling_findElement(){
        // Arrange
        when(initializer.setContext()).thenReturn(action);
        IByWeb selection = selectorFinder.findSelector(driver, selector);
        when(driver.findElement(selection)).thenReturn(control);

        // Act
        WebControl controlerSet = webControlFinderSet.findElement(driver, selector);

        // Assert
        assertEquals(control, controlerSet);
    }
}