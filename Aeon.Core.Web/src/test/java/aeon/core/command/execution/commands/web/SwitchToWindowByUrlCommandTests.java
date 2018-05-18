package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SwitchToWindowByUrlCommandTests {

    private SwitchToWindowByUrlCommand switchToWindowByUrlObject;
    private String testUrl = "url";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private IWebDriver driver;

    @BeforeEach
    public void setUp() {
        switchToWindowByUrlObject = new SwitchToWindowByUrlCommand(testUrl);
    }

    @Test
    public void DriverNullThrowsException(){
        //Arrange
        Throwable illegalArgumentException;

        // Act
        illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class,
                () -> switchToWindowByUrlObject.commandDelegate(null));

        // Assert
        Assertions.assertEquals("driver", illegalArgumentException.getMessage());
    }

    @Test
    public void commandDelegateSwitchToWindowByUrlCommand() {
        //Arrange

        //Act
        switchToWindowByUrlObject.commandDelegate(driver);

        //Assert
        verify(driver, times(1)).switchToWindowByUrl(testUrl);
    }
}
