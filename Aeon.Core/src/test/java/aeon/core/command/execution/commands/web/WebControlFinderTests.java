package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.command.execution.commands.interfaces.ICommand;
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

import static com.sun.tools.doclint.Entity.times;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
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

    private IWebSelectorFinder selectorFinder;

    @Before
    public void setup(){
        webControlFinderObjectDefault = new WebControlFinder();
        webControlFinderObjectSet = new WebControlFinder(selectorFinder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionIfDriverIsNull() {
        //Arrange: nothing

        //Act
        webControlFinderObjectDefault.findElement(null, selector);
        webControlFinderObjectSet.findElement(null, selector);

        //Assert: nothing
    }

    @Test
    public void findElement_GWebControlFinder(){ //Is this correct naming
//        public void findElementWebControlFinder(){ //Is this correct naming
        //Arrange
        when(initializer.setContext()).thenReturn(action);
        when(initializer.findElement(driver, selector)).thenReturn(control);

        //Act
        //??
        WebControl controler = webControlFinderObjectDefault.findElement(driver, selector);
        action.accept(driver);

        //Assert
//        assertThat(controler, instanceOf(WebControl.class));

    }

}


