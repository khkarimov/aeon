package aeon.core.command.execution.commands.web;


import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class ClickAndHoldCommandTests {

    private ClickAndHoldCommand clickAndHoldCommandObject;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IByWeb selector;
    @Mock
    private ICommandInitializer initializer;
    @Mock
    private IWebDriver driver;
    @Mock
    private WebControl control;
    @Mock
    private Consumer<IDriver> action;

    private int duration;


    @Before
    public void setup(){
        clickAndHoldCommandObject = new ClickAndHoldCommand(selector, initializer, duration);
    }

    public ClickAndHoldCommandTests(int duration){
        this.duration = duration;
    }


    @Parameterized.Parameters
    public static Collection<Object []> data(){
        return Arrays.asList(new Object[][]{
                {0}, {1}, {3}, {5}
        });
    }

    @Test
    public void commandDelegateClickAndHoldCommand(){
        //Arrange
        when(initializer.setContext()).thenReturn(action);
        when(initializer.findElement(driver, selector)).thenReturn(control);

        //Act
        Consumer<IDriver> action = clickAndHoldCommandObject.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).clickAndHold(control, duration);
    }
}
