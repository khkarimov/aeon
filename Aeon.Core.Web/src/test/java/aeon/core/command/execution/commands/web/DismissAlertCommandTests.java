package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.web.DismissAlertCommand;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


public class DismissAlertCommandTests {
    private DismissAlertCommand alertCommand;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IWebDriver driver;

    @Before
    public void setUp() {
        alertCommand = new DismissAlertCommand();
    }

    @Test
    public void dismissAlert_executeDismissAlert() {
        // Arrange

        // Act
        alertCommand.getCommandDelegate().accept(driver);

        // Assert
        verify(driver, times(1)).dismissAlert();
    }
}
