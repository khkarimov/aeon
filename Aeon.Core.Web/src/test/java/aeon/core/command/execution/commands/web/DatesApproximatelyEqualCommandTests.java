package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.joda.time.DateTime;
import org.joda.time.Period;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DatesApproximatelyEqualCommandTests {
    private DatesApproximatelyEqualCommand datesApproximatelyEqualCommand;
    private String attributeName = "attrName";
    private DateTime expectedDate = new DateTime();
    private Period acceptableDelta = new Period();

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private IByWeb selector;
    @Mock
    private ICommandInitializer commandInitializer;
    @Mock
    private IWebDriver driver;
    @Mock
    private WebControl control;

    @BeforeEach
    public void setUp() {
        datesApproximatelyEqualCommand = new DatesApproximatelyEqualCommand(selector, commandInitializer, attributeName, expectedDate, acceptableDelta);
    }

    @Test
    public void testCommandDelegate() {
        // Arrange

        // Act
        datesApproximatelyEqualCommand.commandDelegate(driver, control);

        // Assert
        verify(driver, times(1)).datesApproximatelyEqual(control, attributeName, expectedDate, acceptableDelta);
    }

    @Test
    public void testCommandDelegateNullDriver() {
        //Arrange
        Assertions.assertThrows(NullPointerException.class,
                () -> datesApproximatelyEqualCommand.commandDelegate(null, control));

        //Assert
        verify(driver, times(0)).datesApproximatelyEqual(control, attributeName, expectedDate, acceptableDelta);
    }
}
