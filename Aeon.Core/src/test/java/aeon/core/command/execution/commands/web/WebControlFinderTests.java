package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.command.execution.commands.interfaces.IWebSelectorFinder;
import aeon.core.common.web.interfaces.IBy;
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
    private WebControlFinder webControlFinderObjectDefault;
    private WebControlFinder webControlFinderObjectSet;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock private IBy selector;
    @Mock private ICommandInitializer initializer;
    @Mock private IWebDriver driver;
    @Mock private WebControl control;
    @Mock private Consumer<IDriver> action;
    @Mock private IWebSelectorFinder selectorFinder;

    @Before
    public void setup(){
        webControlFinderObjectDefault = new WebControlFinder();
        webControlFinderObjectSet = new WebControlFinder(selectorFinder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionIfDriverIsNull() {
        webControlFinderObjectDefault.findElement(null, selector);
        webControlFinderObjectSet.findElement(null, selector);
    }

    @Test
    public void verify_webControl_Default_After_Calling_findElement(){
        when(initializer.setContext()).thenReturn(action);
        when(driver.findElement(selector)).thenReturn(control);

        WebControl controlDefault = webControlFinderObjectDefault.findElement(driver, selector);
        assertEquals(control, controlDefault);
    }

    @Test
    public void verify_webControl_Set_After_Calling_findElement(){
        when(initializer.setContext()).thenReturn(action);
        IBy selection = selectorFinder.findSelector(driver, selector);
        when(driver.findElement(selection)).thenReturn(control);

        WebControl controlerSet = webControlFinderObjectSet.findElement(driver, selector);
        assertEquals(control, controlerSet);
    }
}