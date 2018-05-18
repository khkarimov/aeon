package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.command.execution.commands.interfaces.IWebSelectorFinder;
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

import java.util.function.Consumer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class WebControlFinderTests {
    private WebControlFinder webControlFinderDefault;
    private WebControlFinder webControlFinderSet;

    @Mock private IByWeb selector;
    @Mock private ICommandInitializer initializer;
    @Mock private IWebDriver driver;
    @Mock private WebControl control;
    @Mock private Consumer<IDriver> action;
    @Mock private IWebSelectorFinder selectorFinder;

    @BeforeEach
    public void setup(){
        webControlFinderDefault = new WebControlFinder();
        webControlFinderSet = new WebControlFinder(selectorFinder);
    }

    @Test
    public void throwIllegalArgumentExceptionIfSetDriverIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> webControlFinderSet.findElement(null, selector));
    }

    @Test
    public void throwIllegalArgumentExceptionIfDefaultDriverIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> webControlFinderDefault.findElement(null, selector));
    }

    @Test
    public void verify_webControl_Default_After_Calling_findElement(){
        // Arrange
        when(initializer.setContext()).thenReturn(action);
        when(driver.findElement(selector)).thenReturn(control);

        // Act
        WebControl controlDefault = webControlFinderDefault.findElement(driver, selector);

        // Assert
        Assertions.assertEquals(control, controlDefault);
    }

    @Test
    public void verify_webControl_Set_After_Calling_findElement(){
        // Arrange
        when(initializer.setContext()).thenReturn(action);
        IByWeb selection = selectorFinder.findSelector(driver, selector);
        when(driver.findElement(selection)).thenReturn(control);

        // Act
        WebControl controllerSet = webControlFinderSet.findElement(driver, selector);

        // Assert
        Assertions.assertEquals(control, controllerSet);
    }
}