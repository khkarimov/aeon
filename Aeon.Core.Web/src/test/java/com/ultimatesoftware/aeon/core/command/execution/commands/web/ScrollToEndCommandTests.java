package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;
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
public class ScrollToEndCommandTests {
    private ScrollToEndCommand scrollToEndCommand;

    @Mock
    private IWebDriver driver;

    @BeforeEach
    public void setUp() {
        scrollToEndCommand = new ScrollToEndCommand();
    }

    @Test
    public void commandDelegateScrollToEndCommand() {
        //Arrange

        //Act
        scrollToEndCommand.driverDelegate(driver);

        //Assert
        verify(driver, times(1)).scrollToEnd();
    }
}
