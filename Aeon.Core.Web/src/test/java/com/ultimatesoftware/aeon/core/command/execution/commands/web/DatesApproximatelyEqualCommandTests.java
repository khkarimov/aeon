package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.time.Period;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class DatesApproximatelyEqualCommandTests {
    private DatesApproximatelyEqualCommand datesApproximatelyEqualCommand;
    private String attributeName = "attrName";
    private LocalDate expectedDate = LocalDate.now();
    private Period acceptableDelta = Period.of(2, 3, 2);

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
