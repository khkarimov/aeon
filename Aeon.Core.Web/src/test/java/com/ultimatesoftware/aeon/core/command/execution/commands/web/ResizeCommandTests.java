package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.awt.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ResizeCommandTests {
    private ResizeCommand resizeCommand;
    private Dimension dimensionSize;
    private String browserSize = "MAXIMIZED";

    @Mock
    private IWebDriver driver;

    @BeforeEach
    public void setUp() {
        resizeCommand = new ResizeCommand(browserSize);
        dimensionSize = new Dimension();
        dimensionSize.setSize(0, 0);
    }

    @Test
    public void commandDelegateResizeCommand() {
        //Arrange

        //Act
        resizeCommand.driverDelegate(driver);

        //Assert
        verify(driver, times(1)).resize(dimensionSize);
    }
}
