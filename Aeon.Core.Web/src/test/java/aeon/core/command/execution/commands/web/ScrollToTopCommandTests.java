package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ScrollToTopCommandTests {
    private ScrollToTopCommand scrollToTopCommand;

    @Mock
    private IWebDriver driver;

    @BeforeEach
    public void setup() {
        scrollToTopCommand = new ScrollToTopCommand();
    }

    @Test
    public void commandDelegateScrollToTopCommand() {
        //Arrange

        //Act
        scrollToTopCommand.driverDelegate(driver);

        //Assert
        verify(driver, times(1)).scrollToTop();
    }

    @Test
    public void testDriverDelegateNullDriver() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> scrollToTopCommand.driverDelegate(null));
    }
}
