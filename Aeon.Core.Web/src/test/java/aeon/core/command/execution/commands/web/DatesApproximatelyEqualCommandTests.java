package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class DatesApproximatelyEqualCommandTests {
    private DatesApproximatelyEqualCommand datesApproximatelyEqualCommand;
    private String attributeName = "attrName";
    private DateTime expectedDate = new DateTime();
    private Period acceptableDelta = new Period();

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private IByWeb selector;
    @Mock
    private ICommandInitializer commandInitializer;
    @Mock
    private IWebDriver driver;
    @Mock
    private WebControl control;

    @Before
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

    @Test (expected = NullPointerException.class)
    public void testCommandDelegateNullDriver() {
        //Arrange

        //Act
        datesApproximatelyEqualCommand.commandDelegate(null, control);

        //Assert
        verify(driver, times(0)).datesApproximatelyEqual(control, attributeName, expectedDate, acceptableDelta);
    }
}
