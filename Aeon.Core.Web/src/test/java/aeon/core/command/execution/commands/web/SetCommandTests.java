package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SetCommandTests {

    private SetCommand setCommandObject;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    IByWeb selector;

    @Mock
    ICommandInitializer initializer;

    @Mock
    WebControl control;

    @Mock
    IWebDriver driver;

    @Mock
    Consumer<IDriver> driverConsumer;

    String value;

    @BeforeEach
    public void setUp() {
        setCommandObject = new SetCommand(selector, initializer, WebSelectOption.Text, value);
    }

    @Test
    public void commandDelegateDriverNullThrowsException()
    {
        //Arrange
        Throwable illegalArgumentException;

        //Act
        illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class,
                () -> setCommandObject.commandDelegate(null, control));

        //Assert
        Assertions.assertEquals("driver", illegalArgumentException.getMessage());
    }

    @Test
    public void commandDelegateSetCommand()
    {
        //Arrange
        when(initializer.setContext()).thenReturn(driverConsumer);
        when(initializer.findElement(driver, selector)).thenReturn(control);

        //Act
        Consumer<IDriver> action = setCommandObject.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).set(control, WebSelectOption.Text, value);
    }
}
